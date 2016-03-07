package jibidus.kaamelott;

import javax.persistence.*;

@Entity
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 1024)
    private String text;

    @ManyToOne
    @JoinColumn(name = "character_code")
    private Character character;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "episode_book", referencedColumnName = "book"),
            @JoinColumn(name = "episode_number", referencedColumnName = "number")
    })
    private Episode episode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
