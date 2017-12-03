package learn.beghibernate.ch03.application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateRankingTest extends BasicRankingTest {
    @Test
    public void updateExistingRanking() {
        service.addRanking("Gene Showrama", "Scottball Most", "Ceylon", 6);
        assertEquals(6, service.getRankingFor("Gene Showrama", "Ceylon"));

        service.updateRanking("Gene Showrama", "Scottball Most", "Ceylon", 7);
        assertEquals(7, service.getRankingFor("Gene Showrama", "Ceylon"));
    }

    @Test
    public void updateNoneExistingRanking() {
        assertEquals(0, service.getRankingFor("Scottball Most", "Ceylon"));

        service.updateRanking("Scottball Most", "Gene Showrama", "Ceylon", 7);
        assertEquals(7, service.getRankingFor("Scottball Most", "Ceylon"));
    }
}
