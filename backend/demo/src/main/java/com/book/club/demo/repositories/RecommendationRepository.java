package com.book.club.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.club.demo.models.Recommendation;
import com.book.club.demo.models.RecommendationId;

public interface RecommendationRepository extends JpaRepository<Recommendation, RecommendationId>{

    List<Recommendation> findByReading_ReadingNumberLessThanEqual(Integer readingNumber);

    
} 
    
