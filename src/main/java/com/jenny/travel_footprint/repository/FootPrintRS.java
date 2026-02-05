package com.jenny.travel_footprint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenny.travel_footprint.modal.FootPrint;

@Repository
public interface FootPrintRS extends JpaRepository<FootPrint, Integer> {
    
    List<FootPrint> findByLocationContaining(String location);
}