package learn.beghibernate.ch04.broken;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BrokenInversionTest {
    private Email email;
    private Message message;

    @Test
    public void testBrokenInversionCode() {
        Long emailId;
        Long messageId;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new Email("Broken");
            message = new Message("Broken");

            email.setMessage(message);
//            message.setEmail(email);

            session.save(email);
            session.save(message);

            emailId = email.getId();
            messageId = message.getId();

            tx.commit();
        }
        assertNotNull(email.getMessage());
        assertNull(message.getEmail());

        getPersistentEntities(emailId, messageId);
        assertNotNull(email.getMessage());
        assertNull(message.getEmail());
    }

    @Test
    public void testProperSimpleInversionCode() {
        Long emailId;
        Long messageId;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new Email("Broken");
            message = new Message("Broken");

            email.setMessage(message);
            message.setEmail(email);

            session.save(email);
            session.save(message);

            emailId = email.getId();
            messageId = message.getId();

            tx.commit();
        }
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());

        getPersistentEntities(emailId, messageId);
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());
    }

    private void getPersistentEntities(Long emailId, Long messageId) {
        try (Session session = SessionUtil.getSession()) {
            email = session.get(Email.class, emailId);
            System.out.println(email);
            message = session.get(Message.class, messageId);
            System.out.println(message);
        }
    }
}
