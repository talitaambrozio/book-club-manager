package com.book.club.demo.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.book.club.demo.controllers.dtos.request.ReadingRequestDTO;
import com.book.club.demo.controllers.dtos.response.BookResponseDTO;
import com.book.club.demo.controllers.dtos.response.ReadingResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.club.demo.services.ReadingService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/readings")
public class ReadingController {

    private final ReadingService readingService;
    
    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @PostMapping("/{readingNumber}")
    public ResponseEntity<BookResponseDTO> drawBook(@PathVariable("readingNumber") Integer readingNumber) {

        BookResponseDTO book = readingService.drawBook(readingNumber);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @PostMapping("")
        public ResponseEntity<ReadingResponseDTO> saveReading(@RequestBody ReadingRequestDTO readingDTO) {

        ReadingResponseDTO response = readingService.saveReading(readingDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{readingId}")
                .buildAndExpand(response.readingId())
                .toUri();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ReadingResponseDTO>> getAllReadings() {
        return new ResponseEntity<>(readingService.getAllReadings(), HttpStatus.OK);
    }

    @GetMapping("{readingId}")
    public ResponseEntity<ReadingResponseDTO> getReadingById(@PathVariable("readingId") UUID readingId) {
        return new ResponseEntity<>(readingService.getReadingById(readingId), HttpStatus.OK);
    }
    
}
