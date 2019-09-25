package com.superloop.todo.service;

import com.superloop.todo.controller.TodoItemDTO;
import com.superloop.todo.repository.TodoItem;

import java.util.List;

public interface ITodoService {

    List<TodoItemDTO> getPendingList();

    List<TodoItemDTO> getDoneList();

    void addItem(TodoItem newItem);

    TodoItem getItem(Long id);

    void editItem(TodoItem item);

    void markItemAsDone(Long id);

    void deleteItem(Long id);
}
