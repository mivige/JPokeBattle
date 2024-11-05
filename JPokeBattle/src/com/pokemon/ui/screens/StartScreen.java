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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Desktop;
import java.net.URI;
import com.pokemon.utils.FontManager;

public class StartScreen extends JFrame {
    private JPanel mainPanel;
    
    private ImageIcon loadGithubIcon() {
        try {
            ImageIcon originalIcon = new ImageIcon("resources/images/ui/github.png");
            Image image = originalIcon.getImage();
            // Scale the image to 16x16 pixels
            Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public StartScreen() {
        initializeScreen();
    }
    
    private void initializeScreen() {
        setTitle("JPokeBattle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        
        // Center panel for main content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("JPokeBattle");
        titleLabel.setFont(FontManager.getPokemonFont(48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        centerPanel.add(titleLabel, gbc);
        
        // Start Button
        JButton startButton = new JButton("Start Battle");
        startButton.setFont(FontManager.getPokemonFont(22));
        startButton.setPreferredSize(new Dimension(300, 50));
        startButton.addActionListener(e -> startBattle());
        
        gbc.gridy = 1;
        centerPanel.add(startButton, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Credits footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // GitHub logo
        ImageIcon githubIcon = loadGithubIcon();
        JLabel iconLabel = new JLabel(githubIcon);
        iconLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/mivige"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Credits text
        JLabel creditsLabel = new JLabel("by mivige");
        creditsLabel.setFont(FontManager.getPokemonFont(12));
        creditsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creditsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/mivige"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        footerPanel.add(creditsLabel);
        //footerPanel.add(Box.createHorizontalStrut(1)); // Add small space between icon and text
        //footerPanel.add(iconLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void startBattle() {
        PokemonSelectionDialog dialog = new PokemonSelectionDialog(this, null, true);
        
        Pokemon playerPokemon = dialog.getSelectedPokemon();
        if (playerPokemon != null) {
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