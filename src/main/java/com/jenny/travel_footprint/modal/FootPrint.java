package com.jenny.travel_footprint.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "FootPrints")
public class FootPrint {

    @Id // 主鍵 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String location;

    private LocalDate travelDate;
    
    private LocalDate endDate;

    @Column(name = "photoUrl")
    private String photoUrl;
    private LocalDateTime createdAt;
}