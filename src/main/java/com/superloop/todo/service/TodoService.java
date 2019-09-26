package com.superloop.todo.service;

import com.superloop.todo.controller.TodoItemDTO;
import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.repository.TodoItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class TodoService implements ITodoService {
    private TodoItemRepository todoRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TodoService(TodoItemRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    public List<TodoItemDTO> getPendingList() {
        // todo implement get pending list
        return new ArrayList<>();
    }

    public List<TodoItemDTO> getDoneList() {
        // todo implement get done list
        return new ArrayList<>();
    }

    public void addItem(TodoItemDTO newItem) {
        TodoItem itemEntity = convertToEntity(newItem);
        todoRepository.save(itemEntity);
    }

    public TodoItemDTO getItem(Long id) {
        TodoItem item = todoRepository.findTodoItemById(id);
        return convertToDTO(item);
    }

    // edit item
    // - status field must not be edited here!
    public void editItem(TodoItemDTO item) {
        TodoItem itemToBeEdited = todoRepository.findTodoItemById(item.getId());

        if (itemToBeEdited == null) {
            throw new ItemNotFoundException("Todo item with given id not found.");
        }

        // make sure that status does not get edited in this method
        if (!itemToBeEdited.getStatus().equals(item.getStatus())) {
            throw new IllegalEditException("Change to status field not allowed.");
        }

        TodoItem changedItem = convertToEntity(item);
        todoRepository.save(changedItem);
    }

    // mark an item as "done"
    public void markItemAsDone(Long id) {
        // todo implement mark item as done
    }

    public void deleteItem(Long id) {
        // todo implement delete
    }

    public TodoItem convertToEntity(TodoItemDTO itemDTO) {
        return modelMapper.map(itemDTO, TodoItem.class);
    }

    public TodoItemDTO convertToDTO(TodoItem item) {
        return modelMapper.map(item, TodoItemDTO.class);
    }
}
