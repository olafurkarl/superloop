package com.superloop.todo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemNotFoundException extends RuntimeException {
    ItemNotFoundException(String msg) {
        super("Not found exception: " + msg);
    }
}
