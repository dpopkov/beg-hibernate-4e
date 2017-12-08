package learn.beghibernate.ch06.manytomany;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class RealBookTest {
    @Test
    public void saveRealBook() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Author bmartin = new Author();
            bmartin.setName("Uncle Bob");
            session.save(bmartin);

            RealBook book1 = new RealBook();
            book1.setTitle("Clean code");
            book1.getAuthors().add(bmartin);
            session.save(book1);

            RealBook book2 = new RealBook();
            book2.setTitle("Clean coder");
            book2.getAuthors().add(bmartin);
            session.save(book2);

            assertNotNull(bmartin.getId());
            assertNotNull(book1.getId());
            assertNotNull(book2.getId());

            tx.commit();

            System.out.println(bmartin);
            System.out.println(book1);
            System.out.println(book2);
        }
    }
}