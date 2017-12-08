package learn.beghibernate.ch06.jpa2annot;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputerBookTest {
    @Test
    public void saveComputerBook() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ComputerBook book = new ComputerBook();
            book.setName("Thinking in Java");
            book.setLanguage("Java");
            session.save(book);

            tx.commit();

            assertNotNull(book.getId());
        }
    }
}