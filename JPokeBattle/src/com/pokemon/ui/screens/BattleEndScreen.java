package com.pokemon.ui.screens;

import javax.swing.*;
import java.awt.*;

public class BattleEndScreen extends JFrame {
    private StartScreen startScreen;
    
    public BattleEndScreen(boolean isVictory, StartScreen startScreen) {
        this.startScreen = startScreen;
        initializeScreen(isVictory);
    }
    
    private void initializeScreen(boolean isVictory) {
        setTitle("Battle End");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Result Message
        String resultText = isVictory ? "Victory!" : "Defeat...";
        JLabel resultLabel = new JLabel(resultText);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        mainPanel.add(resultLabel, gbc);
        
        // Return to Main Menu Button
        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        returnButton.setPreferredSize(new Dimension(250, 50));
        returnButton.addActionListener(e -> returnToMainMenu());
        
        gbc.gridy = 1;
        mainPanel.add(returnButton, gbc);
        
        add(mainPanel);
    }
    
    private void returnToMainMenu() {
        startScreen.setVisible(true);
        this.dispose();
    }
} 