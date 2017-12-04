package learn.beghibernate.ch04.general;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MergeRefreshTest {
    @Test
    public void testMerge() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SimpleObject obj = new SimpleObject();
            obj.setKey("testMerge");
            obj.setValue(11L);
            session.save(obj);

            id = obj.getId();

            tx.commit();
        }

        SimpleObject obj2 = validateSimpleObject(id, 11L);
        obj2.setValue(22L);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.merge(obj2);

            tx.commit();
        }

        validateSimpleObject(id, 22L);
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

