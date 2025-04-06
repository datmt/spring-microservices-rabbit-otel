package com.datmt.learning.java.inventory.service;

import com.datmt.learning.java.common.dto.InventoryOutOfStockEvent;
import com.datmt.learning.java.common.dto.InventoryReservedEvent;
import com.datmt.learning.java.common.dto.OrderPlacedEvent;
import com.datmt.learning.java.common.helper.MessagingTopics;
import com.datmt.learning.java.inventory.model.InventoryItem;
import com.datmt.learning.java.inventory.repository.InventoryRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository repo;
    private final RabbitTemplate rabbitTemplate;

    public InventoryService(InventoryRepository repo, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<InventoryItem> getAll() {
        return repo.findAll();
    }

    public InventoryItem getByProductUlid(String productUlid) {
        return repo.findByProductUlid(productUlid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public InventoryItem updateQuantity(String productUlid, int newQuantity) {
        InventoryItem item = repo.findByProductUlid(productUlid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setAvailableQuantity(newQuantity);
        return repo.save(item);
    }

    public InventoryItem create(InventoryItem item) {
        return repo.save(item);
    }

    public void handleOrderPlaced(OrderPlacedEvent event) {
        List<String> productUlids = event.items().stream()
                .map(OrderPlacedEvent.Item::productUlid)
                .toList();

        List<InventoryItem> stockItems = repo.findAllByProductUlidIn(productUlids);
        Map<String, InventoryItem> inventoryMap = stockItems.stream()
                .collect(Collectors.toMap(InventoryItem::getProductUlid, i -> i));

        List<OrderPlacedEvent.Item> failedItems = new ArrayList<>();
        List<OrderPlacedEvent.Item> reservedItems = new ArrayList<>();

        for (OrderPlacedEvent.Item item : event.items()) {
            InventoryItem inventory = inventoryMap.get(item.productUlid());
            if (inventory == null || !inventory.isInStock(item.quantity())) {
                failedItems.add(item);
            } else {
                inventory.reserve(item.quantity());
                reservedItems.add(item);
            }
        }

        repo.saveAll(stockItems);

        if (failedItems.isEmpty()) {
            InventoryReservedEvent reservedEvent = new InventoryReservedEvent(
                    event.orderUlid(),
                    reservedItems.stream()
                            .map(i -> new InventoryReservedEvent.Item(i.productUlid(), i.quantity()))
                            .toList()
            );
            rabbitTemplate.convertAndSend(
                    MessagingTopics.Inventory.EXCHANGE,
                    MessagingTopics.Inventory.ROUTING_KEY_INVENTORY_RESERVED,
                    reservedEvent
            );
        } else {
            InventoryOutOfStockEvent outOfStock = new InventoryOutOfStockEvent(
                    event.orderUlid(),
                    failedItems.stream().map(OrderPlacedEvent.Item::productUlid).toList()
            );
            rabbitTemplate.convertAndSend(
                    MessagingTopics.Inventory.EXCHANGE,
                    MessagingTopics.Inventory.ROUTING_KEY_OUT_OF_STOCK,
                    outOfStock
            );
        }
    }
}
