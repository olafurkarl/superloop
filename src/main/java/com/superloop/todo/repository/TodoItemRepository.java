package com.superloop.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {
    TodoItem findTodoItemById(Long id);
}
