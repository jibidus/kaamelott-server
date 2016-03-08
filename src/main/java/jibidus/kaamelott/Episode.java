package jibidus.kaamelott;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@IdClass(EpisodeId.class)
@Entity
@Data
public class Episode {

    @Id
    private String book;

    @Id
    private int number;

    private String title;
}
