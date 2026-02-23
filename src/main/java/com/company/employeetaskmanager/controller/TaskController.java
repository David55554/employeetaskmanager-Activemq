
/*
 * REST controller for Task endpoints
 * Used Claude.ai to help structure the endpoint methods and handle error cases
 */


package com.company.employeetaskmanager.controller;

import com.company.employeetaskmanager.model.Task;
import com.company.employeetaskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task created = service.createTask(task);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = service.getTaskById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTaskCount() {
        long count = service.getTaskCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Task>> getAllTasksSorted() {
        List<Task> tasks = service.getAllTasksSorted();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Task>> getTasksByEmployeeId(@PathVariable Long employeeId) {
        List<Task> tasks = service.getTasksByEmployeeId(employeeId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        List<Task> tasks = service.getTasksByStatus(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updated = service.updateTask(id, task);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            service.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}