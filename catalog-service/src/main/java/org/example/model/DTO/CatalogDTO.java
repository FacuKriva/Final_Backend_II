package org.example.model.DTO;

import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
public class CatalogDTO {

    private String genre;
    private List<MovieDTO> movies;

    public CatalogDTO(String genre, List<MovieDTO> movies) {
        this.genre = genre;
        this.movies = movies;
    }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public List<MovieDTO> getMovies() { return movies; }
    public void setMovies(List<MovieDTO> movies) { this.movies = movies; }

    @Override
    public String toString(){
        return "CatalogDTO{ "
                + "genre = " + genre + '\''
                + ", movies = " + movies
                + '}';
    }
}
