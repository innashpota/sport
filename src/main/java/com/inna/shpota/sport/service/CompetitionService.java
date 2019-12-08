package com.inna.shpota.sport.service;

import com.inna.shpota.sport.data.MatchResult;
import com.inna.shpota.sport.data.Result;
import com.inna.shpota.sport.models.Competition;
import com.inna.shpota.sport.models.Match;
import com.inna.shpota.sport.models.Round;
import com.inna.shpota.sport.repository.CompetitionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CompetitionService {
    private final CompetitionRepository repository;

    public CompetitionService(CompetitionRepository repository) {
        this.repository = repository;
    }

    public List<Round> add(Competition competition) {
        int numberOfTeams = competition.getNumberOfTeams();
        List<Round> schedule = createSchedule(numberOfTeams);
        competition.setRounds(schedule);
        Competition saved = repository.save(competition);
        return saved.getRounds();
    }

    public List<Round> findByName(String name) {
        return repository.findByName(name).getRounds();
    }

    public void setWinner(String name, MatchResult matchResult) {
        String firstTeam = matchResult.getFirstTeam();
        String secondTeam = matchResult.getSecondTeam();
        String winner = matchResult.getWinner();
        Competition competition = repository.findByName(name);
        List<Round> rounds = competition.getRounds();
        Optional<Match> optionalMatch = rounds.stream()
                .map(Round::getMatches)
                .flatMap(List::stream)
                .filter(m -> m.getFirstTeam().equals(firstTeam))
                .filter(m -> m.getSecondTeam().equals(secondTeam))
                .findAny();
        if (optionalMatch.isPresent()) {
            Match match = optionalMatch.get();
            match.setPlayed(true);
            match.setWinner(winner);
            repository.save(competition);
        }
    }

    public List<Match> playedMatches(String name) {
        Competition competition = repository.findByName(name);
        List<Round> rounds = competition.getRounds();
        return rounds.stream()
                .map(Round::getMatches)
                .flatMap(List::stream)
                .filter(Match::isPlayed)
                .collect(toList());
    }

    public List<Match> notPlayedMatches(String name) {
        Competition competition = repository.findByName(name);
        List<Round> rounds = competition.getRounds();
        return rounds.stream()
                .map(Round::getMatches)
                .flatMap(List::stream)
                .filter(match -> !match.isPlayed())
                .collect(toList());
    }

    public List<Result> results(String name) {
        Competition competition = repository.findByName(name);
        List<Round> rounds = competition.getRounds();
        int numberOfTeams = competition.getNumberOfTeams();
        List<String> teams = createTeams(numberOfTeams);
        List<Result> results = new ArrayList<>(numberOfTeams);
        for (String team : teams) {
            Result result = new Result();
            result.setTeam(team);
            List<Match> teamMatches = rounds.stream()
                    .map(Round::getMatches)
                    .flatMap(List::stream)
                    .filter(m -> m.getFirstTeam().equals(team) || m.getSecondTeam().equals(team))
                    .collect(toList());
            result.setPlayedMatchesCount(getPlayedMatches(teamMatches));
            result.setWins(getWins(team, teamMatches));
            result.setLose(getLose(team, teamMatches));
            result.setDraws(result.getPlayedMatchesCount() - result.getWins() - result.getLose());
            result.setPoints(result.getWins() * 3 + result.getDraws());
            results.add(result);
        }
        return results;
    }

    private int getLose(String team, List<Match> teamMatches) {
        return (int) teamMatches.stream()
                .filter(Match::isPlayed)
                .map(Match::getWinner)
                .filter(w -> w != null && !w.isEmpty() && !w.equals(team))
                .count();
    }

    private int getWins(String team, List<Match> teamMatches) {
        return (int) teamMatches.stream()
                .filter(Match::isPlayed)
                .map(Match::getWinner)
                .filter(w -> w != null && !w.isEmpty() && w.equals(team))
                .count();
    }

    private int getPlayedMatches(List<Match> teamMatches) {
        return (int) teamMatches.stream()
                .filter(Match::isPlayed)
                .count();
    }

    private List<Round> createSchedule(int numberOfTeams) {
        List<String> teams = createTeams(numberOfTeams);
        int numberOfRounds = numberOfTeams % 2 == 0 ? (numberOfTeams - 1) * 2 : numberOfTeams * 2;
        int matchesPerRound = numberOfTeams / 2;
        int halfRoundMark = (numberOfRounds / 2);
        List<Round> rounds = new ArrayList<>();
        for (int roundIndex = 0; roundIndex < numberOfRounds; roundIndex++) {
            Round round = new Round();
            round.setName("Round " + (roundIndex + 1));
            List<Match> matches = getMatches(
                    numberOfTeams,
                    teams,
                    matchesPerRound,
                    halfRoundMark,
                    roundIndex
            );
            round.setMatches(matches);
            rounds.add(round);
        }
        return rounds;
    }

    private List<Match> getMatches(
            int numberOfTeams,
            List<String> teams,
            int matchesPerRound,
            int halfRoundMark,
            int roundIndex
    ) {
        List<Match> matches = new ArrayList<>();
        for (int matchIndex = 0; matchIndex < matchesPerRound; matchIndex++) {
            int home = (roundIndex + matchIndex) % (numberOfTeams - 1);
            int away = (numberOfTeams - 1 - matchIndex + roundIndex) % (numberOfTeams - 1);

            if (matchIndex == 0) {
                away = numberOfTeams - 1;
            }
            Match match = new Match();
            if (roundIndex < halfRoundMark) {
                match.setFirstTeam(teams.get(home));
                match.setSecondTeam(teams.get(away));
            } else {
                match.setFirstTeam(teams.get(away));
                match.setSecondTeam(teams.get(home));
            }
            matches.add(match);
        }
        return matches;
    }

    private List<String> createTeams(int numberOfTeams) {
        List<String> teams = new ArrayList<>(numberOfTeams);
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add("Team " + (i + 1));
        }
        return teams;
    }
}
