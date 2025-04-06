package com.datmt.learning.java.inventory.controller;

import com.datmt.learning.java.inventory.model.InventoryItem;
import com.datmt.learning.java.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<InventoryItem> all() {
        return service.getAll();
    }

    @GetMapping("/{productUlid}")
    public InventoryItem get(@PathVariable String productUlid) {
        return service.getByProductUlid(productUlid);
    }

    @PutMapping("/{productUlid}")
    public InventoryItem update(@PathVariable String productUlid,
                                @RequestParam(value = "availableQuantity")
                                Integer availableQuantity) {
        return service.updateQuantity(productUlid, availableQuantity);
    }

    @PostMapping
    public InventoryItem create(@RequestBody InventoryItem item) {
        return service.create(item);
    }
}

