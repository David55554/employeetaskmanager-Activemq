/*
 * Service layer for Employee operations
 * Used Claude.ai for validation pattern examples and debugging business logic
 */



package com.company.employeetaskmanager.service;

import com.company.employeetaskmanager.model.Employee;
import com.company.employeetaskmanager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee createEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }

        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        return repository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
    }

    public long getEmployeeCount() {
        return repository.count();
    }

    public List<Employee> getAllEmployeesSorted() {
        return repository.findAllByOrderByNameAsc();
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }

        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setDepartment(employee.getDepartment());
        existing.setSalary(employee.getSalary());

        return repository.save(existing);
    }

    public boolean deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        repository.deleteById(id);
        return true;
    }
}