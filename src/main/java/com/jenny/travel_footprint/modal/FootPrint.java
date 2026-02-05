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

@Data                  // Lombok: 自動產生 Getter/Setter, toString 等
@Entity                // 告訴 JPA 這是一個對應資料庫的實體
@Table(name = "FootPrints") // 對應 SQL Server 中的 "FootPrints" 資料表
public class FootPrint {

    @Id // 主鍵 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動跳號 (Identity)
    private Integer id;

    @Column(nullable = false)
    private String title; // 足跡標題

    @Column(length = 1000)
    private String description; // 心得描述

    private String location; // 地點

    private LocalDate travelDate; // 旅遊日期

    // "照片連結" 或 "建立時間" 欄位
    @Column(name = "photoUrl")
    private String photoUrl;
    private LocalDateTime createdAt;
}