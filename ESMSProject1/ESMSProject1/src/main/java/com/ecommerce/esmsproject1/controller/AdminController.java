package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.Bill;
import com.ecommerce.esmsproject1.repository.BillRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ecommerce.esmsproject1.entity.Product;
import com.ecommerce.esmsproject1.repository.ProductsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private BillRepository billRepository;

    //admin dashboard - sab products dikho
    @GetMapping
    public String adminDashboard(Model model, HttpSession session){
        //session check
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        // Products
        List<Product> productList  = productsRepository.findAll();
        List<Product> lowStock = productList.stream()
                .filter(p -> p.getStockQty() < 5)
                .collect(Collectors.toList());
        // Bills
        List<Bill> allBills = billRepository.findAll();

        // Total revenue
        double totalRevenue = allBills.stream()
                        .mapToDouble(b -> b.getTotalAmount())
                        .sum();

        //recent 5 bills
        List<Bill> recentBills = allBills.stream()
                        .sorted((a, b) ->b.getCreatedAt().compareTo(a.getCreatedAt()))
                                .limit(5)
                                        .collect(Collectors.toList());
        // Stats
        model.addAttribute("totalProducts", productList.size());
        model.addAttribute("totalBills", allBills.size());
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("lowStockProducts", lowStock);
        model.addAttribute("recentBills", recentBills);
        model.addAttribute("products", productList);

        return "admin/dashboard";
    }

    //add Product form
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session){
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    //Add Product -save to db
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, HttpSession session){
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        productsRepository.save(product);
        return "redirect:/admin";
    }

    //Edit Product Form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session){
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Product product = productsRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "admin/edit-product";
    }

    //Edit Product - Update in DB
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, HttpSession session){

        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        product.setId(id);
        productsRepository.save(product);
        return "redirect:/admin";
    }

    //Delete Product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session){

        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        productsRepository.deleteById(id);
        return "redirect:/admin";
    }

    //stock add
    @PostMapping("/stock/add/{id}")
    public String addProductToStock(@PathVariable Long id,
                                    @RequestParam int qty,
                                    HttpSession session, RedirectAttributes ra){
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Product product1 = productsRepository.findById(id).orElseThrow();
        product1.setStockQty(product1.getStockQty() + qty);
        productsRepository.save(product1);
        ra.addFlashAttribute("success", "Product has been added successfully");
        return "redirect:/admin";
    }

    //stock reduce
    @PostMapping("/stock/reduce/{id}")
    public String reduceStock(@PathVariable Long id,
                              @RequestParam int qty,
                              HttpSession session, RedirectAttributes ra){
        if(session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Product product1 = productsRepository.findById(id).orElseThrow();
        int newStockQty = product1.getStockQty() - qty;
        if(newStockQty < 0) {
            ra.addFlashAttribute("error", "Insufficient stock!");
            return "redirect:/admin";
        }
        product1.setStockQty(newStockQty);
        productsRepository.save(product1);
        ra.addFlashAttribute("success", "Stock reduced!");
        return "redirect:/admin";
    }
}
