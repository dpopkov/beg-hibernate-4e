package learn.beghibernate.ch03.application;

public interface RankingService {
    void addRanking(String subject, String observer, String skill, int ranking);

    int getRankingFor(String subject, String skill);
}
