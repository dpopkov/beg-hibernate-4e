package learn.beghibernate.ch03.application;

import learn.beghibernate.ch03.application.impl.HibernateRankingService;

public class BasicRankingTest {
    protected RankingService service = new HibernateRankingService();
}
