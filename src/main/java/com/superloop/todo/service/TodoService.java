package com.superloop.todo.service;

import com.superloop.todo.controller.TodoItemDTO;
import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class TodoService implements ITodoService {
    private TodoItemRepository todoRepository;

    @Autowired
    public TodoService(TodoItemRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoItemDTO> getPendingList() {
        // todo implement get pending list
        return new ArrayList<>();
    }

    public List<TodoItemDTO> getDoneList() {
        // todo implement get done list
        return new ArrayList<>();
    }

    public void addItem(TodoItem newItem) {
        todoRepository.save(newItem);
    }

    public TodoItem getItem(Long id) {
        return todoRepository.findTodoItemById(id);
    }

    // edit item
    // - status field must not be edited here!
    public void editItem(TodoItem item) {
        // todo edit item
    }

    // mark an item as "done"
    public void markItemAsDone(Long id) {
        // todo implement mark item as done
    }

    public void deleteItem(Long id) {
        // todo implement delete
    }
}
