package org.example.service;

import org.example.model.Series;
import org.example.repository.SerieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    @Value("${queue.serie.name}")
    private String seriesEnCola;
    private static SerieRepository serieRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SerieService.class);

    @Autowired
    public SerieService(SerieRepository serieRepository, RabbitTemplate rabbitTemplate) {
        SerieService.serieRepository = serieRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Series> findByGenre(String genre) {
        logger.info("Buscando series según género");
        return serieRepository.getByGenre(genre);
    }

    public static Series saveSeries(Series series) {
        logger.info("Guardando serie");
        return serieRepository.save(series);
    }

    public List<Series> findAll() {
        logger.info("Buscando todas las series");
        return serieRepository.findAll(); }
}
