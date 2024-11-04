package com.pokemon.ui.screens;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import com.pokemon.core.battle.Battle;
import com.pokemon.core.pokemon.Bulbo;
import com.pokemon.core.pokemon.Carmine;
import com.pokemon.core.pokemon.Michelangelo;
import com.pokemon.core.pokemon.Pokemon;
import com.pokemon.ui.components.PokemonSelectionDialog;

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
        PokemonSelectionDialog dialog = new PokemonSelectionDialog(this, null, true);
        dialog.setVisible(true);
        
        Pokemon playerPokemon = dialog.getSelectedPokemon();
        if (playerPokemon != null) {
            // Randomly select enemy Pokemon
            Pokemon[] possibleEnemies = {
                new Bulbo(5),
                new Carmine(5),
                new Michelangelo(5)
            };
            Pokemon enemyPokemon = possibleEnemies[new Random().nextInt(possibleEnemies.length)];
            
            Battle battle = new Battle(playerPokemon, enemyPokemon);
            BattleScreen battleScreen = new BattleScreen(battle, this);
            battleScreen.setVisible(true);
            this.setVisible(false);
        }
    }
} 