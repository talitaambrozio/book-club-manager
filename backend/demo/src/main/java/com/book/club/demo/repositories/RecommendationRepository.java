package com.book.club.demo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.club.demo.models.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID>{

    List<Recommendation> findByReading_ReadingNumberLessThanEqual(Integer readingNumber);
    
} 
    
