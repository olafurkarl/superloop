package com.superloop.todo.service;

import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.security.InvalidParameterException;
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

    public void setTodoRepository(TodoItemRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // get list of pending items
    public List<TodoItem> getPendingList() {
        // todo implement get pending list
        return new ArrayList<>();
    }

    // get list of done items
    public List<TodoItem> getDoneList() {
        // todo implement get done list
        return new ArrayList<>();
    }

    // add new item
    public void addItem(@Valid TodoItem newItem) {
        if ("Done".equals(newItem.getStatus())) {
            throw new InvalidParameterException("new items must have 'Pending' status");
        }
        todoRepository.save(newItem);
    }

    // get item by id
    public TodoItem getItem(Long id) {
        // todo implement get item
        return null;
    }

    // edit item
    // - status field must not be edited here!
    public void editItem(Long id, TodoItem item) {
        // todo edit item
    }

    // mark an item as "done"
    public void markItemAsDone(Long id) {
        // todo implement mark item as done
    }

    public void deleteTodo(Long id) {
        // todo implement delete
    }
}
