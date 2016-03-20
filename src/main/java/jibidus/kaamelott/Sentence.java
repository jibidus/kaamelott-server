package jibidus.kaamelott;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
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

    public Sentence() {
        super();
    }
}
