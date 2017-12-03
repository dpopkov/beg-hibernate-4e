package learn.beghibernate.ch03.application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindAverageRankingTest extends BasicRankingTest {
    @Test
    public void validateRankingAverage() {
        service.addRanking("A", "B", "C", 4);
        service.addRanking("A", "B", "C", 5);
        service.addRanking("A", "B", "C", 6);
        assertEquals(5, service.getRankingFor("A", "C"));

        service.addRanking("A", "B", "C", 7);
        service.addRanking("A", "B", "C", 8);
        assertEquals(6, service.getRankingFor("A", "C"));
    }
}
