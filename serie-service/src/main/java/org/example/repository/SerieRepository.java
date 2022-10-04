package org.example.repository;

import org.example.model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieRepository extends MongoRepository<Series, String> {
    List<Series> getByGenre(String genre);
    List<Series> getById(String id);
}
