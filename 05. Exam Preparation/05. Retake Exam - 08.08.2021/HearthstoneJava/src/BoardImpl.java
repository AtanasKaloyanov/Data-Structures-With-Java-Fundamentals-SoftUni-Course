import java.util.*;
import java.util.stream.Collectors;

public class BoardImpl implements Board {
    Map<String, Card> cardsByName;

    public BoardImpl() {
        this.cardsByName = new HashMap<>();
    }

    @Override
    public void draw(Card card) {
        if (contains(card.getName())) {
            throw new IllegalArgumentException();
        }
        this.cardsByName.put(card.getName(), card);
    }

    @Override
    public Boolean contains(String name) {
        return this.cardsByName.containsKey(name);
    }

    @Override
    public int count() {
        return this.cardsByName.values().size();
    }

    @Override
    public void play(String attackerCardName, String attackedCardName) {
        if (!this.cardsByName.containsKey(attackerCardName) || !this.cardsByName.containsKey(attackedCardName)) {
            throw new IllegalArgumentException();
        }
        Card attackerCard = this.cardsByName.get(attackerCardName);
        Card attackedCard = this.cardsByName.get(attackedCardName);
        if (attackerCard.getLevel() != attackedCard.getLevel()) {
            throw new IllegalArgumentException();
        }
        if (attackedCard.getHealth() > 0) {
            attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getDamage());
            if (attackedCard.getHealth() <= 0) {
                attackerCard.setScore(attackerCard.getScore() + attackedCard.getLevel());
            }
        }
    }

    @Override
    public void remove(String name) {
        if (!cardsByName.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        this.cardsByName.remove(name);
    }

    @Override
    public void removeDeath() {
        List<Card> deathCards = this.cardsByName
                .values()
                .stream()
                .filter((card -> card.getHealth() <= 0))
                .collect(Collectors.toList());

        for (Card deathCard : deathCards) {
            this.cardsByName.remove(deathCard.getName());
        }
    }

    @Override
    public Iterable<Card> getBestInRange(int start, int end) {
        return this.cardsByName.values()
                .stream()
                .filter((card) -> card.getScore() >= start && card.getScore() <= end)
                .sorted((card1, card2) -> Integer.compare(card2.getLevel(), card1.getLevel()))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {
        return this.cardsByName.values()
                .stream()
                .filter((card) -> card.getName().startsWith(prefix))
                .sorted( (card1, card2) -> {
                    String name1 = new StringBuilder(card1.getName()).reverse().toString();
                    String name2 = new StringBuilder(card2.getName()).reverse().toString();

                    int result = name1.compareTo(name2);

                    if (result == 0) {
                        result = Integer.compare(card1.getLevel(), card2.getLevel());
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> searchByLevel(int level) {
        return this.cardsByName.values()
                .stream()
                .filter((card -> card.getLevel() == level))
                .sorted(Comparator.comparing(Card::getScore).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void heal(int health) {
        Card cardWithSmallestHealth = this.cardsByName.values()
                .stream()
                .sorted(Comparator.comparing(Card::getHealth))
                .collect(Collectors.toList())
                .get(0);

        cardWithSmallestHealth.setHealth(cardWithSmallestHealth.getHealth() + health);
    }
}




