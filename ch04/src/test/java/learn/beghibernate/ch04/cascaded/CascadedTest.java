package learn.beghibernate.ch04.cascaded;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CascadedTest {
    @Test
    public void testCascadedPersist() {
        Long emailId;
        Long messageId;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Message message = new Message("Message3 content");
            Email email = new Email("Email3 subject");
            message.setEmail(email);

//            session.save(email);
            session.save(message);

            emailId = email.getId();
            assertNotNull(emailId);

            messageId = message.getId();
            assertNotNull(messageId);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Message message = session.load(Message.class, messageId);
            Email email = session.load(Email.class, emailId);

            System.out.println(message);
            System.out.println(email);
        }
    }
}
