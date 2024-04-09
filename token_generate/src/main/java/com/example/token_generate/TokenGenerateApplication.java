package com.example.token_generate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.token_generate.utils.JwtUtil;


@SpringBootApplication
public class TokenGenerateApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(TokenGenerateApplication.class, args);
    
    }

    @Override
    public void run(String... args) {
        // "user_subject" có thể được thay đổi tùy theo yêu cầu của bạn
        String token = JwtUtil.generateToken("user_subject");
        System.out.println("Generated JWT Token: " + token);
    }

}
