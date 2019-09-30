package com.superloop.todo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IllegalAddException extends RuntimeException {
    IllegalAddException(String msg) {
        super("Illegal add exception: " + msg);
    }
}
