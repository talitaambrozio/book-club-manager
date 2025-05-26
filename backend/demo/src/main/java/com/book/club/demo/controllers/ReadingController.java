package com.book.club.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.club.demo.models.Book;
import com.book.club.demo.models.Reading;
import com.book.club.demo.models.dtos.ReadingDTO;
import com.book.club.demo.services.ReadingService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/readings")
public class ReadingController {

    private final ReadingService readingService;
    
    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @PostMapping("/{readingNumber}")
    public ResponseEntity<Book> drawBook(@PathVariable("readingNumber") Integer readingNumber) {

        Book book = readingService.drawBook(readingNumber);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @PostMapping("")
        public ResponseEntity<String> saveReading(@RequestBody ReadingDTO readingDTO) {

        return new ResponseEntity<>(readingService.saveReading(readingDTO), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ReadingDTO>> getAllReadings() {
        return new ResponseEntity<>(readingService.getAllReadings(), HttpStatus.OK);
    }
    
}
