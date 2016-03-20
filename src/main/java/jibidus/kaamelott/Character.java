package jibidus.kaamelott;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Character {

    @Id
    private String code;
    private String name;

    public Character() {
        super();
    }
}
