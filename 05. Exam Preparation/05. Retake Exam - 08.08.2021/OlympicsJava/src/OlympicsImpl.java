
import java.util.*;
import java.util.stream.Collectors;

public class OlympicsImpl implements Olympics {
    private Map<Integer, Competitor> competitorsById;
    private Map<Integer, Competition> competitionsById;
    private Map<Integer, Competitor> registeredCompetitors;

    // TODO: Using Sets

    public OlympicsImpl() {
        this.competitorsById = new HashMap<>();
        this.competitionsById = new HashMap<>();
        this.registeredCompetitors = new HashMap<>();
    }

    @Override
    public void addCompetitor(int id, String name) {
        if (this.competitorsById.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        this.competitorsById.put(id, new Competitor(id, name));
    }

    @Override
    public void addCompetition(int id, String name, int score) {
        if (this.competitionsById.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        this.competitionsById.put(id, new Competition(name, id, score));
    }

    @Override
    public void compete(int competitorId, int competitionId) {
        if (!this.competitorsById.containsKey(competitorId) || !this.competitionsById.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }
        Competitor competitor = this.competitorsById.get(competitorId);
        Competition competition = this.competitionsById.get(competitionId);
        competition.getCompetitors().add(competitor);
        competitor.setTotalScore(competitor.getTotalScore() + (long) competition.getScore());
        this.registeredCompetitors.put(competitorId, competitor);
    }

    @Override
    public void disqualify(int competitionId, int competitorId) {
        if (!this.competitorsById.containsKey(competitorId) || !this.competitionsById.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }
        Competitor competitor = this.competitorsById.get(competitorId);
        Competition competition = this.competitionsById.get(competitionId);
        if (!competition.getCompetitors().contains(competitor)) {
            throw new IllegalArgumentException();
        }
        competition.getCompetitors().remove(competitor);
        this.registeredCompetitors.remove(competitor.getId());
        competitor.setTotalScore(competitor.getTotalScore() - (long) competition.getScore());
    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
        return this.competitorsById.values()
                .stream()
                .filter((competitor) -> competitor.getTotalScore() > min && competitor.getTotalScore() <= max)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        List<Competitor> comprtitorsWithGivenName = this.competitorsById.values()
                .stream()
                .filter((competitor) -> competitor.getName().equals(name))
                .collect(Collectors.toList());
        if (comprtitorsWithGivenName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return comprtitorsWithGivenName
                .stream()
                .sorted( (comp1, comp2) -> {
                    return Integer.compare(comp1.getId(), comp2.getId());
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        return this.competitorsById.values()
                .stream()
                .filter((competitor) -> competitor.getName().length() >= minLength && competitor.getName().length() <= maxLength)
                .sorted((c1, c2) -> {
                    return Integer.compare(c1.getId(), c2.getId());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        if (!this.competitionsById.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }
        return this.competitionsById.get(competitionId).getCompetitors().contains(comp);
    }

    @Override
    public int competitionsCount() {
        return this.competitionsById.values().size();
    }

    @Override
    public int competitorsCount() {
        return this.competitorsById.values().size();
    }

    @Override
    public Competition getCompetition(int id) {
        if (!this.competitionsById.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return this.competitionsById.get(id);
    }
}
