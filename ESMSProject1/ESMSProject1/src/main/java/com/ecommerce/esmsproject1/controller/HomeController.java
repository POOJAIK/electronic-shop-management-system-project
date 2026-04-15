package com.ecommerce.esmsproject1.controller;

import org.springframework.ui.Model;
import com.ecommerce.esmsproject1.entity.Product;
import com.ecommerce.esmsproject1.repository.ProductsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.ecommerce.esmsproject1.repository.UserRepository;
import com.ecommerce.esmsproject1.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = productsRepository.findAll()
                .stream()
                .distinct()
                .limit(6)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "home";
    }

//    @GetMapping("/signup")
//    public String showSignupPage() {
//        return "signup";
//    }

//    @PostMapping("/register")
//    public String registerUser(
//            @RequestParam String name,
//            @RequestParam String email,
//            @RequestParam String password,
//            @RequestParam String address) {
//
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(password);
//        user.setAddress(address);
//
//        userRepository.save(user);
//
//        return "redirect:/";
//    }
}
