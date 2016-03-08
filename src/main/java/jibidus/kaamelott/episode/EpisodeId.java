package jibidus.kaamelott.episode;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeId implements Serializable {

    public static final Converter<String, EpisodeId> CONVERTER = new Converter<String, EpisodeId>() {
        @Override
        public EpisodeId convert(String source) {
            String[] values = source.split("-");
            return new EpisodeId(values[0], Integer.valueOf(values[1]));
        }
    };

    @Column
    private String book;
    @Column
    private int number;

    public String toString() {
        return book + "-" + String.valueOf(number);
    }

}
