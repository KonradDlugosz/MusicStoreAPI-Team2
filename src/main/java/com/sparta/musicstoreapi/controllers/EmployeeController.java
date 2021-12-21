package com.sparta.musicstoreapi.controllers;

import com.sparta.musicstoreapi.entities.Employee;
import com.sparta.musicstoreapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/chinook/allemployees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/chinook/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        else
            return ResponseEntity.ok(employee.get());
    }

    @PostMapping(value = "/chinook/employee/add")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee newEmployee){
        employeeRepository.save(newEmployee);
        return ResponseEntity.ok(newEmployee);
    }

    @PutMapping(value = "/chinook/employees/update")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee){
        Optional<Employee> results = employeeRepository.findById(employee.getId());
        if(results.isPresent()) {
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
        }else
            return null;
    }


}
