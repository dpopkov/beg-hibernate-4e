package learn.beghibernate.ch03.simple;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RankingTest {
    private static SessionFactory factory;

    @BeforeClass
    public static void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void test01Rankings() {
        populateRankingsData();
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Query<Ranking> query = session.createQuery("from Ranking r " +
                    "where r.subject.name=:name " +
                    "and r.skill.name=:skill", Ranking.class);
            query.setParameter("name", "J. C. Smell");
            query.setParameter("skill", "Java");
            IntSummaryStatistics stats = query.list()
                    .stream()
                    .collect(Collectors.summarizingInt(Ranking::getRanking));

            long count = stats.getCount();
            int average = (int) stats.getAverage();
            tx.commit();

            assertEquals(3, count);
            assertEquals(7, average);
        }
    }

    @Test
    public void test02ChangeRanking() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Query<Ranking> query = session.createQuery("from Ranking r " +
                    "where r.subject.name=:subject and " +
                    "r.observer.name=:observer and " +
                    "r.skill.name=:skill", Ranking.class);
            query.setParameter("subject", "J. C. Smell");
            query.setParameter("observer", "Gene Showrama");
            query.setParameter("skill", "Java");
            Ranking ranking = query.uniqueResult();
            assertNotNull("Could not find matching ranking", ranking);
            ranking.setRanking(9);

            tx.commit();
        }
        assertEquals(8, getAverage("J. C. Smell", "Java"));
    }

    @Test
    public void test03SaveRanking() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Person subject = savePerson(session, "J. C. Smell");
            Person observer = savePerson(session, "Drew Lombardo");
            Skill skill = saveSkill(session, "Java");

            Ranking ranking = new Ranking();
            ranking.setSubject(subject);
            ranking.setObserver(observer);
            ranking.setSkill(skill);
            ranking.setRanking(8);
            session.save(ranking);

            tx.commit();
        }
    }

    private int getAverage(String subject, String skill) {
        try (Session session = factory.openSession()) {
            Query<Ranking> query = session.createQuery("from Ranking r " +
                    "where r.subject.name=:name and " +
                    "r.skill.name=:skill", Ranking.class);
            query.setParameter("name", subject);
            query.setParameter("skill", skill);
            IntSummaryStatistics stats = query.list()
                    .stream()
                    .collect(Collectors.summarizingInt(Ranking::getRanking));
            int average = (int) stats.getAverage();
            return average;
        }
    }

    /*@AfterClass
    public static void clear() {
        factory.close();
        factory = null;
    }*/

    private void populateRankingsData() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            createData(session, "J. C. Smell", "Gene Showrama", "Java", 6);
            createData(session, "J. C. Smell", "Scottball Most", "Java", 7);
            createData(session, "J. C. Smell", "Drew Lombardo", "Java", 8);
            tx.commit();
        }
    }

    private void createData(Session session, String subjectName, String observerName, String skillName, int rank) {
        Person subject = savePerson(session, subjectName);
        Person observer = savePerson(session, observerName);
        Skill skill = saveSkill(session, skillName);

        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rank);
        session.save(ranking);
    }

    private Skill saveSkill(Session session, String name) {
        Skill skill = findSkill(session, name);
        if (skill == null) {
            skill = new Skill();
            skill.setName(name);
            session.save(skill);
        }
        return skill;
    }

    private Skill findSkill(Session session, String name) {
        Query<Skill> query = session.createQuery("from Skill s where s.name=:name");
        query.setParameter("name", name);
        Skill skill = query.uniqueResult();
        return skill;
    }

    private Person savePerson(Session session, String name) {
        Person person = findPerson(session, name);
        if (person == null) {
            person = new Person();
            person.setName(name);
            session.save(person);
        }
        return person;
    }

    private Person findPerson(Session session, String name) {
        Query<Person> query = session.createQuery("from Person p where p.name=:name",
                Person.class);
        query.setParameter("name", name);
        Person person = query.uniqueResult();
        return person;
    }
}