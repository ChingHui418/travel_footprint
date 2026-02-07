package com.jenny.travel_footprint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenny.travel_footprint.modal.FootPrint;
import com.jenny.travel_footprint.repository.FootPrintRS;
import com.jenny.travel_footprint.service.FootPrintS;

@RestController
@RequestMapping("/footprints")
@CrossOrigin(origins = "*")
public class FootPrintC {
    @Autowired
    private FootPrintS service;
    @Autowired
    private FootPrintRS repository;

    // 取得所有足跡
    @GetMapping
    public List<FootPrint> getAll() {
        return service.getAllFootPrints();
    }

    // 取得單筆足跡
    @GetMapping("/{id}")
    public ResponseEntity<FootPrint> getById(@PathVariable Integer id) {
        FootPrint footPrint = service.getFootPrintById(id);
        if(footPrint != null) {
            return ResponseEntity.ok(footPrint);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // 新增足跡
    @PostMapping
    public FootPrint create(@RequestBody FootPrint footPrint) {
        return service.createFootPrint(footPrint);
    }

    // 更新足跡
    @PutMapping("/{id}")
    public ResponseEntity<FootPrint> update(@PathVariable Integer id, @RequestBody FootPrint footPrint) {
        FootPrint updated = service.updateFootPrint(id, footPrint);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除足跡
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
