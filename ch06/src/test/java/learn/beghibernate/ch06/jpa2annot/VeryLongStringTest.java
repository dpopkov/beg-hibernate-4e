package learn.beghibernate.ch06.jpa2annot;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class VeryLongStringTest {
    @Test
    public void saveVeryLongString() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            VeryLongString vls = new VeryLongString();
            vls.setText(createLongText(256));
            session.save(vls);

            tx.commit();

            assertNotNull(vls.getId());
        }
    }

    private String createLongText(int length) {
        StringBuilder sb = new StringBuilder();
        final String letters = "abcdefghijklmnopqrstuvwxyz ";
        for (int i = 0; i < length; i++) {
            sb.append(letters.charAt(i % letters.length()));
        }
        return sb.toString();
    }

}