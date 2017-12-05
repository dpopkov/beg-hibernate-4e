package learn.beghibernate.primarykey;

import learn.beghibernate.primarykey.before.Book;
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

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            Book book = query.uniqueResult();
            System.out.println(book);
        }
    }
}
