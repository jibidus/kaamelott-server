package jibidus.kaamelott;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
