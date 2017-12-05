package learn.beghibernate.ch06.twotables;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CustomerTest {
    @Test
    public void saveCustomerInTwoTables() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setName("Jack Sparrow");
            customer.setAddress("Black Perllllll");
            session.save(customer);

            assertNotNull(customer.getId());

            tx.commit();
        }
    }
}
