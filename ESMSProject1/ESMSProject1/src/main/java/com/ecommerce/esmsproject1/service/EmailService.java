package com.ecommerce.esmsproject1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("ESMS : Password Reset OTP");
        message.setText(
                "Hello,\n\n" +
                 "Your OTP for password reset is:  " + otp + "\n\n"+
                 "This OTP is valid for 10 minutes only.\n" +
                 "Do not share this OTP with anyone.\n\n" +
                 "If you did not request this, ignore this email.\n\n" +
                 "_ ESMS Team"
        );

        mailSender.send(message);
    }
}