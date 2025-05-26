package com.book.club.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "readings")
public class Reading implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID readingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    private Integer readingNumber;
    private LocalDate startDate;
    private LocalDate endDate;
}
