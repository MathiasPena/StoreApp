package com.storeapp.storeapp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.storeapp.storeapp.dto.SaleRequestDTO;
import com.storeapp.storeapp.model.Product;
import com.storeapp.storeapp.model.Sale;
import com.storeapp.storeapp.repository.ProductRepository;
import com.storeapp.storeapp.repository.SaleRepository;

@Service
public class SaleService {
    
    private final ProductRepository productRepo;
    private final SaleRepository saleRepo;

    public SaleService(ProductRepository productRepo, SaleRepository saleRepo) {
        this.productRepo = productRepo;
        this.saleRepo = saleRepo;
    }

    // Transactional por si se cae una operacion (sales + prod)
    @Transactional
    public Sale createSale(SaleRequestDTO req) {
        if (req == null || req.getProductId() == null || req.getQuantity() == null || req.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "producto y cantidad requeridos");
        }

        Product p = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (p.getStock() < req.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        // restar stock
        p.setStock(p.getStock() - req.getQuantity());
        productRepo.save(p);

        // crear venta
        Sale s = new Sale();
        s.setProduct(p);
        s.setQuantity(req.getQuantity());
        s.setUnitPrice(p.getPrice());
        s.setTotal(p.getPrice() * req.getQuantity());

        return saleRepo.save(s);
    }

    //Listar las ventas
    public List<Sale> findAll() {
        return saleRepo.findAll();
    }
}
