package learn.beghibernate.util;

import org.hibernate.Session;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionUtilTest {
    @Test
    public void getSession() throws Exception {
        try (Session session = SessionUtil.getSession()) {
            assertNotNull(session);
        }
    }
}