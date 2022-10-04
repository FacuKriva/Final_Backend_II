package org.example.controller;

import org.example.model.Series;
import org.example.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<?> saveSeries(@RequestBody Series series) {
        return ResponseEntity.ok(service.saveSeries(series));
    }

    @PostMapping("/saveSerie")
    public ResponseEntity<String> saveSerieWithRabbit(@RequestBody Series serie){
        SerieService.saveSeries(serie);
        return ResponseEntity.ok("Se agregó la serie a la cola.");
    }
}
