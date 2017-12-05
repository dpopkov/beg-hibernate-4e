package learn.beghibernate.primarykey;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BookTest {
    @Test
    public void testBook() {
        try (Session session = SessionUtil.getSession()) {
            assertNotNull(session);
        }
    }
}
