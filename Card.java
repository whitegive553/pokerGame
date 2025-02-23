public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        if (rank.equals("Joker")) {
            return "Joker";  // 小王
        }
        if (rank.equals("JOKER")) {
            return "JOKER"; // 大王
        }
        return rank + suit;
    }
}
