import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getTopCard() {
        if (!cards.isEmpty()) {
            return cards.get(cards.size() - 1);
        }
        return null;
    }

    public List<Card> getCards() {
        return cards;
    }
}
