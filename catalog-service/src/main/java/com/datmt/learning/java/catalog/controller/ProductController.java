package com.datmt.learning.java.catalog.controller;

import com.datmt.learning.java.catalog.service.ProductService;
import com.datmt.learning.java.common.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public List<ProductDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{ulid}")
    public ProductDTO get(@PathVariable String ulid) {
        return service.findByUlid(ulid);
    }

    @PutMapping("/{ulid}")
    public ProductDTO update(@PathVariable String ulid, @RequestBody ProductDTO dto) {
        return service.update(ulid, dto);
    }

    @DeleteMapping("/{ulid}")
    public ResponseEntity<Void> delete(@PathVariable String ulid) {
        service.delete(ulid);
        return ResponseEntity.noContent().build();
    }
}
