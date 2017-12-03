package learn.beghibernate.ch03.application;

import java.util.Map;

public interface RankingService {
    void addRanking(String subject, String observer, String skill, int ranking);

    int getRankingFor(String subject, String skill);

    void updateRanking(String subject, String observer, String skill, int ranking);

    void removeRanking(String subject, String observer, String skill);

    Map<String,Integer> findRankingsFor(String subject);
}
