package com.superloop.todo.service;

import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.repository.TodoItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import org.apache.commons.lang3.RandomStringUtils;

import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {
    @Autowired
    private ITodoService todoService;

    private static final Long MOCK_ITEM_ID = 1L;
    private static final String MOCK_ITEM_NAME = "Mock todo";
    private static final String MOCK_ITEM_STATUS = "Pending";
    private static final LocalDate MOCK_ITEM_DUE_DATE = LocalDate.of(2020, 12, 1);

    private static final String TEST_TODO_NAME = "Test todo name";
    private static final String TEST_TODO_DESCRIPTION = "Test description";
    private static final LocalDate TEST_TODO_DATE = LocalDate.now().plusDays(5);
    private static final String TEST_TODO_STATUS = "Pending";

    @Before
    public void setup() {
        TodoItemRepository mockedRepository = mock(TodoItemRepository.class);

        TodoItem mockedItem = mock(TodoItem.class);
        mockedItem.setId(MOCK_ITEM_ID);
        mockedItem.setName(MOCK_ITEM_NAME);
        mockedItem.setStatus(MOCK_ITEM_STATUS);
        mockedItem.setDueDate(MOCK_ITEM_DUE_DATE);
        doReturn(mockedItem).when(mockedRepository).findTodoItemById(1L);

        todoService.setTodoRepository(mockedRepository);
    }


    @Test
    public void testAddItem() {
        TodoItem testItem = new TodoItem(TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, TEST_TODO_STATUS);

        todoService.addItem(testItem);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddItemPastDueDate() {
        LocalDate invalidDate = LocalDate.now().minusDays(5);

        TodoItem testItem = new TodoItem(TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                invalidDate, TEST_TODO_STATUS);

        todoService.addItem(testItem);
    }


    @Test(expected = InvalidParameterException.class)
    public void testAddItemDone() {
        String invalidStatus = "Done";

        TodoItem testItem = new TodoItem(TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, invalidStatus);

        todoService.addItem(testItem);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddItemDescriptionTooLong() {
        Integer lengthLimit = 500;
        String invalidDescription = RandomStringUtils.randomAlphabetic(lengthLimit + 1);

        TodoItem testItem = new TodoItem(TEST_TODO_NAME, invalidDescription,
                TEST_TODO_DATE, TEST_TODO_STATUS);

        todoService.addItem(testItem);
    }
}