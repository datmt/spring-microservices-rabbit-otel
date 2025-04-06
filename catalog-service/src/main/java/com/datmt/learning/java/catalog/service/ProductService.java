package com.datmt.learning.java.catalog.service;

import com.datmt.learning.java.catalog.model.Product;
import com.datmt.learning.java.catalog.repository.ProductRepository;
import com.datmt.learning.java.common.dto.ProductCreatedEvent;
import com.datmt.learning.java.common.dto.ProductDTO;
import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final RabbitTemplate rabbitTemplate;
    public ProductService(ProductRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ProductDTO create(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());

        Product saved = repository.save(product);
        return toDTO(saved);
    }

    private void emitEvent(Product saved) {
        ProductCreatedEvent event = new ProductCreatedEvent(
                saved.getUlid(),
                saved.getName(),
                saved.getPrice()
        );

        rabbitTemplate.convertAndSend(
                MessagingTopics.Catalog.EXCHANGE,
                MessagingTopics.Catalog.ROUTING_KEY_PRODUCT_CREATED,
                event
        );
    }
    public List<ProductDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO findByUlid(String ulid) {
        Product product = repository.findByUlid(ulid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDTO(product);
    }

    public ProductDTO update(String ulid, ProductDTO dto) {
        Product product = repository.findByUlid(ulid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        product.setName(dto.name());
        product.setPrice(dto.price());

        return toDTO(repository.save(product));
    }

    @Transactional
    public void delete(String ulid) {
        if (!repository.existsByUlid(ulid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteByUlid(ulid);
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getUlid(), p.getName(), p.getPrice());
    }
}

