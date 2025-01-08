package com.storeapp.storeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storeapp.storeapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Puedes agregar métodos personalizados si es necesario
    List<Product> findAll();
}
