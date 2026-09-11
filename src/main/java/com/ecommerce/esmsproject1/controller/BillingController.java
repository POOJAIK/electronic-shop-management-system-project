package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.Bill;
import com.ecommerce.esmsproject1.entity.BillItem;
import com.ecommerce.esmsproject1.entity.Product;
import com.ecommerce.esmsproject1.repository.BillItemRepository;
import com.ecommerce.esmsproject1.repository.BillRepository;
import com.ecommerce.esmsproject1.repository.ProductsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/billing")
public class BillingController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private ProductsRepository productsRepository;

    //All Bills List
    @GetMapping
    public String allBills(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null)
            return "redirect:/login";

        model.addAttribute("bills", billRepository.findAll());
        return "admin/bills-list";
    }

    //create bill form
    @GetMapping("/create")
    public String createBillForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null)
            return "redirect:/login";

        model.addAttribute("products", productsRepository.findAll());
        return "admin/create-bill";
    }

    //save bill
    @PostMapping("/save")
    public String saveBill(@RequestParam String customerName,
                           @RequestParam List<Long> productIds,
                           @RequestParam List<Integer> quantities,
                           HttpSession session,
                           RedirectAttributes ra) {
        if (session.getAttribute("loggedInUser") == null)
            return "redirect:/login";

//        pehle check karo koi product select hua?
        boolean anySelected = false;
        for(int qty :quantities) {
            if(qty > 0) {
                anySelected = true;
                break;
            }
        }
        //koi product nahi select hua toh bill mat banao
        if(!anySelected) {
            ra.addFlashAttribute("error", "koi product select nahi kiya!");
            return "redirect:/admin/billing/create";
        }

        Bill bill = new Bill();
        bill.setCustomerName(customerName);

        double total = 0;
        billRepository.save(bill);

        for (int i = 0; i < productIds.size(); i++) {
            Product product = productsRepository.findById(productIds.get(i)).orElseThrow();
            int qty = quantities.get(i);

            if (qty <= 0) continue;

            // stock check karo pahle
            if(qty > product.getStockQty()) {
                ra.addFlashAttribute("error", product.getName() + " ka stock kam hai! ");
                return "redirect:/admin/billing/create";
            }
            BillItem billItem = new BillItem();
            billItem.setBill(bill);
            billItem.setProduct(product);
            billItem.setQuantity(qty);
            billItem.setPrice(product.getPrice() * qty);
            billItemRepository.save(billItem);

            //stock reduce karo
            product.setStockQty(product.getStockQty() - qty);
            productsRepository.save(product);

            total += product.getPrice() * qty;
        }

        bill.setTotalAmount(total);
        billRepository.save(bill);

        ra.addFlashAttribute("success", "Bill successfully create ho gaya!");
        return "redirect:/admin/billing";
    }

    //view single bill
    @GetMapping("/view/{id}")
    public String viewBill(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null)
            return "redirect:/login";

        Bill bill = billRepository.findById(id).orElseThrow();
        model.addAttribute("bill", bill);
        return "admin/view-bill";
    }
}
