package com.superloop.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.superloop.todo.repository.TodoItem;
import com.superloop.todo.repository.TodoItemRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {
    @MockBean
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Long MOCK_ITEM_ID = 1L;
    private static final String MOCK_ITEM_NAME = "Mock todo";
    private static final String MOCK_ITEM_STATUS = "Pending";
    private static final String MOCK_ITEM_DESCRIPTION = "Mock description";
    private static final LocalDate MOCK_ITEM_DUE_DATE = LocalDate.of(2020, 12, 1);

    private static final Long TEST_TODO_ID = 2L;
    private static final String TEST_TODO_NAME = "Test todo name";
    private static final String TEST_TODO_DESCRIPTION = "Test description";
    private static final LocalDate TEST_TODO_DATE = LocalDate.now().plusDays(5);
    private static final String TEST_TODO_STATUS = "Pending";

    private static final TodoItem mockedItem =
            new TodoItem(MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                    MOCK_ITEM_DUE_DATE, MOCK_ITEM_STATUS);

    private static final String ADD_ITEM_URL = "/addItem";
    private static final String GET_ITEM_URL = "/getItem";
    private static final String EDIT_ITEM_URL = "/editItem";


    @Before
    public void setup() {
        // Mocking repository response
        mockedItem.setId(MOCK_ITEM_ID);

        given(todoItemRepository.findTodoItemById(MOCK_ITEM_ID))
                .willReturn(mockedItem);

        // Making sure that the mapping for LocalDate happens correctly
        mapper.registerModule(new JavaTimeModule());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
    }


    @Test
    public void testAddItem() throws JsonProcessingException {
        TodoItemDTO testItem = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, TEST_TODO_STATUS);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ADD_ITEM_URL, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddItemPastDueDate() throws JsonProcessingException {
        LocalDate invalidDate = LocalDate.now().minusDays(5);

        TodoItemDTO testItem = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                invalidDate, TEST_TODO_STATUS);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ADD_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testAddItemDone() throws JsonProcessingException {
        String invalidStatus = "Done";

        TodoItemDTO testItem = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, invalidStatus);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ADD_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testAddItemDescriptionTooLong() throws JsonProcessingException {
        Integer lengthLimit = 500;
        String invalidDescription = RandomStringUtils.randomAlphabetic(lengthLimit + 1);

        TodoItemDTO testItem = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, invalidDescription,
                TEST_TODO_DATE, TEST_TODO_STATUS);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ADD_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetItem() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(GET_ITEM_URL)
                .queryParam("itemId", MOCK_ITEM_ID);

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        String mockJson = mapper.writeValueAsString(mockedItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockJson, response.getBody());
    }

    @Test
    public void testEditItem() throws JsonProcessingException {
        TodoItemDTO testItem = new TodoItemDTO(MOCK_ITEM_ID, "Changed name", "Changed description",
                TEST_TODO_DATE, TEST_TODO_STATUS);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(EDIT_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testEditItemIllegalStatus() throws JsonProcessingException {
        // Making a test item with an illegal status field
        TodoItemDTO testItem = new TodoItemDTO(MOCK_ITEM_ID, MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                MOCK_ITEM_DUE_DATE, "Not a legal status");

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(EDIT_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testEditItemChangedStatus() throws JsonProcessingException {
        TodoItem mockedItem2 =
                new TodoItem(MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                        MOCK_ITEM_DUE_DATE, "Done");

        Long mockedItem2Id = 3L;
        mockedItem2.setId(mockedItem2Id);

        given(todoItemRepository.findTodoItemById(mockedItem2Id))
                .willReturn(mockedItem2);

        // Changing the status, should not be allowed here
        TodoItemDTO testItem = new TodoItemDTO(mockedItem2Id, MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                MOCK_ITEM_DUE_DATE, "Pending");

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(EDIT_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testEditItemNotFound() throws JsonProcessingException {
        Long nonExistentId = 123L;

        // DTO has an item that does not exist in the database
        TodoItemDTO testItem = new TodoItemDTO(nonExistentId, MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                MOCK_ITEM_DUE_DATE, MOCK_ITEM_STATUS);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(EDIT_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}