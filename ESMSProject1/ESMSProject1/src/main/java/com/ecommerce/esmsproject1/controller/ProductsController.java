package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.Product;
import com.ecommerce.esmsproject1.entity.User;
import com.ecommerce.esmsproject1.repository.ProductsRepository;
import jakarta.servlet.http.HttpSession;
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
    public String showproducts(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            //Login nahi hai -> Login page pe bhejo
            return "redirect:/login";
        }

        List<Product> productsList = productsRepository.findAll();
        model.addAttribute("productsList", productsList);

        System.out.println("Total Products : " + productsList.size());
        return "products";
    }
}
