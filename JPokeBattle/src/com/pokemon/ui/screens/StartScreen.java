package com.pokemon.ui.screens;

import javax.swing.*;
import java.awt.*;
import com.pokemon.core.battle.Battle;
import com.pokemon.core.pokemon.Bulbo;

public class StartScreen extends JFrame {
    private JPanel mainPanel;
    
    public StartScreen() {
        initializeScreen();
    }
    
    private void initializeScreen() {
        setTitle("JPokeBattle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("JPokeBattle");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Start Button
        JButton startButton = new JButton("Start Battle");
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(e -> startBattle());
        
        gbc.gridy = 1;
        mainPanel.add(startButton, gbc);
        
        add(mainPanel);
    }
    
    private void startBattle() {
        Bulbo pokemon1 = new Bulbo(5);
        Bulbo pokemon2 = new Bulbo(5);
        Battle battle = new Battle(pokemon1, pokemon2);
        
        BattleScreen battleScreen = new BattleScreen(battle, this);
        battleScreen.setVisible(true);
        this.setVisible(false);
    }
} 