package com.jenny.travel_footprint.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jenny.travel_footprint.modal.FootPrint;
import com.jenny.travel_footprint.repository.FootPrintRS;

@Service
public class FootPrintS {

    @Autowired
    private FootPrintRS repository;

    // 查詢全部足跡
    public List<FootPrint> getAllFootPrints() {
        return repository.findAll();
    }

    // 查詢單筆足跡
    public FootPrint getFootPrintById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // 新增足跡
    public FootPrint createFootPrint(FootPrint footPrint) {
        footPrint.setCreatedAt(LocalDateTime.now());
        return repository.save(footPrint);
    }

    // 更新足跡
    public FootPrint updateFootPrint(Integer id, FootPrint newFootPrint) {
        return repository.findById(id)
            .map(existing -> {
                existing.setLocation(newFootPrint.getLocation());
                existing.setDescription(newFootPrint.getDescription());
                existing.setTitle(newFootPrint.getTitle());
                existing.setTravelDate(newFootPrint.getTravelDate());
                existing.setPhotoUrl(newFootPrint.getPhotoUrl());
                return repository.save(existing);
        })
        .orElse(null);
    }
}
