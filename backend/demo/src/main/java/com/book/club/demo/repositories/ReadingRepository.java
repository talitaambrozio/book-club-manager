
package com.book.club.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.club.demo.models.Reading;

import java.util.UUID;

public interface ReadingRepository extends  JpaRepository<Reading, UUID> {

    Reading findByReadingNumber(Integer readingNumber);
    
}
