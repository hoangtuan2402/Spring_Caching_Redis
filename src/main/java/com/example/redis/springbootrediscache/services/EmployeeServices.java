package com.example.redis.springbootrediscache.services;

import com.example.redis.springbootrediscache.ResouceNotFoundException;
import com.example.redis.springbootrediscache.model.Employee;
import com.example.redis.springbootrediscache.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServices {
    private final EmployeeRepository employeeRepository;


    @Cacheable("employees")
    public List<Employee> findAll() {
        log.info("Load into Get All Method");
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employees", key = "#employeeId")
    public Employee findById(Integer employeeId) {
        log.info("Load into Find Method");
        return employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResouceNotFoundException("Employee not found" + employeeId));
    }

    @CachePut(value = "employees", key = "#employeeId")
    public Employee updateEmployee(int employeeId, Employee employeeDetails) {
        log.info("Load into Update Method");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResouceNotFoundException("Employee not found for this id :: " + employeeId));
        employee.setName(employeeDetails.getName());
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(int employeeId) {
        log.info("Load into Delete Method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResouceNotFoundException("Employee not found" + employeeId));
        employeeRepository.delete(employee);

    }

    @CachePut("employees")
    public Employee createEmployee(Employee employee) {
        log.info("Load into create Method");
        return employeeRepository.save(employee);
    }
}
