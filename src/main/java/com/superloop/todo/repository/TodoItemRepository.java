package com.superloop.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {
    TodoItem findTodoItemById(Long id);
    List<TodoItem> findAllByStatus(String status);
}
