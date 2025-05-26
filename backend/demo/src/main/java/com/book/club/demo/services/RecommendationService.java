package com.book.club.demo.services;

import org.springframework.stereotype.Service;

import com.book.club.demo.exceptions.ConflictException;
import com.book.club.demo.exceptions.ResourceNotFoundException;
import com.book.club.demo.models.Book;
import com.book.club.demo.models.Reading;
import com.book.club.demo.models.Recommendation;
import com.book.club.demo.models.RecommendationId;
import com.book.club.demo.models.dtos.BookDTO;
import com.book.club.demo.repositories.BookRepository;
import com.book.club.demo.repositories.ReadingRepository;
import com.book.club.demo.repositories.RecommendationRepository;

import jakarta.transaction.Transactional;

@Service
public class RecommendationService {

    private final BookRepository bookRepository;
    private final RecommendationRepository recommendationRepository;
    private final ReadingRepository readingRepository;

    public RecommendationService(BookRepository bookRepository,
                          RecommendationRepository recommendationRepository, ReadingRepository readingRepository) {
        this.bookRepository = bookRepository;
        this.recommendationRepository = recommendationRepository;
        this.readingRepository = readingRepository;
    }

    @Transactional
    public String saveBookRecommendation(BookDTO bookDto, Integer readingNumber) {

        bookRepository.findByTitle(bookDto.title()).ifPresent(book -> {
            throw new ConflictException("Book already exists.");
        });

        Book book = Book.builder()
                .title(bookDto.title())
                .author(bookDto.author())
                .build();

        bookRepository.save(book);

        Reading reading = readingRepository.findByReadingNumber(readingNumber);
        
        if(reading == null) {
            throw new ResourceNotFoundException("Reading with number " + readingNumber + " does not exist.");
        }

        RecommendationId recommendationId = new RecommendationId(book.getBookId(), reading.getReadingId());

        Recommendation recommendation = Recommendation.builder()
            .id(recommendationId)
            .book(book)
            .reading(reading)
            .build();
        recommendationRepository.save(recommendation);
        return "Book saved successfully.";
    }

}
