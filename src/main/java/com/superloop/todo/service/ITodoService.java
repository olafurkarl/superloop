package com.superloop.todo.service;

import com.superloop.todo.repository.Todo;

import java.util.List;

public interface ITodoService {

    List<Todo> getPendingList();

    List<Todo> getDoneList();

    boolean addItem(Todo item);

    Todo getItem(Long id);

    boolean editItem(Long id, Todo item);

    boolean markItemAsDone(Long id);

    boolean deleteTodo(Long id);
}
