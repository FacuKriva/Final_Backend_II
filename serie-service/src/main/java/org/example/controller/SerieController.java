package org.example.controller;

import jdk.jfr.internal.Repository;
import org.example.model.Series;
import org.example.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping( "/series" )
public class SerieController {

    private final SerieService service;

    @Autowired
    public SerieController ( SerieService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Series> seriesList = service.findAll();
        return seriesList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(seriesList);
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<?>findById(@PathVariable String id) {
        Series series = service.findById(id);
        return Objects.isNull(series)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(series);
    }

    @PostMapping
    public ResponseEntity<?> saveSeries(@RequestBody Series series) {
        return ResponseEntity.ok(service.saveSeries(series));
    }
}
