package com.todoapp.todo;

import com.todoapp.todo.model.Task;
import com.todoapp.todo.repository.TaskRepository;
import com.todoapp.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class TaskServiceTest {

     @Mock
     private TaskRepository taskRepository; // Mock the TaskRepository to isolate the service layer

     @InjectMocks
     private TaskService taskService; // Inject the mock TaskRepository into the TaskService

     private Task task1;
     private Task task2;

     @BeforeEach
     void setUp() {
         // Initialize the mocks and test data before each test
         MockitoAnnotations.openMocks(this); // Initialize the mock objects
         task1 = new Task(1L, "Task 1", "Description for task 1", false);
         task2 = new Task(2L, "Task 2", "Description for task 2", false);
     }

     @Test
     void getAllTasks_ShouldReturnListOfTasks() {
         // Given
         when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2)); // Mock the repository method

         // When
         List<Task> result = taskService.getAllTasks(); // Call the service method

         // Then
         assertNotNull(result); // Assert that the result is not null
         assertEquals(2, result.size()); // Assert that the list contains 2 tasks
         verify(taskRepository, times(1)).findAll(); // Verify that findAll was called once
     }

     @Test
     void addTask_ShouldReturnSavedTask() {
         // Given
         when(taskRepository.save(any(Task.class))).thenReturn(task1); // Mock the save method

         // When
         Task result = taskService.addTask(task1); // Call the service method

         // Then
         assertNotNull(result); // Assert that the result is not null
         assertEquals("Task 1", result.getTitle()); // Assert the title of the returned task
         verify(taskRepository, times(1)).save(any(Task.class)); // Verify that save was called once
     }

     @Test
     void updateTask_ShouldReturnUpdatedTask() {
         // Given
         when(taskRepository.findById(1L)).thenReturn(Optional.of(task1)); // Mock findById to return task1
         when(taskRepository.save(any(Task.class))).thenReturn(task1); // Mock save method

         // Prepare updated task
         Task updatedTask = new Task(1L, "Updated Task 1", "Updated description", true);

         // When
         Task result = taskService.updateTask(1L, updatedTask); // Call the service method

         // Then
         assertNotNull(result); // Assert that the result is not null
         assertEquals("Updated Task 1", result.getTitle()); // Assert the title was updated
         assertTrue(result.isCompleted()); // Assert that the task is marked as completed
         verify(taskRepository, times(1)).findById(1L); // Verify that findById was called once
         verify(taskRepository, times(1)).save(any(Task.class)); // Verify that save was called once
     }

     @Test
     void deleteTask_ShouldCallDeleteById() {
         // Given
         doNothing().when(taskRepository).deleteById(1L); // Mock deleteById to do nothing

         // When
         taskService.deleteTask(1L); // Call the service method

         // Then
         verify(taskRepository, times(1)).deleteById(1L); // Verify that deleteById was called once
     }

     @Test
     void markTaskAsCompleted_ShouldReturnUpdatedTask() {
         // Given
         when(taskRepository.findById(1L)).thenReturn(Optional.of(task1)); // Mock findById to return task1
         when(taskRepository.save(any(Task.class))).thenReturn(task1); // Mock save method

         // When
         Task result = taskService.markTaskAsCompleted(1L); // Call the service method

         // Then
         assertNotNull(result); // Assert that the result is not null
         assertTrue(result.isCompleted()); // Assert that the task is marked as completed
         verify(taskRepository, times(1)).findById(1L); // Verify that findById was called once
         verify(taskRepository, times(1)).save(any(Task.class)); // Verify that save was called once
     }

     @Test
     void updateTask_ShouldThrowExceptionWhenTaskNotFound() {
         // Given
         when(taskRepository.findById(1L)).thenReturn(Optional.empty()); // Mock findById to return empty

         // Prepare updated task
         Task updatedTask = new Task(1L, "Updated Task", "Description", false);

         // When & Then
         assertThrows(RuntimeException.class, () -> taskService.updateTask(1L, updatedTask)); // Assert that an exception is thrown
         verify(taskRepository, times(1)).findById(1L); // Verify that findById was called once
     }
 }