package com.book.club.demo.services;

import java.util.List;
import java.util.UUID;

import com.book.club.demo.controllers.dtos.mapper.BookMapper;
import com.book.club.demo.controllers.dtos.mapper.RecommendationMapper;
import com.book.club.demo.controllers.dtos.request.BookRequestDTO;
import org.springframework.stereotype.Service;

import com.book.club.demo.exceptions.ConflictException;
import com.book.club.demo.exceptions.ResourceNotFoundException;
import com.book.club.demo.models.Book;
import com.book.club.demo.models.Reading;
import com.book.club.demo.models.Recommendation;
import com.book.club.demo.controllers.dtos.response.BookResponseDTO;
import com.book.club.demo.controllers.dtos.response.RecommendationResponseDTO;
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
    public BookResponseDTO saveBookRecommendation(BookRequestDTO bookDto, Integer readingNumber) {

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

        Recommendation recommendation = Recommendation.builder()
            .book(book)
            .reading(reading)
            .build();
        recommendationRepository.save(recommendation);
        return BookMapper.INSTANCE.bookToDTO(book);
    }

    public RecommendationResponseDTO getRecommendationById(UUID recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Recommendation not found with Id: %s .", recommendationId)));

        return RecommendationMapper.INSTANCE.recommendationToDTO(recommendation);
    }

    public List<RecommendationResponseDTO> getAllRecommendations() {
        List<Recommendation> recommendations = recommendationRepository.findAll();
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found.");
        }

        return RecommendationMapper.INSTANCE.recommendationsToDTOs(recommendations);
    }

}
