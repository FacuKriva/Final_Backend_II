package org.example.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.example.model.DTO.CatalogDTO;
import org.example.model.DTO.MovieDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CatalogService {

    private final MovieFeignClient mFC;
    private static final Logger logger = LoggerFactory.getLogger(CatalogService.class);

    @Autowired
    public CatalogService(MovieFeignClient mFC) { this.mFC = mFC; }

    public CatalogDTO getMovieCatalogByGenre(String genre) {
        logger.info("");
        ResponseEntity<List<MovieDTO>> movieResponse = mFC.getCatalogByGenre(genre);
        if (movieResponse.getStatusCode().is2xxSuccessful() && !Objects.isNull(movieResponse.getBody())) {
            CatalogDTO catalog = new CatalogDTO();
            catalog.setGenre(genre);
            catalog.setMovies(movieResponse.getBody());
            return catalog;
        }
        return null;
    }

    public CatalogDTO movieFallback(CallNotPermittedException e) {
        System.out.println("Algo falló en el servicio de películas");
        return new CatalogDTO();
    }

    @CircuitBreaker(name = "movie", fallbackMethod = "movieFallback")
    @Retry(name = "movie")
    public CatalogDTO getMovieCatalogByGenreOrThrowError(String genre, Boolean throwError) {
        logger.info("");
        ResponseEntity<List<MovieDTO>> movieResponse = mFC.getCatalogByGenreWithThrowError(genre, throwError);
        CatalogDTO catalog = new CatalogDTO();

        if(movieResponse.getStatusCode().is2xxSuccessful() && !Objects.isNull(movieResponse.getBody())){
            catalog.setGenre(genre);
            catalog.setMovies(movieResponse.getBody());
        }
        return catalog;
    }
}
