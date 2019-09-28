package com.superloop.todo.controller;

import com.superloop.todo.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class TodoController {
    private final ITodoService todoService;

    @Autowired
    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/addItem")
    public void addItem(@Valid @RequestBody TodoItemDTO itemDTO) {
        todoService.addItem(itemDTO);
    }

    @GetMapping("/getItem")
    public TodoItemDTO getItem(@RequestParam Long itemId) {
        return todoService.getItem(itemId);
    }

    @GetMapping("/getDoneList")
    public List<TodoItemDTO> getDoneList() {
        return todoService.getDoneList();
    }

    @GetMapping("/getPendingList")
    public List<TodoItemDTO> getPendingList() {
        return todoService.getPendingList();
    }

    @GetMapping("/getList")
    public List<TodoItemDTO> getList() {
        return todoService.getList();
    }

    @PostMapping("/editItem")
    public void editItem(@Valid @RequestBody TodoItemDTO itemDTO) {
        todoService.editItem(itemDTO);
    }

    @PostMapping("/markItemAsDone")
    public void markItemAsDone(@RequestParam Long itemId) {
        todoService.markItemAsDone(itemId);
    }

    @PostMapping("/deleteItem")
    public void deleteItem(@RequestParam Long itemId) {
        todoService.deleteItem(itemId);
    }

}
