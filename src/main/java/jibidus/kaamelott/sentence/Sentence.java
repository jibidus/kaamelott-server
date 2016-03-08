package jibidus.kaamelott.sentence;

import jibidus.kaamelott.character.Character;
import jibidus.kaamelott.episode.Episode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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

    public Sentence(Character character, Episode episode, String text) {
        this.character = character;
        this.episode = episode;
        this.text = text;
    }
}
