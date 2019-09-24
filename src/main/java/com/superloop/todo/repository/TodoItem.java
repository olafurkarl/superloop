package com.superloop.todo.repository;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
@Entity
public class TodoItem {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Future
    @Column
    private LocalDate dueDate;

    @Length(max = 500)
    @Column
    private String description;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("Pending")
    private String status;

    public TodoItem(@NotEmpty String name, @Length(max = 500) String description,
                    @FutureOrPresent LocalDate dueDate, @NotNull String status) {
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
