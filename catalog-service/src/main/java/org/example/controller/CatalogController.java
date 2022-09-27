package org.example.controller;

import org.example.model.DTO.CatalogDTO;
import org.example.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping( "/catalogs" )
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) { this.catalogService = catalogService; }

    @GetMapping( "/{genre}" )
    public ResponseEntity<CatalogDTO> getMovieCatalogByGenre(@PathVariable String genre) {
        CatalogDTO catalog = catalogService.getMovieCatalogByGenre(genre);
        return Objects.isNull(catalog)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    @GetMapping( "/errors/{genre}" )
    public ResponseEntity<CatalogDTO> getMovieCatalogByGenreOrThrowError(
            @PathVariable String genre,
            @RequestParam Boolean throwError) {
        CatalogDTO catalog =catalogService.getMovieCatalogByGenreOrThrowError(genre, throwError);
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }
}
