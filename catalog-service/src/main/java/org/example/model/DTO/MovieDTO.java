package org.example.model.DTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MovieDTO {

    private Integer id;
    private String movieName;
    private String genre;
    private String urlStream;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getUrlStream() { return urlStream; }
    public void setUrlStream(String urlStream) { this.urlStream = urlStream; }

    @Override
    public String toString() {
        return "MovieDTO {"
                + "id = " + id
                + ", movieName = " + movieName + '\''
                + ", genre = " + genre + '\''
                + ", urlStream = " +urlStream + '\''
                + "}";
    }
}
