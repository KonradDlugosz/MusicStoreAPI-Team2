package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Employee;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.EmployeeRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class AuthController
{
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping(value = "/token/add")
    public String createToken(@RequestParam String email) throws NoSuchAlgorithmException
    {
        int accessLevel = 0;
        Optional<Employee> result = employeeRepository.findByEmail(email);

        if(result.isPresent())
        {
            if(result.get().getTitle().contains("Sales"))
            {
                accessLevel = 1;
            }
            else
                accessLevel = 2;
        }

        String sToken = generateToken(email);
        Token token = new Token(sToken, email, accessLevel);
        tokenRepository.save(token);

        return sToken;
    }

    private String generateToken(String email) throws NoSuchAlgorithmException
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        Key secretKey = generator.generateKey();

        JwtBuilder builder = Jwts.builder().setSubject(email).signWith(signatureAlgorithm, secretKey);

        return builder.compact();
    }

    public static Integer checkPermissionLevel(TokenRepository tokenRepository, String token)
    {
        Optional<Token> result = tokenRepository.findByToken(token);

        //returns permission level 0-2, otherwise returns null
        if(result.isPresent())
        {
            return result.get().getPermissionLevel();
        }
        else
            return null;
    }
}
