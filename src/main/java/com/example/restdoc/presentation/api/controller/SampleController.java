package com.example.restdoc.presentation.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @RequestMapping("/")
  public ResponseEntity<String> hello() {
    return new ResponseEntity("hello world", HttpStatus.OK);
  }
}
