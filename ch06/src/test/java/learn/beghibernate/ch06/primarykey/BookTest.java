package learn.beghibernate.ch06.primarykey;

import learn.beghibernate.ch06.primarykey.before.Book;
import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BookTest {
    @Test
    public void testBook() {
        try (Session session = SessionUtil.getSession()) {
            assertNotNull(session);

            Transaction tx = session.beginTransaction();

            Book book = new Book();
            book.setPages(2048);
            book.setTitle("The Best Java Textbook");

            session.save(book);
            assertNotNull(book.getId());

            Book book2 = new Book();
            book2.setPages(3);
            book2.setTitle("Now title cannot be null");
            session.save(book2);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Query<Book> query = session.createQuery("from SimpleBook", Book.class);
            for(Book book : query.list()) {
                System.out.println(book);
            }
        }
    }
}
