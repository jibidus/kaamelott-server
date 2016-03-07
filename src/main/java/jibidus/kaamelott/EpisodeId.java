package jibidus.kaamelott;


import java.io.Serializable;

public class EpisodeId implements Serializable {

    private String book;
    private int number;

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
