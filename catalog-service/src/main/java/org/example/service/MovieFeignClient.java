package org.example.service;

import org.example.config.LoadBalancerConfig;
import org.example.model.DTO.MovieDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient( name = "movie-service" )
@LoadBalancerClient( name = "movie-service", configuration = LoadBalancerConfig.class)
public interface MovieFeignClient {

    @GetMapping( "/movies/{genre}" )
    ResponseEntity<List<MovieDTO>> getCatalogByGenre(@PathVariable String genre);

    @GetMapping ( "/movies/withErrors/{genre}")
    ResponseEntity<List<MovieDTO>> getCatalogByGenreWithThrowError(@PathVariable( value = "genre") String genre,
                                                                 @RequestParam("throwError") boolean throwError);
}
