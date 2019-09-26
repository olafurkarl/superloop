package com.superloop.todo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IllegalEditException extends RuntimeException {
    IllegalEditException(String msg) {
        super("Illegal edit exception: " + msg);
    }
}
