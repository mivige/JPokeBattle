package com.pokemon.ui.components;

import javax.swing.*;
import java.awt.*;
import com.pokemon.core.pokemon.*;
import com.pokemon.core.battle.Battle;
import java.util.List;

public class PokemonSelectionDialog extends JDialog {
    private Pokemon selectedPokemon;
    private Pokemon[] availablePokemon = {
        new Bulbo(5),
        new Carmine(5),
        new Michelangelo(5)
    };
    private Battle battle;

    public PokemonSelectionDialog(JFrame parent, Battle battle, boolean modal) {
        super(parent, "Select Pokemon", modal);
        this.battle = battle;
        initializeDialog();
        setVisible(true);
    }

    private void initializeDialog() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Choose your Pokemon");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(titleLabel, gbc);
        
        // Pokemon buttons
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        int col = 0;
        
        for (Pokemon pokemon : availablePokemon) {
            // Get stored Pokemon state if it exists
            Pokemon storedPokemon = battle != null ? battle.getStoredPokemon(pokemon) : pokemon;
            if (storedPokemon == null) storedPokemon = pokemon;
            
            JButton button = new JButton();
            button.setLayout(new BorderLayout());
            
            // Create a panel for Pokemon info using stored Pokemon state
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.add(new JLabel(storedPokemon.getName()));
            infoPanel.add(new JLabel("HP: " + storedPokemon.getCurrentHP() + "/" + storedPokemon.getStats().getHP()));
            
            button.add(infoPanel, BorderLayout.CENTER);
            button.setPreferredSize(new Dimension(150, 60));
            
            // Use the stored Pokemon when selected
            final Pokemon finalStoredPokemon = storedPokemon;
            button.addActionListener(e -> {
                selectedPokemon = finalStoredPokemon;
                dispose();
            });
            
            gbc.gridx = col++;
            add(button, gbc);
        }

        pack();
        setLocationRelativeTo(getParent());
    }

    public Pokemon getSelectedPokemon() {
        return selectedPokemon;
    }
} 