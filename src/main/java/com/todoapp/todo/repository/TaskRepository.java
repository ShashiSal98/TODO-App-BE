package com.todoapp.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todoapp.todo.model.Task;


@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
}
