package com.superloop.todo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalEditException extends RuntimeException {
    public IllegalEditException(String msg) {
        super("Illegal edit exception: " + msg);
    }
}
