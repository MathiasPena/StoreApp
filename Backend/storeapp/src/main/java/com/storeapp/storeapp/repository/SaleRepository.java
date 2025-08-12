package com.storeapp.storeapp.repository;

import com.storeapp.storeapp.model.Sale;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // EntityGraph porque la relacion con product es "Lazy",
    // Pedimos los prod junto a sales
    @EntityGraph(attributePaths = "product")
    List<Sale> findAll();
}
