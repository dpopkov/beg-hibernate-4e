package learn.beghibernate.ch06.jpa2annot;

import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TimeIntervalTest {

    public static final int MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    @Test
    public void saveTimeInterval() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            TimeInterval ti1 = new TimeInterval();
            ti1.setStart(new Date());
            Date dt = new Date();
            dt = addDays(dt, 1);
            ti1.setFinish(dt);

            TimeInterval ti2 = new TimeInterval();
            dt = addDays(dt, 2);
            ti2.setStart(dt);
            dt = addDays(dt, 1);
            ti2.setFinish(dt);

            session.save(ti1);
            session.save(ti2);

            assertNotNull(ti1.getId());
            assertNotNull(ti2.getId());

            tx.commit();
        }
    }

    private Date addDays(Date date, int days) {
        Date resultDate = new Date(date.getTime() + MILLIS_PER_DAY * days);
        return resultDate;
    }
}