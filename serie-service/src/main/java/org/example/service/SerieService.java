package org.example.service;

import org.example.model.Series;
import org.example.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    @Autowired
    public SerieService(SerieRepository serieRepository) { this.serieRepository = serieRepository; }

    public Series findById(String id) {
        return serieRepository.findById(id).orElse(null);
    }

    public List<Series> findAll() { return serieRepository.findAll(); }

    public Series saveSeries(Series series) { return serieRepository.save(series); }
}
