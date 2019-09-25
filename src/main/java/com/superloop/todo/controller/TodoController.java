package com.superloop.todo.controller;

import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.service.ITodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class TodoController {
    private final ITodoService todoService;

    private ModelMapper modelMapper;

    @Autowired
    public TodoController(ITodoService todoService, ModelMapper modelMapper) {
        this.todoService = todoService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/addItem")
    public void addItem(@Valid @RequestBody TodoItemDTO itemDTO) {
        todoService.addItem(convertToEntity(itemDTO));
    }

    @GetMapping("/getItem")
    public TodoItemDTO getItem(@RequestParam Long itemId) {
        TodoItem item = todoService.getItem(itemId);
        return convertToDTO(item);
    }

    @GetMapping("/getDoneList")
    public List<TodoItemDTO> getDoneList() {
        return todoService.getDoneList();
    }

    @GetMapping("/getPendingList")
    public List<TodoItemDTO> getPendingList() {
        return todoService.getPendingList();
    }

    @PostMapping("/editItem")
    public void editItem(@Valid @RequestBody TodoItemDTO itemDTO) {
        todoService.editItem(convertToEntity(itemDTO));
    }

    @PostMapping("/markItemAsDone")
    public void markItemAsDone(@RequestParam Long itemId) {
        todoService.markItemAsDone(itemId);
    }

    @PostMapping("/deleteItem")
    public void deleteItem(@RequestParam Long itemId) {
        todoService.deleteItem(itemId);
    }

    private TodoItem convertToEntity(TodoItemDTO itemDTO) {
        return modelMapper.map(itemDTO, TodoItem.class);
    }

    private TodoItemDTO convertToDTO(TodoItem item) {
        return modelMapper.map(item, TodoItemDTO.class);
    }
}
