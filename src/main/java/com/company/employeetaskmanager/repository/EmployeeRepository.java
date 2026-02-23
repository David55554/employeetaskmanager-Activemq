package com.company.employeetaskmanager.repository;

import com.company.employeetaskmanager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByOrderByNameAsc();

    List<Employee> findByDepartment(String department);

    Employee findByEmail(String email);
}