/*
 * Service layer for Task operations
 * Used Claude.ai to understand exception handling and input validation
 */


package com.company.employeetaskmanager.service;

import com.company.employeetaskmanager.model.Task;
import com.company.employeetaskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public Task createTask(Task task) {
        if (task.getTaskName() == null || task.getTaskName().trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }

        if (task.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        if (task.getDeadline() == null || task.getDeadline().trim().isEmpty()) {
            throw new IllegalArgumentException("Deadline is required");
        }

        if (task.getPriority() == null || task.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority is required");
        }

        if (task.getStatus() == null || task.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }

        return repository.save(task);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    public long getTaskCount() {
        return repository.count();
    }

    public List<Task> getAllTasksSorted() {
        return repository.findAllByOrderByDeadlineAsc();
    }

    public List<Task> getTasksByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<Task> getTasksByStatus(String status) {
        return repository.findByStatus(status);
    }

    public Task updateTask(Long id, Task task) {
        Task existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        if (task.getTaskName() == null || task.getTaskName().trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }

        if (task.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        if (task.getDeadline() == null || task.getDeadline().trim().isEmpty()) {
            throw new IllegalArgumentException("Deadline is required");
        }

        if (task.getPriority() == null || task.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority is required");
        }

        if (task.getStatus() == null || task.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }

        existing.setTaskName(task.getTaskName());
        existing.setEmployeeId(task.getEmployeeId());
        existing.setDeadline(task.getDeadline());
        existing.setPriority(task.getPriority());
        existing.setStatus(task.getStatus());

        return repository.save(existing);
    }

    public boolean deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with ID: " + id);
        }
        repository.deleteById(id);
        return true;
    }
}
