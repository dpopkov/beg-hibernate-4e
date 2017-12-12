package learn.beghibernate.lifecycle;

import learn.beghibernate.util.JPASessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAccountTest {
    private static final String PERSISTENCE_UNIT_NAME = "chapter07";

    @Test
    public void saveUserAccount() {
        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();

            UserAccount account1 = new UserAccount();
            account1.setName("John Doe");
            account1.setPassword("123");
            assertNull(account1.getPasswordHash());
            assertNull(account1.getSalt());
            assertFalse(account1.validPassword("123"));

            session.save(account1);

            assertNotNull(account1.getPasswordHash());
            assertNotNull(account1.getSalt());
            assertTrue(account1.validPassword("123"));

            tx.commit();
        }

        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();

            Query<UserAccount> query = session.createQuery(
                    "from UserAccount u where u.name=:name", UserAccount.class);
            query.setParameter("name", "John Doe");
            UserAccount account2 = query.uniqueResult();

            assertNotNull(account2.getPasswordHash());
            assertNotNull(account2.getSalt());

            session.delete(account2);

            tx.commit();

            assertTrue(account2.validPassword("123"));
        }
    }
}