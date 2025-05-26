package com.book.club.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.book.club.demo.exceptions.BadRequestException;
import com.book.club.demo.models.Book;
import com.book.club.demo.models.Reading;
import com.book.club.demo.models.Recommendation;
import com.book.club.demo.models.dtos.BookDTO;
import com.book.club.demo.models.dtos.ReadingDTO;
import com.book.club.demo.repositories.ReadingRepository;
import com.book.club.demo.repositories.RecommendationRepository;

import jakarta.transaction.Transactional;

import com.book.club.demo.repositories.BookRepository;


@Service
public class ReadingService {

    private final BookRepository bookRepository;
    private final ReadingRepository readingRepository;
    private final RecommendationRepository recommendationRepository;

    public ReadingService(BookRepository bookRepository, ReadingRepository readingRepository, 
    RecommendationRepository recommendationRepository) {
        this.bookRepository = bookRepository;
        this.readingRepository = readingRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Transactional
    public String saveReading(ReadingDTO readingDTO) {

        boolean isReadingExists = readingRepository.findByReadingNumber(readingDTO.readingNumber()) != null;
        if(isReadingExists) {
            throw new IllegalArgumentException("Reading already exists.");
        }
        Reading newReading = Reading.builder()
                .readingNumber(readingDTO.readingNumber())
                .startDate(readingDTO.startDate())
                .endDate(readingDTO.endDate())
                .build();
        readingRepository.save(newReading);
        return "Reading saved successfully.";
    }

    @Transactional
    public Book drawBook(Integer readingNumber) {
        Reading currentReading = readingRepository.findByReadingNumber(readingNumber);

        if (currentReading == null) {
            throw new BadRequestException("Reading with number " + readingNumber + " does not exist.");
        }

        if(currentReading.getBook() != null) {
            throw new BadRequestException("Book for reading number " + readingNumber + " has already been selected.");
        }

        List<Recommendation> allRecommendations = recommendationRepository.findByReading_ReadingNumberLessThanEqual(readingNumber);

        List<Reading> allReadings = readingRepository.findAll();

        List<Book> alreadyReadBooks = new ArrayList<>();

        for (Reading reading : allReadings) {
            if(reading.getReadingNumber().equals(1)) {
                continue;
            }

            Book book = bookRepository.findByBookId(reading.getBook().getBookId());
            alreadyReadBooks.add(book);
        }
       
        List<Book> availableBooks = allRecommendations.stream()
                .map(Recommendation::getBook)
                .filter(book -> !alreadyReadBooks.contains(book))
                .distinct()
                .toList();

        if (availableBooks.isEmpty()) {
            throw new BadRequestException("No books available for selection.");
        }
        int randomIndex = (int) (Math.random() * availableBooks.size());

        Book selectedBook = availableBooks.get(randomIndex);
        currentReading.setBook(selectedBook);
        readingRepository.save(currentReading);

        return selectedBook;
    }

    @Transactional
    public List<ReadingDTO> getAllReadings() {
        List<Reading> readings = readingRepository.findAll();
        List<ReadingDTO> readingDTOs = readings.stream()
                .map(reading -> new ReadingDTO(new BookDTO(reading.getBook().getTitle(), reading.getBook().getAuthor()),
                    reading.getReadingNumber(),
                        reading.getStartDate(), 
                        reading.getEndDate()))
                .toList();
        return readingDTOs;
    }
}
