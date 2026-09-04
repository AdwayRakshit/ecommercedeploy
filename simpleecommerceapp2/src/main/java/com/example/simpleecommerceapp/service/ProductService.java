package com.example.simpleecommerceapp.service;

import com.example.simpleecommerceapp.entity.Product;
import com.example.simpleecommerceapp.controller.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
    public Product getProductById(Long id){
        return productRepo.findById(id).orElseThrow(()->new RuntimeException("product with id "+id+" not found"));

    }
    public void  createProduct(Product product){
        productRepo.save(product);
    }

    public void deleteProduct(Long id){
        productRepo.findById(id).orElseThrow(()->new RuntimeException("product with id "+id+" not found"));
        productRepo.deleteById(id);
    }

    public Product findProductByName(String name){
        return productRepo.findByName(name);
    }
    public void updateProduct(Product product){

        Product existingProduct = productRepo.findById(product.getId())
                .orElseThrow(() ->
                        new RuntimeException("product with id " + product.getId() + " not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());

        // Only update price if provided
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }

        productRepo.save(existingProduct);
    }




}
