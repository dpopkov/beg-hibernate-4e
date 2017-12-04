package learn.beghibernate.ch04.orphan;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrphanRemovalTest {
    @Test
    public void orphanRemovalTest() {
        Long id = createLibrary();

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Library library = session.load(Library.class, id);
            assertEquals(3, library.getBooks().size());

            library.getBooks().remove(0);
            assertEquals(2, library.getBooks().size());

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Library l2 = session.load(Library.class, id);
            assertEquals(2, l2.getBooks().size());

            Query<Book> query = session.createQuery("from Book b", Book.class);
            List<Book> books = query.list();
            assertEquals(2, books.size());

            tx.commit();
        }
    }

    private Long createLibrary() {
        Library library;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            library = new Library();
            library.setName("orphanLib");
            session.save(library);

            Book book = library.addBook("book 1");
            session.save(book);

            book = library.addBook("book 2");
            session.save(book);

            book = library.addBook("book 3");
            session.save(book);

            tx.commit();
        }
        return library.getId();
    }
}
