package learn.beghibernate.ch06.onetomany;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class PublisherTest {
    @Test
    public void testSavePublisher() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Publisher publisher = new Publisher();
            publisher.setName("Prentice Wesley");
            publisher.addBookByTitle("Java1");
            publisher.addBookByTitle("Java2");

            session.save(publisher);

            tx.commit();

            assertNotNull(publisher.getId());

            System.out.println(publisher);
            for (PrintedBook book : publisher.getBooks()) {
                System.out.println(book);
            }
        }
    }
}