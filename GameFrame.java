import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private Game game;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel centerPanel;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private List<DeckPanel> player1Decks;
    private List<DeckPanel> player2Decks;
    private Player currentPlayer;

    public GameFrame() {
        game = new Game();
        currentPlayer = game.getCurrentPlayer();

        setTitle("纸牌游戏 - JFrame 图形化版");
        setSize(1200, 800);  // 调整窗口大小
        setResizable(false); // 禁止调整窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 玩家 2 区域（上方）
        player2Panel = new JPanel();
        player2Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        player2Decks = new ArrayList<>();
        for (Deck deck : game.getPlayer2().getDecks()) {
            DeckPanel deckPanel = new DeckPanel(deck);
            player2Decks.add(deckPanel);
            player2Panel.add(deckPanel);
        }
        add(player2Panel, BorderLayout.NORTH);

        // 中间区域：显示上一手牌
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        centerPanel.setBorder(BorderFactory.createTitledBorder("当前出的牌"));
        centerPanel.setPreferredSize(new Dimension(800, 200));
        updateCenterPanel(null);
        add(centerPanel, BorderLayout.CENTER);

        // 状态和操作区（中间下方）
        statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout());
        statusLabel = new JLabel("当前回合：" + currentPlayer.getName());
        statusPanel.add(statusLabel);

        JButton playButton = new JButton("出牌");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playTurn();
            }
        });

        JButton passButton = new JButton("跳过");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passTurn();
            }
        });

        statusPanel.add(playButton);
        statusPanel.add(passButton);

        // 使用 BoxLayout 垂直排列中间区域和状态区
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(centerPanel);
        middlePanel.add(statusPanel);
        add(middlePanel, BorderLayout.CENTER);

        // 玩家 1 区域（下方）
        player1Panel = new JPanel();
        player1Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        player1Decks = new ArrayList<>();
        for (Deck deck : game.getPlayer1().getDecks()) {
            DeckPanel deckPanel = new DeckPanel(deck);
            player1Decks.add(deckPanel);
            player1Panel.add(deckPanel);
        }
        add(player1Panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * 更新中间区域显示上一手牌
     * @param cards 当前出的牌
     */
    private void updateCenterPanel(List<Card> cards) {
        centerPanel.removeAll();
        if (cards == null || cards.isEmpty()) {
            centerPanel.add(new JLabel("任意出牌"));
        } else {
            for (Card card : cards) {
                JLabel label = new JLabel(card.toString());
                label.setFont(new Font("Arial", Font.BOLD, 24));
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                label.setPreferredSize(new Dimension(80, 120));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                centerPanel.add(label);
            }
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void playTurn() {
        List<Card> selectedCards = new ArrayList<>();
        List<DeckPanel> currentDecks = (currentPlayer == game.getPlayer1()) ? player1Decks : player2Decks;

        for (DeckPanel deckPanel : currentDecks) {
            Card selectedCard = deckPanel.getSelectedCard();
            if (selectedCard != null) {
                selectedCards.add(selectedCard);
            }
        }

        if (selectedCards.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择要出的牌！");
            return;
        }

        if (game.playTurn(selectedCards)) {
            currentPlayer = game.getCurrentPlayer();
            statusLabel.setText("当前回合：" + currentPlayer.getName());
            updateCenterPanel(selectedCards);  // 更新中间区域显示
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "出牌不合法或不够大！");
        }
    }

    private void passTurn() {
        game.passTurn();
        currentPlayer = game.getCurrentPlayer();
        statusLabel.setText("当前回合：" + currentPlayer.getName() + "（新一轮开始）");
        updateCenterPanel(null);  // 新一轮开始时清空中间区域
        refresh();
    }

    /**
     * 刷新界面，更新所有牌堆状态
     */
    private void refresh() {
        player1Panel.removeAll();
        player2Panel.removeAll();

        player1Decks = new ArrayList<>();
        for (Deck deck : game.getPlayer1().getDecks()) {
            DeckPanel deckPanel = new DeckPanel(deck);
            player1Decks.add(deckPanel);
            player1Panel.add(deckPanel);
        }

        player2Decks = new ArrayList<>();
        for (Deck deck : game.getPlayer2().getDecks()) {
            DeckPanel deckPanel = new DeckPanel(deck);
            player2Decks.add(deckPanel);
            player2Panel.add(deckPanel);
        }

        player1Panel.revalidate();
        player1Panel.repaint();
        player2Panel.revalidate();
        player2Panel.repaint();
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
