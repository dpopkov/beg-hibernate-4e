package learn.beghibernate.ch03.application;

import learn.beghibernate.ch03.application.impl.HibernateRankingService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddRankingTest {
    RankingService service = new HibernateRankingService();

    @Test
    public void addRanking() {
        service.addRanking("J. C. Smell", "Drew Lombardo", "Mule", 8);
        assertEquals(8, service.getRankingFor("J. C. Smell", "Mule"));
    }
}
