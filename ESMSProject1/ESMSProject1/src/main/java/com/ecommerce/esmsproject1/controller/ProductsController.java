package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.Product;
import com.ecommerce.esmsproject1.entity.User;
import com.ecommerce.esmsproject1.repository.ProductsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/products")
    public String showproducts(
            Model model,
            HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String category
    ) {
        //session check
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;

        //category filter with pagination
        if (category != null && !category.isEmpty()) {
            productPage = productsRepository.findByCategory(category, pageable);
        } else {
            productPage = productsRepository.findAll(pageable);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("selectedCategory", category);

        return "products";

//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//
//        if (loggedInUser == null) {
//            //Login nahi hai -> Login page pe bhejo
//            return "redirect:/login";
//        }
//
//        List<Product> productsList = productsRepository.findAll();
//        model.addAttribute("productsList", productsList);
//
//        model.addAttribute("products", productsRepository.findAll());
//
//        System.out.println("Total Products : " + productsList.size());
//        return "products";
    }
}
