package learn.beghibernate.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersistHelper {
    public static <T> T persist(T object) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.persist(object);
        tx.commit();
        session.close();
        return object;
    }
}
