package learn.beghibernate.ch08.caching;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class SupplierTest {
    @Test
    public void readingInTwoSessions() {
        Integer id;
        // Create entity
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Supplier supplier = new Supplier();
            supplier.setName("Supplier1");
            session.save(supplier);
            assertNotNull(supplier.getId());
            id = supplier.getId();

            tx.commit();
        }

        // Read in 1 session (from L1 cache)
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Supplier supplier1 = session.byId(Supplier.class).load(id);
            Supplier supplier2 = session.byId(Supplier.class).load(id);

            assertEquals(supplier1, supplier2);
            assertTrue(supplier1 == supplier2);

            tx.commit();
        }

        // Read in 2 sessions (from L2 cache)
        try (Session session1 = SessionUtil.getSession()) {
            Transaction tx = session1.beginTransaction();

            Supplier supplier1 = session1.byId(Supplier.class).load(id);

            try (Session session2 = SessionUtil.getSession()) {
                Transaction tx2 = session2.beginTransaction();

                Supplier supplier2 = session2.byId(Supplier.class).load(id);

                assertEquals(supplier1, supplier2);
                assertTrue(supplier1 != supplier2);

                tx2.commit();
            }

            tx.commit();
        }
    }
}