package learn.beghibernate.lifecycle;

import learn.beghibernate.util.JPASessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

public class LifecycleThingTest {
    static Logger logger = Logger.getLogger(LifecycleThingTest.class);

    private static final String PERSISTENCE_UNIT_NAME = "chapter07";

    @Test
    public void testLifecycle() {
        Integer id;
        LifecycleThing thing1, thing2, thing3;
        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();

            thing1 = new LifecycleThing();
            thing1.setName("Thing 1");
            session.save(thing1);
            id = thing1.getId();

            tx.commit();
        }

        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();

            thing2 = session
                    .byId(LifecycleThing.class)
                    .load(-1);
            assertNull(thing2);

            logger.info("attempted to load nonexistent reference");

            thing2 = session
                    .byId(LifecycleThing.class)
                    .load(id);
            assertNotNull(thing2);
            assertEquals(thing1, thing2);

            thing2.setName("Thing 2");

            tx.commit();
        }

        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();

            thing3 = session
                    .byId(LifecycleThing.class)
                    .load(id);
            assertNotNull(thing3);
            assertEquals(thing2, thing3);

            session.delete(thing3);

            tx.commit();
        }
        assertEquals(7, LifecycleThing.lifecycleCalls.nextClearBit(0));
    }
}