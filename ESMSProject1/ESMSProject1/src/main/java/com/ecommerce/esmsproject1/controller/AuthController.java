package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.User;
import com.ecommerce.esmsproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //signup code
    //signup page show karo

    @GetMapping("/signup")
    public String showSignupPage() {
        //signup.html
        return "signup";
    }

    //form submit hone pe yahan aayega
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user); //db me save karo
        return "redirect:/login?registered=true";   //login page pe bhejo
    }

    //--Login--

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";   //login.html
    }

    //login form submit
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, jakarta.servlet.http.HttpSession session) {

        System.out.println("Login attempt:" +user.getEmail()); // debug line

        //Database me email se user dhundo
        User existingUser = userRepository.findByEmail(user.getEmail());

        System.out.println(("Found user: " + existingUser));

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            //login successful
            session.setAttribute("loggedInUser", existingUser);
            //Products page pe bhejo
            return "redirect:/products";
        }else {
            //login failed
            return "redirect:/login?error=true";
        }
    }

    //logout
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
