package com.jumbotail.shipping.controller;

import com.jumbotail.shipping.model.Seller;
import com.jumbotail.shipping.repository.SellerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerRepository sellerRepository;

    public SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    // POST /sellers
    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerRepository.save(seller);
    }

    // GET /sellers (to verify)
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }
}