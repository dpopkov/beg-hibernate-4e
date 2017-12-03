package learn.beghibernate.ch03.application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RemoveRankingTest extends BasicRankingTest {
    @Test
    public void removeExistingRanking() {
        service.addRanking("R1", "R2", "RS1", 8);
        assertEquals(8, service.getRankingFor("R1", "RS1"));

        service.removeRanking("R1", "R2", "RS1");
        assertEquals(0, service.getRankingFor("R1", "RS1"));
    }

    @Test
    public void removeNoneExistingRanking() {
        service.removeRanking("R3", "R4", "RS2");
    }
}
