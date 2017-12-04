package learn.beghibernate.ch04.general;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersistingEntitiesTest {
    @Test
    public void testSaveLoad() {
        Long id;
        SimpleObject obj;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj = new SimpleObject();
            obj.setKey("s1");
            obj.setValue(10L);
            assertNull(obj.getId());

            session.save(obj);
            assertNotNull(obj.getId());
            id = obj.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            // loading the object by id
            SimpleObject o2 = session.load(SimpleObject.class, id);
            assertEquals("s1", o2.getKey());
            assertNotNull(o2.getValue());
            assertEquals(10L, o2.getValue().longValue());

            SimpleObject o3 = session.load(SimpleObject.class, id);
            // since o2 and o3 were loaded in the same session, they are the same instance
            assertEquals(o2, o3);
            assertEquals(obj, o2);

            assertTrue(o2 == o3);
            assertFalse(o2 == obj);
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
