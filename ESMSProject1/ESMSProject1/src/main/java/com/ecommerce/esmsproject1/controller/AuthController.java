package com.ecommerce.esmsproject1.controller;

import com.ecommerce.esmsproject1.entity.PasswordResetToken;
import com.ecommerce.esmsproject1.entity.User;
import com.ecommerce.esmsproject1.repository.PasswordResetTokenRepository;
import com.ecommerce.esmsproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.ecommerce.esmsproject1.service.EmailService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Random;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    //signup code

    @GetMapping("/signup")
    public String showSignupPage() {
        //signup.html
        return "signup";
    }

    //form submit hone pe yahan aayega
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        if (!isValidPassword(user.getPassword())) {
            model.addAttribute("error",
                    "Password must have uppercase,lowercase, number and special characters!");
            return "signup";
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists!");
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));  //BCrypt
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
    public String loginUser(@ModelAttribute User user,
                            jakarta.servlet.http.HttpSession session) {

        System.out.println("Login attempt:" + user.getEmail()); // debug line

        //Database me email se user dhundo
        User existingUser = userRepository.findByEmail(user.getEmail());

        System.out.println(("Found user: " + existingUser));

        //if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            //login successful
        if (existingUser != null && passwordEncoder.matches(
                user.getPassword(), existingUser.getPassword())) {
            session.setAttribute("username", existingUser.getName());
            session.setAttribute("loggedInUser", existingUser);
            //Products page pe bhejo
            return "redirect:/";
        } else {
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

    //forgot password
    @GetMapping("/forgot-password")
    public String showForgotPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam String email,
            Model model) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account found with this email!");
            return "forgot-password";
        }

        tokenRepository.deleteAllByEmail(email);

        String otp = String.valueOf((new Random().nextInt(900000) + 100000));
        PasswordResetToken token = new PasswordResetToken(email, otp);
        tokenRepository.save(token);

        try {
            emailService.sendOtpEmail(email, otp);
            return "redirect:/verify-otp?email=" + email;
        } catch (Exception e) {
            model.addAttribute("error", "Email send failed. check Gmail config");
            return "forgot-password";
        }
    }
//   ---verify otp ---
    @GetMapping("/verify-otp")
    public String showVerifyOtpPage(
            @RequestParam String email,
            Model model) {
        model.addAttribute("email", email);
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String processVerifyOtp(@RequestParam String email,
                                   @RequestParam String otp,
                                   Model model) {

        Optional<PasswordResetToken> tokenOpt =
                tokenRepository.findTopByEmailOrderByIdDesc(email);

        if (tokenOpt.isEmpty()) {
            model.addAttribute("error", "OTP not found. Request again.");
            model.addAttribute("email", email);
            return "verify-otp";
        }

        PasswordResetToken token = tokenOpt.get();

        if (token.isExpired()) {
            model.addAttribute("error", "OTP expired. Request a new one.");
            model.addAttribute("email", email);
            return "verify-otp";
        }

        if (token.isUsed()) {
            model.addAttribute("error", "OTP already used. Request a new one.");
            model.addAttribute("email", email);
            return "verify-otp";
        }

        if (!token.getOtp().equals(otp)) {
            model.addAttribute("error", "Wrong OTP! Try again. ");
            model.addAttribute("email", email);
            return "verify-otp";
        }

        token.setUsed(true);
        tokenRepository.save(token);

        return "redirect:/reset-password?email=" + email;
    }
// --- Reset Password ----
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String email,
                                       @RequestParam String newpassword,
                                       Model model) {

        if (!isValidPassword(newpassword)) {
            model.addAttribute("error", "Password must be at least 8 characters long");
            model.addAttribute("email", email);
            return "reset-password";
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "redirect:/login?error=true";
        }

        user.setPassword(passwordEncoder.encode(newpassword));
        userRepository.save(user);

        tokenRepository.deleteAllByEmail(email);

        return "redirect:/login?reset=true";
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.matches(" .*[!@#$%^&*()_+=\\[\\]{}|;:,<>?/~`-].*");
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}

