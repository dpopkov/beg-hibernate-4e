package learn.beghibernate.ch06.onetoone;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void testSavePerson() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Person person = new Person();
            person.setName("Jack Sparrow");
            Address address = new Address();
            address.setCity("Gavana");
            address.setInCityLocation("The Best Tavern");
            person.setAddress(address);

            session.save(address);
            session.save(person);

            tx.commit();

            assertNotNull(address.getId());
            assertNotNull(person.getId());

            System.out.println(person);
            System.out.println(address);
        }
    }
}