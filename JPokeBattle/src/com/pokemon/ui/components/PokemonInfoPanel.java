package com.pokemon.ui.components;

import javax.swing.*;
import java.awt.*;
import com.pokemon.core.pokemon.Pokemon;

public class PokemonInfoPanel extends JPanel {
    private Pokemon pokemon;
    private JLabel nameLabel;
    private JLabel hpLabel;
    private JProgressBar hpBar;

    public PokemonInfoPanel(Pokemon pokemon) {
        this.pokemon = pokemon;
        setLayout(new BorderLayout());
        initializeComponents();
    }

    public void setPokemon(Pokemon newPokemon) {
        this.pokemon = newPokemon;
        updateComponents();
    }

    private void updateComponents() {
        nameLabel.setText(pokemon.getName() + " Lv." + pokemon.getLevel());
        hpLabel.setText("HP: " + pokemon.getCurrentHP() + "/" + pokemon.getStats().getHP());
        hpBar.setMaximum(pokemon.getStats().getHP());
        hpBar.setValue(pokemon.getCurrentHP());
    }

    private void initializeComponents() {
        nameLabel = new JLabel(pokemon.getName() + " Lv." + pokemon.getLevel());
        hpLabel = new JLabel("HP: " + pokemon.getCurrentHP() + "/" + pokemon.getStats().getHP());
        
        hpBar = new JProgressBar(0, pokemon.getStats().getHP());
        hpBar.setValue(pokemon.getCurrentHP());
        hpBar.setStringPainted(true);
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(hpLabel);
        
        add(infoPanel, BorderLayout.NORTH);
        add(hpBar, BorderLayout.CENTER);
    }

    public void updateInfo() {
        hpLabel.setText("HP: " + pokemon.getCurrentHP() + "/" + pokemon.getStats().getHP());
        hpBar.setValue(pokemon.getCurrentHP());
    }
}