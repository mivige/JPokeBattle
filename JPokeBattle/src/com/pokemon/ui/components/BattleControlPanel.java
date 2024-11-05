package com.pokemon.ui.components;

import javax.swing.*;
import java.awt.*;
import com.pokemon.core.battle.Battle;
import com.pokemon.core.moves.Move;
import com.pokemon.ui.screens.BattleScreen;
import com.pokemon.core.pokemon.Pokemon;
import java.util.List;
import com.pokemon.utils.FontManager;

public class BattleControlPanel extends JPanel {
    private Battle battle;
    private JButton[] moveButtons;
    private JButton switchButton;
    private BattleScreen battleScreen;
    private BattleLogPanel battleLog;
    private boolean isExecutingMove = false;

    public BattleControlPanel(Battle battle, BattleScreen battleScreen) {
        this.battle = battle;
        this.battleScreen = battleScreen;
        
        setLayout(new BorderLayout());
        initializeControls();
        updateControls();
        
        battle.setBattleLogger(message -> displayBattleMessage(message));
    }

    private void initializeControls() {
        // Move buttons panel
        JPanel movesPanel = new JPanel(new GridLayout(2, 2));
        moveButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            final int moveIndex = i;
            moveButtons[i] = new JButton();
            moveButtons[i].setFont(FontManager.getPokemonFont(12));
            moveButtons[i].setEnabled(false);
            moveButtons[i].addActionListener(e -> executeMove(moveIndex));
            movesPanel.add(moveButtons[i]);
        }
        
        // Switch button
        switchButton = new JButton("Switch Pokemon");
        switchButton.setFont(FontManager.getPokemonFont(12));
        switchButton.addActionListener(e -> handlePokemonSwitch());
        
        // Battle log panel
        battleLog = new BattleLogPanel();
        
        JPanel controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.add(movesPanel, BorderLayout.CENTER);
        controlsPanel.add(switchButton, BorderLayout.SOUTH);
        
        add(controlsPanel, BorderLayout.CENTER);
        add(battleLog, BorderLayout.SOUTH);
    }
    
    private void executeMove(int moveIndex) {
        if (isExecutingMove) return;
        
        List<Move> moves = battle.getPlayer1Pokemon().getMoves();
        if (moveIndex < moves.size()) {
            isExecutingMove = true;
            setButtonsEnabled(false);
            
            battle.executeMove(moves.get(moveIndex));
            battleScreen.updateScreen();
            
            if (battle.isBattleOver()) {
                handleBattleEnd();
            }
        }
    }
    
    private void displayBattleMessage(String message) {
        battleLog.queueMessage(message);
        
        // Re-enable buttons after all messages are displayed
        Timer enableTimer = new Timer(4500, e -> {
            isExecutingMove = false;
            if (!battle.isBattleOver()) {
                setButtonsEnabled(true);
            }
            ((Timer)e.getSource()).stop();
        });
        enableTimer.setRepeats(false);
        enableTimer.start();
    }
    
    private void handlePokemonSwitch() {
        if (isExecutingMove) return;
        
        PokemonSelectionDialog dialog = new PokemonSelectionDialog(battleScreen, battle, true);
        
        Pokemon newPokemon = dialog.getSelectedPokemon();
        if (newPokemon != null) {
            isExecutingMove = true;
            setButtonsEnabled(false);
            
            battleLog.queueMessage(battle.getPlayer1Pokemon().getName() + ", come back!");
            battle.switchPlayerPokemon(newPokemon);
            battleScreen.updatePokemonInfo(newPokemon, true);
            battleLog.queueMessage("Go, " + newPokemon.getName() + "!");
            
            // Update move buttons immediately after switch
            updateControls();
            
            // Enemy turn after switch
            Timer enemyTimer = new Timer(4500, e -> {
                battle.executeEnemyTurn();
                battleScreen.updateScreen();
                
                if (!battle.isBattleOver()) {
                    isExecutingMove = false;
                    setButtonsEnabled(true);
                    updateControls();  // Update controls again after enemy turn
                } else {
                    handleBattleEnd();
                }
                ((Timer)e.getSource()).stop();
            });
            enemyTimer.setRepeats(false);
            enemyTimer.start();
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        for (JButton button : moveButtons) {
            if (!button.getText().equals("-")) {
                button.setEnabled(enabled);
            }
        }
        switchButton.setEnabled(enabled);
    }

    private void handleBattleEnd() {
        isExecutingMove = true;
        setButtonsEnabled(false);
        boolean playerWon = battle.getPlayer1Pokemon().getCurrentHP() > 0;
        
        // Show battle end screen after messages finish
        Timer endTimer = new Timer(4500, e -> {
            battleScreen.endBattle(playerWon);
            ((Timer)e.getSource()).stop();
        });
        endTimer.setRepeats(false);
        endTimer.start();
    }

    public void updateControls() {
        List<Move> pokemonMoves = battle.getPlayer1Pokemon().getMoves();
        for (int i = 0; i < moveButtons.length; i++) {
            if (i < pokemonMoves.size()) {
                Move move = pokemonMoves.get(i);
                moveButtons[i].setText(move.getName());
                moveButtons[i].setEnabled(!isExecutingMove);
            } else {
                moveButtons[i].setText("-");
                moveButtons[i].setEnabled(false);
            }
        }
        switchButton.setEnabled(!isExecutingMove);
    }
}