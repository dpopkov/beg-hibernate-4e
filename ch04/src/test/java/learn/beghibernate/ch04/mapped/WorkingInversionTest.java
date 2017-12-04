package learn.beghibernate.ch04.mapped;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class WorkingInversionTest {
    private Email email;
    private Message message;

    @Test
    public void testImpliedRelationship() {
        Long emailId;
        Long messageId;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new Email("Inverse Email");
            message = new Message("Inverse Message");

//            email.setMessage(message);
            message.setEmail(email);

            session.save(email);
            session.save(message);

            emailId = email.getId();
            messageId = message.getId();

            tx.commit();
        }
        assertEquals("Inverse Email", email.getSubject());
        assertEquals("Inverse Message", message.getContent());
        assertNull(email.getMessage());
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
