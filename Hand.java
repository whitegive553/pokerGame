import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hand {
    public enum HandType {
        SINGLE, PAIR, TRIPLE, STRAIGHT, BOMB, DOUBLE_JOKER, INVALID
    }

    private List<Card> cards;
    private HandType handType;

    public Hand(List<Card> cards) {
        this.cards = cards;
        this.handType = determineHandType();
    }

    public HandType getHandType() {
        return handType;
    }

    public List<Card> getCards() {
        return cards;
    }

    private HandType determineHandType() {
        Collections.sort(cards, Comparator.comparingInt(Hand::getCardValue));

        int size = cards.size();

        // 单张
        if (size == 1) return HandType.SINGLE;

        // 对子
        if (size == 2 && getCardValue(cards.get(0)) == getCardValue(cards.get(1))) {
            return HandType.PAIR;
        }

        // 三张
        if (size == 3 && getCardValue(cards.get(0)) == getCardValue(cards.get(1))
                && getCardValue(cards.get(1)) == getCardValue(cards.get(2))) {
            return HandType.TRIPLE;
        }

        // 双王炸弹
        if (size == 2 && isDoubleJoker(cards)) {
            return HandType.DOUBLE_JOKER;
        }

        // 炸弹（3 张相同）
        if (size == 3 && getCardValue(cards.get(0)) == getCardValue(cards.get(1))
                && getCardValue(cards.get(1)) == getCardValue(cards.get(2))) {
            return HandType.BOMB;
        }

        // 顺子
        if (size >= 3 && isStraight(cards)) {
            return HandType.STRAIGHT;
        }

        return HandType.INVALID;
    }

    private boolean isDoubleJoker(List<Card> cards) {
        return cards.size() == 2 &&
                ((cards.get(0).getRank().equals("Joker") && cards.get(1).getRank().equals("JOKER")) ||
                        (cards.get(0).getRank().equals("JOKER") && cards.get(1).getRank().equals("Joker")));
    }

    private boolean isStraight(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (getCardValue(cards.get(i + 1)) - getCardValue(cards.get(i)) != 1) {
                return false;
            }
        }
        return true;
    }

    public static int getCardValue(Card card) {
        String rank = card.getRank();
        switch (rank) {
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "A": return 14;
            case "2": return 15;
            case "Joker": return 16;   // 小王
            case "JOKER": return 17;   // 大王
            default: return 0;
        }
    }

    public boolean beats(Hand other) {
        if (this.handType == HandType.INVALID) return false;
        if (other.handType == HandType.INVALID) return true;

        // 双王炸弹可以压所有牌型，包括其他炸弹
        if (this.handType == HandType.DOUBLE_JOKER) return true;
        if (other.handType == HandType.DOUBLE_JOKER) return false;

        // 普通炸弹可以压所有非炸弹牌型
        if (this.handType == HandType.BOMB && other.handType != HandType.BOMB) {
            return true;
        }

        // 类型必须相同才能比较大小
        if (this.handType == other.handType) {
            return getCardValue(this.cards.get(0)) > getCardValue(other.cards.get(0));
        }

        return false;
    }

    @Override
    public String toString() {
        return cards.toString() + " (" + handType + ")";
    }
}

