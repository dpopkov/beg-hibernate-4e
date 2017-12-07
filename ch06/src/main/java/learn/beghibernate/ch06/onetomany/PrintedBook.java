package learn.beghibernate.ch06.onetomany;

import javax.persistence.*;

@Entity
public class PrintedBook {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "PrintedBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
