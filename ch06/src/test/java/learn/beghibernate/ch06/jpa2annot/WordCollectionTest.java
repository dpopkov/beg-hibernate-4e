package learn.beghibernate.ch06.jpa2annot;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordCollectionTest {
    @Test
    public void saveWordCollection() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            WordCollection wc = new WordCollection();
            wc.setName("Ordinals");
            wc.addWord("First");
            wc.addWord("Second");
            wc.addWord("Third");
            session.save(wc);

            assertNotNull(wc.getId());

            tx.commit();

            System.out.println(wc);
        }
    }
}