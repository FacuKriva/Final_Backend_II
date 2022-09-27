package org.example.model;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;

@Document
@NoArgsConstructor
public class Series {

    @Id
    private String id;
    private String serieName;
    private String genre;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSerieName() { return serieName; }
    public void setSerieName(String serieName) { this.serieName = serieName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public String toString() {
        return "{\"Series\":{"
                + "\"id\":\"" + id + "\""
                + ", \"name\":\"" + serieName + "\""
                + ", \"genre\":\"" + genre + "\""
                + "}}";
    }
}
