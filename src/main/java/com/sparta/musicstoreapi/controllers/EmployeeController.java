package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Employee;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.EmployeeRepository;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chinook")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(value = "/employees/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Employee> getAllEmployees(@PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                return employeeRepository.findAll();
            }
        }
        return null;
    }

    @GetMapping(value = "/employee/{id}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Employee> employee = employeeRepository.findById(id);
                if (employee.isEmpty())
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
                else
                    return ResponseEntity.ok(employee.get());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PostMapping(value = "/employee/add/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee newEmployee, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                employeeRepository.save(newEmployee);
                return ResponseEntity.ok(newEmployee);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/employee/update/{token}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 2) {
                Optional<Employee> results = employeeRepository.findById(employee.getId());
                if (results.isPresent()) {
                    results.get().setFirstName(employee.getFirstName());
                    results.get().setLastName(employee.getLastName());
                    results.get().setTitle(employee.getTitle());
                    results.get().setReportsTo(employee.getReportsTo());
                    results.get().setBirthDate(employee.getBirthDate());
                    results.get().setHireDate(employee.getHireDate());
                    results.get().setAddress(employee.getAddress());
                    results.get().setCity(employee.getCity());
                    results.get().setState(employee.getState());
                    results.get().setCountry(employee.getCountry());
                    results.get().setPostalCode(employee.getPostalCode());
                    results.get().setPhone(employee.getPhone());
                    results.get().setEmail(employee.getEmail());
                    results.get().setFax(employee.getFax());
                    final Employee updatedEmployee = employeeRepository.save(employee);
                    return ResponseEntity.ok(updatedEmployee);
                } else
                    return null;
            }
        }
        return null;
    }
}