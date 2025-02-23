import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Deck> decks;

    public Player(String name) {
        this.name = name;
        decks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            decks.add(new Deck());
        }
    }

    public String getName() {
        return name;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void showTopCards() {
        System.out.println("\n" + name + " 的牌堆状态：");
        for (int i = 0; i < decks.size(); i++) {
            Card topCard = decks.get(i).getTopCard();
            int remainingCards = decks.get(i).getCards().size();
            System.out.println("牌堆 " + (i + 1) + ": " +
                    (topCard != null ? topCard : "空") +
                    " (剩余 " + remainingCards + " 张)");
        }
    }
}
