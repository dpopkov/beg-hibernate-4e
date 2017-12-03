package learn.beghibernate.ch03.application;

import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class FindAllRankingsTest extends BasicRankingTest {
    @Test
    public void findAllRankingsEmptySet() {
        assertEquals(0, service.getRankingFor("Nobody", "Java"));
        assertEquals(0, service.getRankingFor("Nobody", "Python"));
        Map<String, Integer> rankings = service.findRankingsFor("Nobody");
        assertEquals(0, rankings.size());
    }

    @Test
    public void findAllRankings() {
        assertEquals(0, service.getRankingFor("Somebody", "Java"));
        assertEquals(0, service.getRankingFor("Somebody", "Python"));
        service.addRanking("Somebody", "Nobody", "Java", 9);
        service.addRanking("Somebody", "Nobody", "Java", 7);
        service.addRanking("Somebody", "Nobody", "Python", 7);
        service.addRanking("Somebody", "Nobody", "Python", 5);
        Map<String, Integer> rankings = service.findRankingsFor("Somebody");

        assertEquals(2, rankings.size());
        assertNotNull(rankings.get("Java"));
        assertEquals(new Integer(8), rankings.get("Java"));
        assertNotNull(rankings.get("Python"));
        assertEquals(new Integer(6), rankings.get("Python"));
    }
}
