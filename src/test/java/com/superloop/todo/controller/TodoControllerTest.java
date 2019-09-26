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
import java.util.ArrayList;
import java.util.List;

import static com.superloop.todo.service.TodoService.STATUS_DONE;
import static com.superloop.todo.service.TodoService.STATUS_PENDING;
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
    private static final String MOCK_ITEM_DESCRIPTION = "Mock description";
    private static final LocalDate MOCK_ITEM_DUE_DATE = LocalDate.of(2020, 12, 1);

    private static final Long TEST_TODO_ID = 2L;
    private static final String TEST_TODO_NAME = "Test todo name";
    private static final String TEST_TODO_DESCRIPTION = "Test description";
    private static final LocalDate TEST_TODO_DATE = LocalDate.now().plusDays(5);

    private static final TodoItem mockedItem =
            new TodoItem(MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                    MOCK_ITEM_DUE_DATE, STATUS_PENDING);

    private static List<TodoItem> mockedPendingList;

    private static List<TodoItem> mockedDoneList;

    private static final String BASE_URL = "/api/v1";
    private static final String ADD_ITEM_URL = BASE_URL + "/addItem";
    private static final String GET_ITEM_URL = BASE_URL + "/getItem";
    private static final String EDIT_ITEM_URL = BASE_URL + "/editItem";
    private static final String MARK_ITEM_AS_DONE_URL = BASE_URL + "/markItemAsDone";
    private static final String DELETE_ITEM_URL = BASE_URL + "/deleteItem";
    private static final String GET_DONE_LIST_URL = BASE_URL + "/getDoneList";
    private static final String GET_PENDING_LIST_URL = BASE_URL + "/getPendingList";

    @Before
    public void setup() {
        // Mocking repository response
        mockedItem.setId(MOCK_ITEM_ID);

        given(todoItemRepository.findTodoItemById(MOCK_ITEM_ID))
                .willReturn(mockedItem);

        mockedDoneList = new ArrayList<>();

        // creating mocked list of done items
        for (int i = 10; i < 15; i++) {
            TodoItem mockedItemDone = new TodoItem(MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                    MOCK_ITEM_DUE_DATE, STATUS_DONE);
            mockedItemDone.setId(Integer.toUnsignedLong(i));
            mockedDoneList.add(mockedItemDone);
        }

        mockedPendingList = new ArrayList<>();

        // creating mocked list of pending items
        for (int i = 15; i < 20; i++) {
            TodoItem mockedItemPending = new TodoItem(MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                    MOCK_ITEM_DUE_DATE, STATUS_PENDING);
            mockedItemPending.setId(Integer.toUnsignedLong(i));
            mockedPendingList.add(mockedItemPending);
        }


        given(todoItemRepository.findAllByStatus(STATUS_PENDING)).willReturn(mockedPendingList);

        given(todoItemRepository.findAllByStatus(STATUS_DONE)).willReturn(mockedDoneList);

        // Making sure that the mapping for LocalDate happens correctly
        mapper.registerModule(new JavaTimeModule());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
    }


    @Test
    public void testAddItem() throws JsonProcessingException {
        TodoItemDTO testItem = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, STATUS_PENDING);

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
                invalidDate, STATUS_PENDING);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ADD_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testAddItemDone() throws JsonProcessingException {
        TodoItemDTO testItem = new TodoItemDTO(TEST_TODO_ID, TEST_TODO_NAME, TEST_TODO_DESCRIPTION,
                TEST_TODO_DATE, STATUS_DONE);

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
                TEST_TODO_DATE, STATUS_PENDING);

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
                TEST_TODO_DATE, STATUS_PENDING);

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
                        MOCK_ITEM_DUE_DATE, STATUS_DONE);

        Long mockedItem2Id = 3L;
        mockedItem2.setId(mockedItem2Id);

        given(todoItemRepository.findTodoItemById(mockedItem2Id))
                .willReturn(mockedItem2);

        // Changing the status, should not be allowed here
        TodoItemDTO testItem = new TodoItemDTO(mockedItem2Id, MOCK_ITEM_NAME, MOCK_ITEM_DESCRIPTION,
                MOCK_ITEM_DUE_DATE, STATUS_PENDING);

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
                MOCK_ITEM_DUE_DATE, STATUS_PENDING);

        String json = mapper.writeValueAsString(testItem);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(EDIT_ITEM_URL, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testMarkItemAsDone() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(MARK_ITEM_AS_DONE_URL)
                .queryParam("itemId", MOCK_ITEM_ID);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), MOCK_ITEM_ID, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testMarkItemAsDoneItemNotFound() {
        Long nonExistentId = 123L;

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(MARK_ITEM_AS_DONE_URL)
                .queryParam("itemId", nonExistentId);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), nonExistentId, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteItem() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(DELETE_ITEM_URL)
                .queryParam("itemId", MOCK_ITEM_ID);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), MOCK_ITEM_ID, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteItemNotFound() {
        Long nonExistentId = 123L;

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(DELETE_ITEM_URL)
                .queryParam("itemId", nonExistentId);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), nonExistentId, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetDoneList() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(GET_DONE_LIST_URL);

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        String mockJson = mapper.writeValueAsString(mockedDoneList);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockJson, response.getBody());
    }

    @Test
    public void testGetPendingList() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(GET_PENDING_LIST_URL);

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        String mockJson = mapper.writeValueAsString(mockedPendingList);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockJson, response.getBody());
    }
}