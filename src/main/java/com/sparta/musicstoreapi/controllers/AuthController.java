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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class AuthController {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping(value = "/token/add/{email}")
    public String createToken(@PathVariable String email) throws NoSuchAlgorithmException
    {
        Optional<Token> tokenQuery = tokenRepository.findByEmail(email);
        if(tokenQuery.isPresent())
            return null;

        int accessLevel = 0;
        Optional<Employee> result = employeeRepository.findByEmail(email);

        if (result.isPresent()) {
            if (result.get().getTitle().contains("Sales")) {
                accessLevel = 1;
            } else
                accessLevel = 2;
        }

        String sToken = generateToken(email);
        Token token = new Token(sToken, email, accessLevel);
        tokenRepository.save(token);

        return sToken;
    }

    private String generateToken(String email) throws NoSuchAlgorithmException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        Key secretKey = generator.generateKey();

        JwtBuilder builder = Jwts.builder().setSubject(email).signWith(signatureAlgorithm, secretKey);

        return builder.compact();
    }

    public static Integer checkPermissionLevel(TokenRepository tokenRepository, String token) {
        Optional<Token> result = tokenRepository.findByToken(token);

        //returns permission level 0-2, otherwise returns null
        if (result.isPresent()) {
            return result.get().getPermissionLevel();
        } else
            return null;
    }

    @GetMapping(value = "/token/findAll")
    public List<Token> findTokenAll() {
        return tokenRepository.findAll();
    }

    @GetMapping(value = "/token/findByToken/{token}")
    public Token findTokenByToken(@PathVariable String token) {
        Optional<Token> output = tokenRepository.findByToken(token);
        if (output.isEmpty()) {
            return null;
        }
        return output.get();
    }

    @DeleteMapping(value = "/token/deleteById/{id}")
    public ResponseEntity<Integer> deleteTokenById(@PathVariable Integer id) {

        Optional<Token> token = tokenRepository.findById(id);
        if (token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            tokenRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping(value = "/token/deleteByToken/{token}")
    public ResponseEntity<String> deleteByToken(@PathVariable String token)
    {
        Optional<Token> tokenEntity = tokenRepository.findByToken(token);
        if(tokenEntity.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            tokenRepository.deleteById(tokenEntity.get().getId());
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
