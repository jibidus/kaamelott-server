package jibidus.kaamelott;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class EpisodeId implements Serializable {

    private String book;
    private int number;
}
