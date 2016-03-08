package jibidus.kaamelott;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Character {

    @Id
    private String code;
    private String name;

    public Character() {
        super();
    }

    public Character(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
