package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Employee;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.EmployeeRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping(value = "/chinook/token/add")
    public Token createToken(@RequestParam String email) throws NoSuchAlgorithmException {
        Integer accessLevel = 0;
        Optional<Employee> result = employeeRepository.findByEmail(email);

        if (result.isPresent()) {
            if (result.get().getTitle().contains("Sales")) {
                accessLevel = 1;
            } else
                accessLevel = 2;
        }

        Token token = new Token(generateToken(email), email, accessLevel);
        System.out.println(token);
        tokenRepository.save(token);

        return token;
    }

    private String generateToken(String email) throws NoSuchAlgorithmException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        Key secretKey = generator.generateKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder().setSubject(email).signWith(signatureAlgorithm, secretKey);

        long expirationDelta = 86400000 * 7;
        long expirationMillis = nowMillis + expirationDelta;
        Date exp = new Date(expirationMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    @PostMapping(value = "/chinook/token/findAll")
    public List<Token> findAllToken() throws NoSuchAlgorithmException {
        return tokenRepository.findAll();
    }

    @PostMapping(value = "/chinook/token/delete/{id}")
    public ResponseEntity<Integer> deleteTokenById(@PathVariable Integer id) throws NoSuchAlgorithmException {
          tokenRepository.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
