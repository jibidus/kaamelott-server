package jibidus.kaamelott.episode;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
public class Episode {

    @EmbeddedId
    private EpisodeId id;

    private String title;

    public Episode() {
        this.id = new EpisodeId();
    }

    public Episode(String book, int number, String title) {
        this.id = new EpisodeId(book, number);
        this.title = title;
    }

    public String getBook() {
        return getId().getBook();
    }

    public void setBook(String book) {
        getId().setBook(book);
    }

    public int getNumber() {
        return getId().getNumber();
    }

    public void setNumber(int number) {
        getId().setNumber(number);
    }
}
