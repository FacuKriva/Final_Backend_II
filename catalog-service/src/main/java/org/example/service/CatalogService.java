package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.example.model.Catalog;
import org.example.model.DTO.CatalogDTO;
import org.example.model.DTO.MovieDTO;
import org.example.repository.CatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CatalogService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final CatalogRepository cr;
    private final MovieFeignClient mFC;
    private static final Logger logger = LoggerFactory.getLogger(CatalogService.class);

    @Autowired
    public CatalogService(CatalogRepository cr, MovieFeignClient mFC) {
        this.cr = cr;
        this.mFC = mFC;
    }

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

    @RabbitListener(queues = "${queue.movie.name}")
    public String saveMovieToCatalog(MovieDTO movie) {
        logger.info("Se recibe una movie por RabbitMQ y se la guarda en el Catalog");
        String response = "Se guardó la película en el catálogo";
        Optional<Catalog> catalogOptional;
        Catalog catalog;
        String genre = movie.getGenre();

        if (movie.getGenre() == null || movie.getGenre().isEmpty() || movie.getGenre().equals(" ")) {
            response = "es necesario un género para poder continuar con el guardado "
                    + "en el catálogo correspondiente";
        }

        catalogOptional = cr.findByGenre(genre);

        if (!catalogOptional.isPresent()) {
            response = "El catálogo seleccionado no ha sido encontrado";
        }

        catalog = catalogOptional.get();
        catalog.getMovies().add(movie);
        cr.save(catalog);
        return response;
    }

    public CatalogDTO movieFallbackMethod(CallNotPermittedException exception) {
        System.out.println("ERROR :: CIRCUIT BREAKER :: Algo ha fallado en :: MOVIE-SERVICE");
        return new CatalogDTO();
    }
}
