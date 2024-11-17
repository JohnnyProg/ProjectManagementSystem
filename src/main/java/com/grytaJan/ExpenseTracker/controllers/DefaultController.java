package com.grytaJan.ExpenseTracker.controllers;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping("/")
    public ResponseEntity<String> mainPage() {
        return new ResponseEntity<String>("Hello world", HttpStatus.OK);
    }

    @GetMapping("/test/auth")
    public ResponseEntity<String> testAuth() {
        return new ResponseEntity<String>("authorized", HttpStatus.OK);
    }

}
