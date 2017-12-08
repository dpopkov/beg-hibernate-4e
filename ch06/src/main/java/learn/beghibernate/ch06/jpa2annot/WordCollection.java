package learn.beghibernate.ch06.jpa2annot;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WordCollection {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    private List<String> words = new ArrayList<>();

    public void addWord(String word) {
        words.add(word);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "WordCollection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", words=" + words +
                '}';
    }
}
