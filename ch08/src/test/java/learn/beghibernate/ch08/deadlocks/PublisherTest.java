package learn.beghibernate.ch08.deadlocks;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.PessimisticLockException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PublisherTest {
    @Test
    public void demonstrateRollback() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("delete from Publisher");
            query.executeUpdate();
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Query<Publisher> query = session.createQuery("from Publisher", Publisher.class);
            assertEquals(0, query.list().size());
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Publisher publisher = new Publisher();
            publisher.setName("foo");
            session.save(publisher);
            tx.rollback();
        }

        try (Session session = SessionUtil.getSession()) {
            Query<Publisher> query = session.createQuery("from Publisher", Publisher.class);
            assertEquals(0, query.list().size());
        }
    }

    @Test
    public void showDeadlock() throws InterruptedException {
        Long idA;
        Long idB;

        // clear out the old data and populate tables
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Publisher").executeUpdate();

            Publisher publisher;
            publisher = new Publisher();
            publisher.setName("A");
            session.save(publisher);
            idA = publisher.getId();

            publisher = new Publisher();
            publisher.setName("B");
            session.save(publisher);
            idB = publisher.getId();

            tx.commit();
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> updatePublishers("session1", idA, idB));
        executor.submit(() -> updatePublishers("session2", idB, idA));
        executor.shutdown();

        if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Executor did not terminate");
            }
        }

        try (Session session = SessionUtil.getSession()) {
            Query<Publisher> query = session.createQuery("from Publisher p order by p.name", Publisher.class);
            String result = query
                    .list()
                    .stream()
                    .map(Publisher::getName)
                    .collect(Collectors.joining(","));
            assertEquals("A,B", result);
        }
    }

    private void updatePublishers(String prefix, Long... ids) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            for (Long id : ids) {
                Thread.sleep(100);
                Publisher publisher = session.byId(Publisher.class).load(id);
                publisher.setName(prefix + " " + publisher.getName());
            }
            tx.commit();
        } catch (InterruptedException | PessimisticLockException e) {
            e.printStackTrace();
        }
    }
}