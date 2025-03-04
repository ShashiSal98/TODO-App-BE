package com.todoapp.todo;


import com.todoapp.todo.controller.TaskController;
import com.todoapp.todo.model.Task;
import com.todoapp.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class TaskControllerTest {

     @Mock
     private TaskService taskService;

     @InjectMocks
     private TaskController taskController;

     private Task task1;
     private Task task2;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test method is executed
        MockitoAnnotations.openMocks(this);

        // Create mock tasks for testing. These will be used in different tests.
        task1 = new Task(1L, "Task 1", "Description for task 1", false);
        task2 = new Task(2L, "Task 2", "Description for task 2", false);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // Arrange: Create a list of tasks (task1 and task2) to be returned by the mocked service
        List<Task> tasks = Arrays.asList(task1, task2);

        // Mock the getAllTasks method in the service to return the list of tasks
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act: Call the getAllTasks method in the controller to retrieve the list of tasks
        List<Task> result = taskController.getAllTasks();

        // Assert: Ensure that the result is not null, confirming the task list was returned
        assertNotNull(result);

        // Assert: Verify that the size of the list is 2, indicating that two tasks were returned
        assertEquals(2, result.size());

        // Assert: Verify that the getAllTasks method in the service was called exactly once
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void addTask_ShouldReturnSavedTask() {
        // Arrange: Mock the addTask method in the service to return 'task1' when any Task object is passed
        when(taskService.addTask(any(Task.class))).thenReturn(task1);

        // Act: Call the addTask method in the controller with 'task1' as the task to be added
        Task result = taskController.addTask(task1);

        // Assert: Ensure that the result is not null, confirming that a task was returned
        assertNotNull(result);

        // Assert: Verify that the title of the returned task is "Task 1", which is the expected value
        assertEquals("Task 1", result.getTitle());

        // Assert: Verify that the addTask method in the service was called exactly once with any Task object
        verify(taskService, times(1)).addTask(any(Task.class));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        // Arrange: Mock the updateTask method in the service to return 'task1' when called with task ID 1L and any Task object
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(task1);

        // Act: Call the updateTask method in the controller with task ID 1L and 'task1' as the updated task
        Task result = taskController.updateTask(1L, task1);

        // Assert: Ensure that the result is not null
        assertNotNull(result);

        // Assert: Verify that the title of the result is "Task 1" (expected value)
        assertEquals("Task 1", result.getTitle());

        // Assert: Verify that the updateTask method in the service was called exactly once with task ID 1L and any Task object
        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void deleteTask_ShouldReturnVoid() {
        // Arrange: Mock the deleteTask method in the service to do nothing when called with task ID 1L
        doNothing().when(taskService).deleteTask(1L);

        // Act: Call the deleteTask method in the controller
        taskController.deleteTask(1L);

        // Assert: Verify that the deleteTask method in the service was called exactly once with task ID 1L
        verify(taskService, times(1)).deleteTask(1L);
    }

     @Test
    void markTaskAsCompleted_ShouldReturnUpdatedTask_WhenTaskIsNotCompleted() {
        // Arrange: Set up the scenario where the task is not completed
        Task taskNotCompleted = new Task();
        taskNotCompleted.setCompleted(false);

        // Mock the service method to return a task that is not completed
        when(taskService.markTaskAsCompleted(1L)).thenReturn(taskNotCompleted);

        // Act: Call the controller method
        Task result = taskController.markTaskAsCompleted(1L);

        // Assert: Check that the result is not null and the task is not completed
        assertNotNull(result);
        assertFalse(result.isCompleted()); // This verifies that the task is not completed initially
        verify(taskService, times(1)).markTaskAsCompleted(1L);
    }

    @Test
    void markTaskAsCompleted_ShouldReturnUpdatedTask_WhenTaskIsAlreadyCompleted() {
        // Arrange: Set up the scenario where the task is already completed
        Task taskCompleted = new Task();
        taskCompleted.setCompleted(true);

        // Mock the service method to return a task that is already completed
        when(taskService.markTaskAsCompleted(1L)).thenReturn(taskCompleted);

        // Act: Call the controller method
        Task result = taskController.markTaskAsCompleted(1L);

        // Assert: Check that the result is not null and the task is completed
        assertNotNull(result);
        assertTrue(result.isCompleted()); // This verifies that the task is completed
        verify(taskService, times(1)).markTaskAsCompleted(1L);
    }

}
