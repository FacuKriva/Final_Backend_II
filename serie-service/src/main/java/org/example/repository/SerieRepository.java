package org.example.repository;

import org.example.model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SerieRepository extends MongoRepository<Series, String> { }
