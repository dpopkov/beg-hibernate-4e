package learn.beghibernate.ch03.simple;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {
    private SessionFactory factory;

    @Before
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testSavePerson() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Person person = new Person();
            person.setName("J. C. Smell");

            session.save(person);

            tx.commit();
        }
    }
}