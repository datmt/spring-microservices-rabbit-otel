package com.datmt.learning.java.order.service;

import com.datmt.learning.java.common.dto.OrderPlacedEvent;
import com.datmt.learning.java.common.helper.MessagingTopics;
import com.datmt.learning.java.order.dto.CreateOrderRequest;
import com.datmt.learning.java.order.dto.OrderDTO;
import com.datmt.learning.java.order.dto.OrderItemDTO;
import com.datmt.learning.java.order.model.Order;
import com.datmt.learning.java.order.model.OrderItem;
import com.datmt.learning.java.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public OrderDTO createOrder(CreateOrderRequest req) {
        Order order = new Order();

        List<OrderItem> items = req.items().stream().map(itemReq -> {
            OrderItem item = new OrderItem();
            item.setProductUlid(itemReq.productUlid());
            item.setQuantity(itemReq.quantity());
            // Price should come from snapshot service in real-world
            item.setPrice(BigDecimal.ZERO);
            return item;
        }).toList();

        order.setItems(items);
        order.calculateTotal();

        order = orderRepository.save(order);

        // 🔥 Send event
        OrderPlacedEvent event = new OrderPlacedEvent(
                order.getUlid(),
                items.stream()
                        .map(i -> new OrderPlacedEvent.Item(i.getProductUlid(), i.getQuantity()))
                        .toList()
        );

        rabbitTemplate.convertAndSend(
                MessagingTopics.Order.EXCHANGE,
                MessagingTopics.Order.ROUTING_KEY_ORDER_PLACED,
                event
        );

        return toDTO(order);
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public OrderDTO findByUlid(String ulid) {
        return orderRepository.findByUlid(ulid)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteByUlid(String ulid) {
        orderRepository.findByUlid(ulid)
                .ifPresentOrElse(
                        orderRepository::delete,
                        () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
                );
    }

    private OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setUlid(order.getUlid());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());

        List<OrderItemDTO> items = order.getItems().stream().map(i -> {
            OrderItemDTO d = new OrderItemDTO();
            d.setProductUlid(i.getProductUlid());
            d.setQuantity(i.getQuantity());
            d.setPrice(i.getPrice());
            return d;
        }).toList();

        dto.setItems(items);
        return dto;
    }
}

