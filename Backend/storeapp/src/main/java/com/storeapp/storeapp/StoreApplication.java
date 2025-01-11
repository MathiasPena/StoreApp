package com.storeapp.storeapp;

import com.storeapp.storeapp.model.Product;
import com.storeapp.storeapp.model.User;
import com.storeapp.storeapp.repository.ProductRepository;
import com.storeapp.storeapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Leyendo productos de la base de datos...");

        // Obtener todos los productos
        List<Product> products = productRepository.findAll();

        // Mostrar productos en la consola
        if (products.isEmpty()) {
            System.out.println("No se encontraron productos en la base de datos.");
        } else {
            products.forEach(product -> System.out.println(product));
        }

        // Obtener todos los usuarios
        List<User> usuarios = userRepository.findAll();

        // Mostrar productos en la consola
        if (usuarios.isEmpty()) {
            System.out.println("No se encontraron usuarios en la base de datos.");
        } else {
            usuarios.forEach(usuario -> System.out.println(usuario));
        }
    }
}