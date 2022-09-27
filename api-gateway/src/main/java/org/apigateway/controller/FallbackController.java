package org.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping( "/fallback" )
public class FallbackController {

    @CircuitBreaker( name = "movieService" )
    @GetMapping( "/movies" )
    public ResponseEntity< String > moviesFallback() {
        return new ResponseEntity<>( "No ha sido posible conectarse al servidor de películas.",
                HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @CircuitBreaker( name = "catalogService" )
    @GetMapping( "/catalogs" )
    public ResponseEntity< String > catalogFallback() {
        return new ResponseEntity<>( "Los catálogos no se encuentran disponibles en estos momentos.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CircuitBreaker ( name = "seriesService" )
    @GetMapping( "/series" )
    public ResponseEntity< String > seriesFallback() {
        return new ResponseEntity<>( "No se pudieron encontrar series debido a un error de conexión.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
