package com.todoapp.todo.service;

import com.todoapp.todo.model.Task;
import com.todoapp.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing tasks in the to-do application.
 * This class interacts with the repository to perform CRUD operations.
 */
@Service // Marks this class as a Spring service component to be managed by the Spring container
public class TaskService {

    private final TaskRepository taskRepository; // Injecting the TaskRepository to interact with the database

    // Constructor to inject the TaskRepository dependency into the TaskService class
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retrieves all tasks from the database.
     * @return A list of all tasks in the system.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Fetches all tasks from the database using the repository
    }

    /**
     * Adds a new task to the database.
     * @param task The task object to be saved.
     * @return The saved task with an assigned ID.
     */
    public Task addTask(Task task) {
        return taskRepository.save(task); // Saves the new task to the database and returns the saved task
    }

    /**
     * Updates an existing task with the new details provided.
     * @param id The ID of the task to be updated.
     * @param updatedTask The updated task details.
     * @return The updated task after saving it.
     * @throws RuntimeException if the task with the given ID is not found.
     */
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id) // Finds the task by ID
                .map(task -> {
                    // If the task is found, update its fields
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task); // Save and return the updated task
                })
                .orElseThrow(() -> new RuntimeException("Task not found")); // If the task is not found, throw an exception
    }

    /**
     * Deletes a task from the database.
     * @param id The ID of the task to be deleted.
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id); // Deletes the task with the given ID from the database
    }

    /**
     * Marks a task as completed.
     * @param id The ID of the task to be marked as completed.
     * @return The updated task with the completed status set to true.
     * @throws RuntimeException if the task with the given ID is not found.
     */
    public Task markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id) // Finds the task by ID
                .orElseThrow(() -> new RuntimeException("Task not found")); // If not found, throw an exception
        task.setCompleted(true); // Set the task's completed status to true
        return taskRepository.save(task); // Save and return the updated task
    }
}