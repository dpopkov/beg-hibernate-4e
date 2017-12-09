package learn.beghibernate.ch06.ordering;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployerTest {
    @Test
    public void saveEmployer() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Employer employer = new Employer();
            employer.setName("Bill");

            employer.addEmployeeByName("Emp1");
            employer.addEmployeeByName("Emp5");
            employer.addEmployeeByName("Emp3");
            employer.addEmployeeByName("Emp2");
            employer.addEmployeeByName("Emp4");

            session.save(employer);

            tx.commit();

            assertNotNull(employer.getId());
            System.out.println(employer);
        }
    }
}