package com.example.redis.springbootrediscache.controller;


import com.example.redis.springbootrediscache.model.Employee;
import com.example.redis.springbootrediscache.services.EmployeeServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
public class EmployeeController {

    private EmployeeServices employeeServices;

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeServices.createEmployee(employee));
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeServices.findAll());
    }

    @GetMapping("employees/{employeeId}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        System.out.println("Employee fetching from database:: " + employeeId);
        return ResponseEntity.ok(employeeServices.findById(employeeId));

    }


    @PutMapping("employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                                                   @RequestBody Employee employeeDetails) {
        return ResponseEntity.ok(employeeServices.updateEmployee(employeeId, employeeDetails));
    }


    @DeleteMapping("employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "id") Integer employeeId) {
        employeeServices.deleteEmployee(employeeId);
        return ResponseEntity.ok("Deleted");
    }
}
