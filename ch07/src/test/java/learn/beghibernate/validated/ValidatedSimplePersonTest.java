package learn.beghibernate.validated;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;

public class ValidatedSimplePersonTest {
    @Test
    public void createValidPerson() {
        ValidatedSimplePerson person = ValidatedSimplePerson.builder()
                .age(15)
                .fname("Johnny")
                .lname("McYoungster").build();
        persist(person);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createValidatedUnderagePerson() {
        ValidatedSimplePerson person = ValidatedSimplePerson.builder()
                .age(12)
                .fname("Johnny")
                .lname("McYoungster").build();
        persist(person);
        fail("Should have failed validation");
    }

    @Test(expected = ConstraintViolationException.class)
    public void createValidatedPoorFNamePerson() {
        ValidatedSimplePerson person = ValidatedSimplePerson.builder()
                .age(14)
                .fname("J")
                .lname("McYoungster").build();
        persist(person);
        fail("Should have failed validation");
    }

    @Test(expected = ConstraintViolationException.class)
    public void createValidatedPoorLNamePerson() {
        ValidatedSimplePerson person = ValidatedSimplePerson.builder()
                .age(14)
                .fname("Johnny")
                .lname("M").build();
        persist(person);
        fail("Should have failed validation");
    }

    @Test(expected = ConstraintViolationException.class)
    public void createValidatedNoNamePerson() {
        ValidatedSimplePerson person = ValidatedSimplePerson.builder()
                .age(14)
                .lname("McYoungster").build();
        persist(person);
        fail("Should have failed validation");
    }

    private ValidatedSimplePerson persist(ValidatedSimplePerson person) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();

        session.persist(person);

        tx.commit();
        session.close();
        return person;
    }
}