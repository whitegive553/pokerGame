import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class Game {
    private List<Card> deck;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Hand lastHand;
    private int passCount;
    private int round;

    public Game() {
        deck = new ArrayList<>();
        String[] suits = {"♠", "♥", "♣", "♦"};
        String[] ranks = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};

        // 初始化 52 张普通牌
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }

        // 初始化 2 张王
        deck.add(new Card("Joker", "1"));
        deck.add(new Card("JOKER", "2"));

        // 洗牌
        Collections.shuffle(deck);

        // 初始化玩家
        player1 = new Player("玩家 1");
        player2 = new Player("玩家 2");

        // 发牌：6 个牌堆，每堆 9 张
        for (int i = 0; i < 9; i++) {
            for (Deck d : player1.getDecks()) {
                d.addCard(deck.remove(0));
            }
            for (Deck d : player2.getDecks()) {
                d.addCard(deck.remove(0));
            }
        }

        lastHand = null;
        passCount = 0;
        round = 1;

        currentPlayer = player1; // 初始化为玩家 1 先出牌
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public boolean playTurn(List<Card> selectedCards) {
        Hand currentHand = new Hand(selectedCards);

        if (currentHand.getHandType() == Hand.HandType.INVALID) {
            return false;
        }

        if (lastHand == null || currentHand.beats(lastHand)) {
            lastHand = currentHand;
            passCount = 0;

            for (Card card : selectedCards) {
                for (Deck deck : currentPlayer.getDecks()) {
                    if (deck.getTopCard() == card) {
                        deck.getCards().remove(card);
                        break;
                    }
                }
            }

            if (isWinner(currentPlayer)) {
                JOptionPane.showMessageDialog(null, currentPlayer.getName() + " 胜利！");
                System.exit(0);
            }

            switchTurn();
            return true;
        }

        return false;
    }

    public void passTurn() {
        lastHand = null; // 一方跳过后，重置 lastHand，重新开始新一轮
        passCount = 0; // 清空跳过计数
        switchTurn(); // 切换到另一位玩家
    }


    private boolean isWinner(Player player) {
        for (Deck deck : player.getDecks()) {
            if (!deck.getCards().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

