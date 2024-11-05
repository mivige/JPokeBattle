package com.pokemon.ui.components;

import javax.swing.*;
import java.awt.*;
import com.pokemon.core.moves.Move;
import com.pokemon.core.pokemon.Pokemon;
import com.pokemon.utils.FontManager;

public class MoveSelectionDialog extends JDialog {
    private Move selectedMove;
    private boolean moveSelected = false;

    public MoveSelectionDialog(JFrame parent, Pokemon pokemon, Move newMove) {
        super(parent, "Replace Move", true);
        initializeDialog(pokemon, newMove);
    }

    private void initializeDialog(Pokemon pokemon, Move newMove) {
        setLayout(new BorderLayout());
        
        JPanel movePanel = new JPanel(new GridLayout(5, 1));
        JLabel titleLabel = new JLabel("Select move to replace with " + newMove.getName());
        titleLabel.setFont(FontManager.getPokemonFont(12));
        movePanel.add(titleLabel);
        
        ButtonGroup group = new ButtonGroup();
        for (Move move : pokemon.getMoves()) {
            JRadioButton moveButton = new JRadioButton(move.getName());
            moveButton.setFont(FontManager.getPokemonFont(12));
            moveButton.addActionListener(e -> selectedMove = move);
            group.add(moveButton);
            movePanel.add(moveButton);
        }
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        
        confirmButton.setFont(FontManager.getPokemonFont(12));
        cancelButton.setFont(FontManager.getPokemonFont(12));
        
        confirmButton.addActionListener(e -> {
            if (selectedMove != null) {
                moveSelected = true;
                dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        
        add(movePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(getParent());
    }

    public Move getSelectedMove() {
        return selectedMove;
    }

    public boolean isMoveSelected() {
        return moveSelected;
    }
} 