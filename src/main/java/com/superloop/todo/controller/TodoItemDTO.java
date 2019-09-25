package com.superloop.todo.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Validated
public class TodoItemDTO {
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @FutureOrPresent(message = "Date must not be due in the past")
    private LocalDate dueDate;

    @Length(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Pattern(regexp = "Pending", flags = Pattern.Flag.UNICODE_CASE)
    private String status;

    public TodoItemDTO() {
    }

    public TodoItemDTO(Long id, String name, String description, LocalDate dueDate, String status) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
