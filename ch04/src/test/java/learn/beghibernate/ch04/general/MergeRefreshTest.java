package learn.beghibernate.ch04.general;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class MergeRefreshTest {

    @Test
    public void testMerge() {
        Long id = createAndSaveSimpleObject("testMerge", 11L);

        SimpleObject obj2 = loadAndValidateSimpleObject(id, "testMerge", 11L);
        obj2.setValue(22L);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.merge(obj2);

            tx.commit();
        }

        loadAndValidateSimpleObject(id, "testMerge", 22L);
    }

    @Test
    public void testRefresh() {
        Long id = createAndSaveSimpleObject("testRefresh", 11L);

        SimpleObject obj2 = loadAndValidateSimpleObject(id, "testRefresh", 11L);
        obj2.setValue(22L);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.refresh(obj2);

            tx.commit();
        }

        loadAndValidateSimpleObject(id,"testRefresh", 11L);
    }

    @Test
    public void testDelete() {
        Long id = createAndSaveSimpleObject("testDelete", 121L);

        SimpleObject detachedObj = loadAndValidateSimpleObject(id, "testDelete", 121L);
        assertEquals(id, detachedObj.getId());

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.delete(detachedObj);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            SimpleObject deleted = session.get(SimpleObject.class, id);
            assertNull(deleted);
        }
    }

    @Test
    public void testBulkDelete() {
        Long id1 = createAndSaveSimpleObject("testDelete1", 122L);
        Long id2 = createAndSaveSimpleObject("testDelete2", 123L);
        loadAndValidateSimpleObject(id1, "testDelete1", 122L);
        loadAndValidateSimpleObject(id2, "testDelete2", 123L);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery("delete from SimpleObject").executeUpdate();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            SimpleObject deleted = session.get(SimpleObject.class, id1);
            assertNull(deleted);
            deleted = session.get(SimpleObject.class, id2);
            assertNull(deleted);
        }
    }

    private Long createAndSaveSimpleObject(String key, Long value) {
        Long objectId;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SimpleObject obj = new SimpleObject();
            obj.setKey(key);
            obj.setValue(value);
            session.save(obj);

            objectId = obj.getId();

            tx.commit();
        }
        return objectId;
    }

    private SimpleObject loadAndValidateSimpleObject(Long id, String key, Long value) {
        SimpleObject so;
        try (Session session = SessionUtil.getSession()) {
            so = session.load(SimpleObject.class, id);

            assertEquals(key, so.getKey());
            assertEquals(value, so.getValue());
        }
        return so;
    }
}

