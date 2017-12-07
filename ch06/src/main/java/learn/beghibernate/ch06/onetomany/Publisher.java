package learn.beghibernate.ch06.onetomany;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<PrintedBook> books = new HashSet<>();

    public void addBookByTitle(String title) {
        PrintedBook book = new PrintedBook();
        book.setTitle(title);
        book.setPublisher(this);
        books.add(book);
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

    public Set<PrintedBook> getBooks() {
        return books;
    }

    public void setBooks(Set<PrintedBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", printed books=" + (books != null ? books.size() : 0) +
                '}';
    }
}
