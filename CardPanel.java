import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPanel extends JPanel {
    private Card card;
    private boolean isSelected;
    private boolean isFaceUp;

    public CardPanel(Card card) {
        this.card = card;
        this.isSelected = false;
        this.isFaceUp = true;

        setPreferredSize(new Dimension(80, 120));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBackground(Color.WHITE);

        // 鼠标悬停效果：轻微放大
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSelected = !isSelected;
                if (isSelected) {
                    setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    setLocation(getX(), getY() - 15); // 上移表示选中
                } else {
                    setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    setLocation(getX(), getY() + 15); // 恢复原位
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                setSize(new Dimension(85, 125));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(isSelected ? BorderFactory.createLineBorder(Color.RED, 3)
                        : BorderFactory.createLineBorder(Color.BLACK, 2));
                setSize(new Dimension(80, 120));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 背面：用灰色表示
        if (!isFaceUp) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("CARD", 20, 70);
        } else {
            // 正面：显示点数和花色
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString(card.getRank(), 10, 30);
            g2.drawString(card.getSuit(), 50, 90);
        }
    }

    public Card getCard() {
        return card;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
