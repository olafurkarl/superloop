package com.superloop.todo.service;

import com.superloop.todo.controller.TodoItemDTO;
import com.superloop.todo.repository.TodoItem;

import java.util.List;

public interface ITodoService {

    List<TodoItemDTO> getPendingList();

    List<TodoItemDTO> getDoneList();

    void addItem(TodoItemDTO newItem);

    TodoItemDTO getItem(Long id);

    void editItem(TodoItemDTO item);

    void markItemAsDone(Long id);

    void deleteItem(Long id);

    TodoItem convertToEntity(TodoItemDTO itemDTO);
    TodoItemDTO convertToDTO(TodoItem item);
}
