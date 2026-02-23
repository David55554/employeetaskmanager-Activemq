package com.company.employeetaskmanager.repository;

import com.company.employeetaskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOrderByDeadlineAsc();

    List<Task> findByEmployeeId(Long employeeId);

    List<Task> findByStatus(String status);
}