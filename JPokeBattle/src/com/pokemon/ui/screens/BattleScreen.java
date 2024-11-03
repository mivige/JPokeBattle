package com.pokemon.ui.screens;

import javax.swing.*;
import java.awt.*;
import com.pokemon.core.battle.Battle;
import com.pokemon.ui.components.PokemonInfoPanel;
import com.pokemon.ui.components.BattleControlPanel;

public class BattleScreen extends JFrame {
    private JPanel mainPanel;
    private PokemonInfoPanel player1Info;
    private PokemonInfoPanel player2Info;
    private BattleControlPanel controlPanel;
    private Battle battle;
    private StartScreen startScreen;

    public BattleScreen(Battle battle, StartScreen startScreen) {
        this.battle = battle;
        this.startScreen = startScreen;
        initializeScreen();
    }

    private void initializeScreen() {
        setTitle("JPokeBattle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        
        // Create info panels for both Pokemon
        player1Info = new PokemonInfoPanel(battle.getPlayer1Pokemon());
        player2Info = new PokemonInfoPanel(battle.getPlayer2Pokemon());
        
        // Create battle field panel
        JPanel battleField = new JPanel(new GridLayout(2, 1));
        battleField.add(player2Info);
        battleField.add(player1Info);
        
        // Create control panel
        controlPanel = new BattleControlPanel(battle, this);

        mainPanel.add(battleField, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    public void updateScreen() {
        player1Info.updateInfo();
        player2Info.updateInfo();
        controlPanel.updateControls();
        repaint();
    }

    public void endBattle(boolean playerWon) {
        BattleEndScreen endScreen = new BattleEndScreen(playerWon, startScreen);
        endScreen.setVisible(true);
        this.dispose();
    }
}