package com.storeapp.storeapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.storeapp.storeapp.dto.SaleRequestDTO;
import com.storeapp.storeapp.model.Sale;
import com.storeapp.storeapp.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) { this.saleService = saleService; }

    //Crear una venta
    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody SaleRequestDTO req) {
        Sale s = saleService.createSale(req);
        return ResponseEntity.status(201).body(s);
    }

    //Listar Todas las ventas hechas para testeo (no dto)
    @GetMapping
    public ResponseEntity<List<Sale>> findAll() {
        return ResponseEntity.ok(saleService.findAll());
    }
}