package learn.beghibernate.ch06.jpa2annot;

import javax.persistence.Entity;

@Entity
public class ComputerBook extends BookSuperClass {
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
