package learn.beghibernate.ch04.general;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersistingEntitiesTest {
    @Test
    public void testSaveLoad() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SimpleObject obj = new SimpleObject();
            obj.setKey("s1");
            obj.setValue(10L);
            assertNull(obj.getId());

            session.save(obj);
            assertNotNull(obj.getId());

            tx.commit();
        }
    }

    @Test
    public void testSavingEntitiesTwice() {
        Long id;
        SimpleObject obj;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj = new SimpleObject();
            obj.setKey("osas");
            obj.setValue(20L);

            session.save(obj);
            assertNotNull(obj.getId());

            id = obj.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj.setValue(22L);

            session.save(obj);

            tx.commit();
        }

        assertNotEquals(id, obj.getId());
        System.out.println("id = " + id);
        System.out.println("obj.getId() = " + obj.getId());
    }

    @Test
    public void testSaveOrUpdateEntity() {
        Long id;
        SimpleObject obj;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj = new SimpleObject();
            obj.setKey("osas2");
            obj.setValue(30L);

            session.save(obj);
            assertNotNull(obj.getId());

            id = obj.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj.setValue(33L);

            session.saveOrUpdate(obj);

            tx.commit();
        }

        assertEquals(id, obj.getId());
        System.out.println("id = " + id);
        System.out.println("obj.getId() = " + obj.getId());
    }
}
