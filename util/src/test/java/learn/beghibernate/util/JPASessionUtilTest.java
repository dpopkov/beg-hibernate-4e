package learn.beghibernate.util;

import learn.beghibernate.util.model.Thing;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.Assert.*;

public class JPASessionUtilTest {
    private static final String PERSISTENCE_UNIT_NAME = "utiljpa";
    private static final String NONEXISTENT = "nonexistent";

    @Test
    public void getEntityManager() {
        EntityManager em = JPASessionUtil.getEntityManager(PERSISTENCE_UNIT_NAME);
        em.close();
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void nonexistentEntityManagerName() {
        JPASessionUtil.getEntityManager(NONEXISTENT);
        fail("We shouldn't be able to acquire an EntityManager here");
    }

    @Test
    public void getSession() {
        Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME);
        session.close();
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void nonexistentSessionName() {
        JPASessionUtil.getSession(NONEXISTENT);
        fail("We shouldn't be able to acquire a Session here");
    }

    @Test
    public void testEntityManager() {
        EntityManager em = JPASessionUtil.getEntityManager(PERSISTENCE_UNIT_NAME);

        Thing thing = new Thing();
        em.getTransaction().begin();
        thing.setName("Thing 1");
        em.persist(thing);
        em.getTransaction().commit();
        em.close();

        em = JPASessionUtil.getEntityManager(PERSISTENCE_UNIT_NAME);
        em.getTransaction().begin();
        TypedQuery<Thing> query = em.createQuery("from Thing t where t.name=:name", Thing.class);
        query.setParameter("name", "Thing 1");
        Thing result = query.getSingleResult();
        assertNotNull(result);
        assertEquals(thing, result);
        em.remove(result);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testSession() {
        Thing thing;
        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();
            thing = new Thing();
            thing.setName("Thing 2");
            session.persist(thing);
            tx.commit();
            assertNotNull(thing.getId());
        }

        try (Session session = JPASessionUtil.getSession(PERSISTENCE_UNIT_NAME)) {
            Transaction tx = session.beginTransaction();
            Query<Thing> query = session.createQuery("from Thing t where t.name=:name", Thing.class);
            query.setParameter("name", "Thing 2");
            Thing receivedThing = query.uniqueResult();
            assertNotNull(receivedThing);
            assertEquals(thing, receivedThing);
            session.delete(receivedThing);
            tx.commit();
        }
    }
}