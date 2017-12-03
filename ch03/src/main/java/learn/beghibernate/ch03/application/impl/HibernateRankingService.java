package learn.beghibernate.ch03.application.impl;

import learn.beghibernate.ch03.application.RankingService;
import learn.beghibernate.ch03.simple.Person;
import learn.beghibernate.ch03.simple.Ranking;
import learn.beghibernate.ch03.simple.Skill;
import learn.beghibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;
import java.util.stream.Collectors;

public class HibernateRankingService implements RankingService {

    @Override
    public void addRanking(String subject, String observer, String skill, int ranking) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            addRanking(session, subject, observer, skill, ranking);

            tx.commit();
        }
    }

    @Override
    public int getRankingFor(String subject, String skill) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            int average = getRankingFor(session, subject, skill);
            tx.commit();

            return average;
        }
    }

    @Override
    public void updateRanking(String subject, String observer, String skill, int rankingValue) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Ranking ranking = findRanking(session, subject, observer, skill);
            if (ranking == null) {
                addRanking(session, subject, observer, skill, rankingValue);
            } else {
                ranking.setRanking(rankingValue);
            }

            tx.commit();
        }
    }

    @Override
    public void removeRanking(String subject, String observer, String skill) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Ranking ranking = findRanking(session, subject, observer, skill);
            if (ranking != null) {
                session.delete(ranking);
            }

            tx.commit();
        }
    }

    @Override
    public Map<String, Integer> findRankingsFor(String subject) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Map<String, Integer> results = findRankingsFor(session, subject);
            tx.commit();
            return results;
        }
    }

    @Override
    public Person findBestPersonFor(String skill) {
        Person person = null;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            person = findBestPersonFor(session, skill);
            tx.commit();;
        }
        return person;
    }

    private Person findBestPersonFor(Session session, String skill) {
        Query<Object[]> query = session.createQuery("select r.subject.name, avg(r.ranking) " +
                "from Ranking r where " +
                "r.skill.name=:skillName " +
                "group by r.subject.name " +
                "order by avg(r.ranking) desc", Object[].class);
        query.setParameter("skillName", skill);
        List<Object[]> result = query.list();
        if (result.size() > 0) {
            String subjectName = (String) result.get(0)[0];
            return findPerson(session, subjectName);
        }
        return null;
    }

    private Map<String, Integer> findRankingsFor(Session session, String subject) {
        Query<Ranking> query = session.createQuery("from Ranking r where " +
                "r.subject.name=:subjectName", Ranking.class);
        query.setParameter("subjectName", subject);
        Map<String, Integer> results = new HashMap<>();
        List<Ranking> rankings = query.list();
        Set<String> skills = new HashSet<>();
        for (Ranking r : rankings) {
            skills.add(r.getSkill().getName());
        }
        for (String skill : skills) {
            results.put(skill, this.getRankingFor(subject, skill));
        }
        return results;
    }

    private Ranking findRanking(Session session, String subject, String observer, String skill) {
        Query<Ranking> query = session.createQuery("from Ranking r where " +
                "r.subject.name=:subjectName and " +
                "r.observer.name=:observerName and " +
                "r.skill.name=:skillName", Ranking.class);
        query.setParameter("subjectName", subject);
        query.setParameter("observerName", observer);
        query.setParameter("skillName", skill);
        Ranking ranking = query.uniqueResult();
        return ranking;
    }

    private int getRankingFor(Session session, String subject, String skill) {
        Query<Ranking> query = session.createQuery("from Ranking r " +
                "where r.subject.name=:subjectName " +
                "and r.skill.name=:skillName", Ranking.class);
        query.setParameter("subjectName", subject);
        query.setParameter("skillName", skill);
        IntSummaryStatistics stats = query
                .list()
                .stream()
                .collect(Collectors.summarizingInt(Ranking::getRanking));
        return (int) stats.getAverage();
    }

    private void addRanking(Session session, String subjectName, String observerName, String skillName, int rank) {
        Person subject = savePerson(session, subjectName);
        Person observer = savePerson(session, observerName);
        Skill skill = saveSkill(session, skillName);

        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rank);
        session.save(ranking);
    }

    private Skill saveSkill(Session session, String skillName) {
        Skill skill = findSkill(session, skillName);
        if (skill == null) {
            skill = new Skill();
            skill.setName(skillName);
            session.save(skill);
        }
        return skill;
    }

    private Skill findSkill(Session session, String skillName) {
        Query<Skill> query = session.createQuery("from Skill s where s.name=:skillName", Skill.class);
        query.setParameter("skillName", skillName);
        Skill skill = query.uniqueResult();
        return skill;
    }

    private Person savePerson(Session session, String name) {
        Person person = findPerson(session, name);
        if (person == null) {
            person = new Person();
            person.setName(name);
            session.save(person);
        }
        return person;
    }

    private Person findPerson(Session session, String name) {
        Query<Person> query = session.createQuery("from Person p where p.name=:name", Person.class);
        query.setParameter("name", name);
        Person person = query.uniqueResult();
        return person;
    }
}
