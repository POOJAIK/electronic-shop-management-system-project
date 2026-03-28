package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.Product;
import com.ecommerce.esmsproject1.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/products")
    public String showproducts(Model model) {

        List<Product> productsList = productsRepository.findAll();

        System.out.println("Total Products : " + productsList.size());

        model.addAttribute("productsList", productsList);

//        System.out.println(productsList);

        return "products";
    }
}
