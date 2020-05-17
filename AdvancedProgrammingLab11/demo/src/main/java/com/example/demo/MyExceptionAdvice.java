package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionAdvice {
    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<String>
    handleGenericNotFoundException() {
        return new ResponseEntity<>("{\"error\": \"BAD_REQUEST\"}", HttpStatus.BAD_REQUEST);
    }

}

