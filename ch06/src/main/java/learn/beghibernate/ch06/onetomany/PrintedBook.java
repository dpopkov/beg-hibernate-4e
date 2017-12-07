package learn.beghibernate.ch06.onetomany;

import javax.persistence.*;

@Entity
public class PrintedBook {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private Publisher publisher;

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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "PrintedBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher=" + (publisher != null ? publisher.getName() : "null") +
                '}';
    }
}
