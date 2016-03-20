package jibidus.kaamelott;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EpisodeId implements Serializable {

    private String book;
    private int number;
}
