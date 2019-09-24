package com.superloop.todo.service;

import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.repository.TodoItemRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ITodoService {

    void setTodoRepository(TodoItemRepository todoRepository);

    List<TodoItem> getPendingList();

    List<TodoItem> getDoneList();

    void addItem(@Valid TodoItem newItem);

    TodoItem getItem(Long id);

    void editItem(Long id, TodoItem item);

    void markItemAsDone(Long id);

    void deleteTodo(Long id);
}
