package com.superloop.todo.service;

import com.superloop.todo.controller.TodoItemDTO;
import com.superloop.todo.repository.TodoItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {

    @Autowired
    TodoService todoService;

    private static final Long TEST_TODO_ID = 1L;
    private static final String TEST_TODO_NAME = "Test todo name";
    private static final String TEST_TODO_DESCRIPTION = "Test description";
    private static final LocalDate TEST_TODO_DATE = LocalDate.now().plusDays(5);
    private static final String TEST_TODO_STATUS = "Pending";

    @Test
    public void testConvertToEntity() {
        TodoItemDTO testItemDTO = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, TEST_TODO_STATUS);

        TodoItem testItem = todoService.convertToEntity(testItemDTO);

        assertNotNull(testItem);
        assertEquals(testItem.getId(), testItemDTO.getId());
        assertEquals(testItem.getName(), testItemDTO.getName());
        assertEquals(testItem.getDescription(), testItemDTO.getDescription());
        assertEquals(testItem.getDueDate(), testItemDTO.getDueDate());
        assertEquals(testItem.getStatus(), testItemDTO.getStatus());
    }

    @Test
    public void testConvertToDTO() {
        TodoItem testItem = new TodoItem(TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, TEST_TODO_STATUS);

        testItem.setId(TEST_TODO_ID);

        TodoItemDTO testItemDTO = todoService.convertToDTO(testItem);

        assertNotNull(testItemDTO);
        assertEquals(testItem.getId(), testItemDTO.getId());
        assertEquals(testItem.getName(), testItemDTO.getName());
        assertEquals(testItem.getDescription(), testItemDTO.getDescription());
        assertEquals(testItem.getDueDate(), testItemDTO.getDueDate());
        assertEquals(testItem.getStatus(), testItemDTO.getStatus());
    }
}
