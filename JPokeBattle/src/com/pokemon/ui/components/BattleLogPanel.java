package com.pokemon.ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.Queue;
import java.util.LinkedList;
import com.pokemon.utils.FontManager;

public class BattleLogPanel extends JPanel {
    private JLabel messageLabel;
    private Timer charTimer;
    private static final int CHAR_DELAY = 50;
    private static final int MESSAGE_DURATION = 2000;
    private Queue<String> messageQueue;
    private boolean isDisplaying;
    
    public BattleLogPanel() {
        setPreferredSize(new Dimension(0, 50));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setLayout(new BorderLayout());
        
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(JLabel.LEFT);
        messageLabel.setFont(FontManager.getPokemonFont(16));
        add(messageLabel, BorderLayout.CENTER);
        
        messageQueue = new LinkedList<>();
        isDisplaying = false;
    }
    
    public void queueMessage(String message) {
        messageQueue.offer(message);
        if (!isDisplaying) {
            displayNextMessage();
        }
    }
    
    private void displayNextMessage() {
        if (messageQueue.isEmpty()) {
            isDisplaying = false;
            return;
        }
        
        isDisplaying = true;
        String message = messageQueue.poll();
        showMessage(message).thenRun(this::displayNextMessage);
    }
    
    private CompletableFuture<Void> showMessage(String message) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        if (charTimer != null && charTimer.isRunning()) {
            charTimer.stop();
        }
        
        StringBuilder displayText = new StringBuilder();
        final int[] charIndex = {0};
        
        charTimer = new Timer(CHAR_DELAY, e -> {
            if (charIndex[0] < message.length()) {
                displayText.append(message.charAt(charIndex[0]));
                messageLabel.setText(displayText.toString());
                charIndex[0]++;
            } else {
                charTimer.stop();
                Timer clearTimer = new Timer(MESSAGE_DURATION, e2 -> {
                    messageLabel.setText("");
                    ((Timer)e2.getSource()).stop();
                    future.complete(null);
                });
                clearTimer.setRepeats(false);
                clearTimer.start();
            }
        });
        
        charTimer.start();
        return future;
    }
} 