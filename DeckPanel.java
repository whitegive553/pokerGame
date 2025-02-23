import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeckPanel extends JPanel {
    private Deck deck;
    private CardPanel topCardPanel;

    public DeckPanel(Deck deck) {
        this.deck = deck;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("剩余：" + deck.getCards().size()));

        if (!deck.getCards().isEmpty()) {
            topCardPanel = new CardPanel(deck.getTopCard());
            add(topCardPanel, BorderLayout.CENTER);
        } else {
            add(new JLabel("空"), BorderLayout.CENTER);
        }
    }

    public Card getSelectedCard() {
        if (topCardPanel != null && topCardPanel.isSelected()) {
            return topCardPanel.getCard();
        }
        return null;
    }
}
