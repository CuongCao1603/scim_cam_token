package com.example.token_generate.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.token_generate.model.Account;
import com.example.token_generate.repository.AccountRepository;

@Component
public class JwtUtil {
    private static String SECRET_KEY;

    private final AccountRepository accountRepository;

    @Autowired
    public JwtUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    private void init() {
        // Lấy secret key từ cơ sở dữ liệu và thiết lập cho SECRET_KEY
        Account account = accountRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Account not found"));
        setSecretKey(account.getSecretKey());
    }

    public static void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    public static String generateToken(String subject) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTime = 600000; // 10 minutes in milliseconds

        return Jwts.builder()
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true; // Nếu không có lỗi, token hợp lệ
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        } catch (Exception e) {
            System.out.println("JWT Token is invalid");
        }
        return false; // Trong trường hợp có lỗi, token không hợp lệ hoặc đã hết hạn
    }

    public String getUsernameFromToken(String token, String secretKey) {
        return getClaimFromToken(token, Claims::getSubject, secretKey);
    }

    public Date getExpirationDateFromToken(String token, String secretKey) {
        return getClaimFromToken(token, Claims::getExpiration, secretKey);
    }

    public Boolean isTokenExpired(String token, String secretKey) {
        final Date expiration = getExpirationDateFromToken(token, secretKey);
        return expiration.before(new Date());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String secretKey) {
        final Claims claims = getAllClaimsFromToken(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
