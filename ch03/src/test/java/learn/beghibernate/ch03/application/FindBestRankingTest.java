package learn.beghibernate.ch03.application;

import learn.beghibernate.ch03.simple.Person;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class FindBestRankingTest extends BasicRankingTest {
    @Test
    public void findBestForNoneExistentSkill() {
        Person p = service.findBestPersonFor("no skill");
        assertNull(p);
    }

    @Test
    public void findBestForSkill() {
        service.addRanking("S1", "O1", "Sk1", 6);
        service.addRanking("S1", "O2", "Sk1", 8);
        service.addRanking("S2", "O1", "Sk1", 5);
        service.addRanking("S2", "O2", "Sk1", 7);
        service.addRanking("S3", "O1", "Sk1", 7);
        service.addRanking("S3", "O2", "Sk1", 9);
        // data that should not factor in!
        service.addRanking("S3", "O1", "Sk2", 2);
        Person p = service.findBestPersonFor("Sk1");
        assertEquals("S3", p.getName());
    }
}
