package com.book.club.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.club.demo.models.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>{

    Optional<Book> findByTitle(String title);
    Book findByBookId(UUID bookId);
    
}
