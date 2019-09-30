package com.superloop.todo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Validated
public class TodoItemDTO {
    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @FutureOrPresent(message = "Date must not be due in the past")
    private LocalDate dueDate;

    @Length(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    // Status could easily be a boolean "isDone" sort of value,
    // however I've decided on a String type to allow new statuses in the future
    @Pattern(regexp = "Pending|Done", flags = Pattern.Flag.UNICODE_CASE)
    private String status = "Pending";

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
