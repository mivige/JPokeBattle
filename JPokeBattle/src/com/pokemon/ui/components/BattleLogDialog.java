package com.pokemon.ui.components;

import javax.swing.*;
import java.awt.*;

public class BattleLogDialog extends JDialog {
    private JTextArea logArea;
    
    public BattleLogDialog(JFrame parent) {
        super(parent, "Battle Log", false);
        initializeDialog();
    }
    
    private void initializeDialog() {
        setSize(300, 150);
        setLocationRelativeTo(getParent());
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
    }
    
    public void addMessage(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public void clear() {
        logArea.setText("");
    }
} 