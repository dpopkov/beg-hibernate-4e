package learn.beghibernate.ch04.general;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MergeRefreshTest {

    @Test
    public void testMerge() {
        Long id = createAndSaveSimpleObject("testMerge", 11L);

        SimpleObject obj2 = validateSimpleObject(id, 11L);
        obj2.setValue(22L);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.merge(obj2);

            tx.commit();
        }

        validateSimpleObject(id, 22L);
    }

    @Test
    public void testRefresh() {
        Long id = createAndSaveSimpleObject("testMerge", 11L);

        SimpleObject obj2 = validateSimpleObject(id, 11L);
        obj2.setValue(22L);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.refresh(obj2);

            tx.commit();
        }

        validateSimpleObject(id, 11L);
    }

    private Long createAndSaveSimpleObject(String key, Long value) {
        Long objectId = null;
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

    private SimpleObject validateSimpleObject(Long id, Long value) {
        SimpleObject so = null;
        try (Session session = SessionUtil.getSession()) {
            so = session.load(SimpleObject.class, id);

            assertEquals("testMerge", so.getKey());
            assertEquals(value, so.getValue());
        }
        return so;
    }
}

