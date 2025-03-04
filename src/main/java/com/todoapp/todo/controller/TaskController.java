package com.todoapp.todo.controller;

import com.todoapp.todo.model.Task;
import com.todoapp.todo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller class for managing tasks in the to-do application.
 * It handles HTTP requests and maps them to appropriate methods in the TaskService.
 */
@RestController
@RequestMapping("/task") // This defines the base URL mapping for task-related endpoints (e.g., /task)
@CrossOrigin(origins = "http://localhost:3000") // Allows cross-origin requests from the specified front-end URL
public class TaskController {

    private final TaskService taskService; // Injecting the TaskService to access task-related business logic

    // Constructor-based dependency injection to inject TaskService into the controller
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint to retrieve all tasks.
     * @return A list of all tasks stored in the system.
     */
    @GetMapping // HTTP GET request to fetch all tasks
    public List<Task> getAllTasks() {
        return taskService.getAllTasks(); // Delegates the request to TaskService
    }

    /**
     * Endpoint to add a new task.
     * @param task The task object to be added to the system.
     * @return The created task with its assigned ID.
     */
    @PostMapping // HTTP POST request to add a new task
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task); // Delegates to TaskService to add a new task
    }

    /**
     * Endpoint to update an existing task.
     * @param id The ID of the task to be updated.
     * @param task The updated task details.
     * @return The updated task.
     */
    @PutMapping("/{id}") // HTTP PUT request to update an existing task
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task); // Delegates to TaskService to update the task by ID
    }

    /**
     * Endpoint to delete a task.
     * @param id The ID of the task to be deleted.
     */
    @DeleteMapping("/{id}") // HTTP DELETE request to delete a task
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id); // Delegates to TaskService to delete the task by ID
    }

    /**
     * Endpoint to mark a task as completed.
     * @param id The ID of the task to be marked as completed.
     * @return The updated task after marking it as completed.
     */
    @PatchMapping("/{id}/complete") // HTTP PATCH request to mark a task as completed
    public Task markTaskAsCompleted(@PathVariable Long id) {
        return taskService.markTaskAsCompleted(id); // Delegates to TaskService to mark the task as completed
    }
}