package it.pokebattle.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.io.File;

import it.pokebattle.battle.Battle;
import it.pokebattle.battle.BattleListener;
import it.pokebattle.game.GameState;
import it.pokebattle.model.Move;
import it.pokebattle.model.Pokemon;

/**
 * Classe che rappresenta la schermata di battaglia del gioco.
 * Mostra i Pokémon in campo, le loro barre dei PS e il menu delle azioni.
 */
public class BattleScreen extends JPanel implements BattleListener {
    private static final long serialVersionUID = 1L;
    
    // Componenti dell'interfaccia
    private JPanel battleFieldPanel;
    private JPanel playerPokemonPanel;
    private JPanel enemyPokemonPanel;
    private JPanel actionPanel;
    private JPanel movePanel;
    private JPanel messagePanel;
    
    private JLabel playerPokemonNameLabel;
    private JLabel playerPokemonLevelLabel;
    private JLabel playerPokemonHpLabel;
    private JProgressBar playerPokemonHpBar;
    
    private JLabel enemyPokemonNameLabel;
    private JLabel enemyPokemonLevelLabel;
    private JLabel enemyPokemonHpLabel;
    private JProgressBar enemyPokemonHpBar;
    
    private JLabel messageLabel;
    
    private JButton[] moveButtons;
    private JButton switchButton;
    private JButton runButton;
    
    private Battle battle;
    private MainScreen mainScreen; // Riferimento alla schermata principale
    
    private Timer messageTimer;
    private String fullMessage;
    private int messageCharIndex;

    private Queue<String> messageQueue = new LinkedList<>();
    private boolean isMessageShowing = false;

    private JLabel enemyPokemonImageLabel;
    private JLabel playerPokemonImageLabel;

    /**
     * Costruttore della schermata di battaglia
     * 
     * @param mainScreen Riferimento alla schermata principale
     * @param battle Battaglia da visualizzare
     */
    public BattleScreen(MainScreen mainScreen, Battle battle) {
        this.mainScreen = mainScreen;
        this.battle = battle;
        this.battle.addBattleListener(this);
        
        setLayout(new BorderLayout());
        initComponents();
        updateDisplay();
    }
    
    /**
     * Inizializza i componenti dell'interfaccia
     */
    private void initComponents() {
        battleFieldPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        battleFieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel del Pokémon nemico (info)
        enemyPokemonPanel = new JPanel(new BorderLayout());
        JPanel enemyInfoPanel = new JPanel(new GridLayout(3, 1));
        
        enemyPokemonNameLabel = new JLabel();
        enemyPokemonNameLabel.setFont(FontManager.getPokemonFont(16));
        
        enemyPokemonLevelLabel = new JLabel();
        enemyPokemonLevelLabel.setFont(FontManager.getPokemonFont(12));
        
        enemyPokemonHpBar = new JProgressBar(0, 100);
        enemyPokemonHpBar.setForeground(Color.GREEN);
        enemyPokemonHpBar.setStringPainted(true);
        
        enemyPokemonHpLabel = new JLabel();
        enemyPokemonHpLabel.setFont(FontManager.getPokemonFont(12));
        
        enemyInfoPanel.add(enemyPokemonNameLabel);
        enemyInfoPanel.add(enemyPokemonLevelLabel);
        
        JPanel enemyHpPanel = new JPanel(new BorderLayout());
        enemyHpPanel.add(enemyPokemonHpBar, BorderLayout.CENTER);
        enemyHpPanel.add(enemyPokemonHpLabel, BorderLayout.EAST);
        
        enemyInfoPanel.add(enemyHpPanel);
        enemyPokemonPanel.add(enemyInfoPanel, BorderLayout.CENTER);
        
        // Immagine Pokémon nemico
        enemyPokemonImageLabel = new JLabel();
        enemyPokemonImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel del Pokémon del giocatore (info)
        playerPokemonPanel = new JPanel(new BorderLayout());
        JPanel playerInfoPanel = new JPanel(new GridLayout(3, 1));
        
        playerPokemonNameLabel = new JLabel();
        playerPokemonNameLabel.setFont(FontManager.getPokemonFont(16));
        
        playerPokemonLevelLabel = new JLabel();
        playerPokemonLevelLabel.setFont(FontManager.getPokemonFont(12));
        
        playerPokemonHpBar = new JProgressBar(0, 100);
        playerPokemonHpBar.setForeground(Color.GREEN);
        playerPokemonHpBar.setStringPainted(true);
        
        playerPokemonHpLabel = new JLabel();
        playerPokemonHpLabel.setFont(FontManager.getPokemonFont(12));
        
        playerInfoPanel.add(playerPokemonNameLabel);
        playerInfoPanel.add(playerPokemonLevelLabel);
        
        JPanel playerHpPanel = new JPanel(new BorderLayout());
        playerHpPanel.add(playerPokemonHpBar, BorderLayout.CENTER);
        playerHpPanel.add(playerPokemonHpLabel, BorderLayout.EAST);
        
        playerInfoPanel.add(playerHpPanel);
        playerPokemonPanel.add(playerInfoPanel, BorderLayout.CENTER);

        // Immagine Pokémon giocatore
        playerPokemonImageLabel = new JLabel();
        playerPokemonImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        battleFieldPanel.add(enemyPokemonPanel);         // (0,0) info nemico
        battleFieldPanel.add(enemyPokemonImageLabel);    // (0,1) immagine nemico
        battleFieldPanel.add(playerPokemonImageLabel);   // (1,0) immagine giocatore
        battleFieldPanel.add(playerPokemonPanel);        // (1,1) info giocatore
        
        // Panel dei messaggi
        messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        messageLabel = new JLabel();
        messageLabel.setFont(FontManager.getPokemonFont(16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        messagePanel.add(messageLabel, BorderLayout.CENTER);
        
        // Panel delle azioni
        actionPanel = new JPanel(new CardLayout());
        
        // Panel principale delle azioni
        JPanel mainActionPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainActionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JButton fightButton = new JButton("LOTTA");
        fightButton.setFont(FontManager.getPokemonFont(16));
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) actionPanel.getLayout()).show(actionPanel, "moves");
                showAnimatedMessage("Quale mossa?");
            }
        });
        
        switchButton = new JButton("POKÉMON");
        switchButton.setFont(FontManager.getPokemonFont(16));
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainScreen.showPokemonSwitchScreen(battle);
            }
        });
        
        runButton = new JButton("FUGA");
        runButton.setFont(FontManager.getPokemonFont(16));
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAnimatedMessage("Sei fuggito dalla lotta!");
                // Termina la battaglia e torna alla schermata principale
                mainScreen.showGameOverScreen(false);
            }
        });
        
        mainActionPanel.add(fightButton);
        mainActionPanel.add(switchButton);
        mainActionPanel.add(runButton);
        
        // Panel delle mosse
        movePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        movePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        moveButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            moveButtons[i] = new JButton();
            moveButtons[i].setFont(FontManager.getPokemonFont(14));
            final int moveIndex = i;
            moveButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    executeMove(moveIndex);
                }
            });
            movePanel.add(moveButtons[i]);
        }
        
        JButton backButton = new JButton("INDIETRO");
        backButton.setFont(FontManager.getPokemonFont(14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) actionPanel.getLayout()).show(actionPanel, "main");
                showAnimatedMessage("Cosa farà " + battle.getPlayerPokemon().getName() + "?");
            }
        });
        
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.add(backButton);
        
        JPanel movesWithBackPanel = new JPanel(new BorderLayout());
        movesWithBackPanel.add(movePanel, BorderLayout.CENTER);
        movesWithBackPanel.add(backPanel, BorderLayout.SOUTH);

        actionPanel.add(mainActionPanel, "main");
        actionPanel.add(movesWithBackPanel, "moves");
        
        // Aggiunge i panel al layout principale
        add(battleFieldPanel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.SOUTH);
        
        // Mostra il panel principale delle azioni
        ((CardLayout) actionPanel.getLayout()).show(actionPanel, "main");

        // Mostra il messaggio iniziale con effetto macchina da scrivere
        showAnimatedMessage("Cosa farà " + battle.getPlayerPokemon().getName() + "?");
    }
    
    /**
     * Aggiorna la visualizzazione in base allo stato attuale della battaglia
     */
    public void updateDisplay() {
        Pokemon playerPokemon = battle.getPlayerPokemon();
        Pokemon enemyPokemon = battle.getEnemyPokemon();

        playerPokemonNameLabel.setText(playerPokemon.getName() + " (Lv. " + playerPokemon.getLevel() + ")");
        // Mostra HP e EXP nella riga sottostante
        playerPokemonLevelLabel.setText(
            "HP: " + playerPokemon.getCurrentHp() + "/" + playerPokemon.getMaxHp() +
            "   EXP: " + playerPokemon.getExperience() + "/" + playerPokemon.getExperienceToNextLevel()
        );
        playerPokemonHpLabel.setText("");

        int playerHpPercentage = (int) ((double) playerPokemon.getCurrentHp() / playerPokemon.getMaxHp() * 100);
        playerPokemonHpBar.setValue(playerHpPercentage);

        // Cambia il colore della barra HP in base alla percentuale
        if (playerHpPercentage < 20) {
            playerPokemonHpBar.setForeground(Color.RED);
        } else if (playerHpPercentage < 50) {
            playerPokemonHpBar.setForeground(Color.ORANGE);
        } else {
            playerPokemonHpBar.setForeground(Color.GREEN);
        }

        // Nemico
        enemyPokemonNameLabel.setText(enemyPokemon.getName() + " (Lv. " + enemyPokemon.getLevel() + ")");
        // Mostra solo HP nella riga sottostante (EXP non visibile per il nemico)
        enemyPokemonLevelLabel.setText(
            "HP: " + enemyPokemon.getCurrentHp() + "/" + enemyPokemon.getMaxHp()
        );
        enemyPokemonHpLabel.setText("");

        int enemyHpPercentage = (int) ((double) enemyPokemon.getCurrentHp() / enemyPokemon.getMaxHp() * 100);
        enemyPokemonHpBar.setValue(enemyHpPercentage);
        
        // Cambia il colore della barra HP in base alla percentuale
        if (enemyHpPercentage < 20) {
            enemyPokemonHpBar.setForeground(Color.RED);
        } else if (enemyHpPercentage < 50) {
            enemyPokemonHpBar.setForeground(Color.ORANGE);
        } else {
            enemyPokemonHpBar.setForeground(Color.GREEN);
        }

        // Aggiorna i pulsanti delle mosse
        List<Move> moves = playerPokemon.getMoves();
        for (int i = 0; i < 4; i++) {
            if (i < moves.size()) {
                Move move = moves.get(i);
                moveButtons[i].setText(move.getName() + " (" + move.getCurrentPp() + "/" + move.getPp() + ")");
                moveButtons[i].setEnabled(move.getCurrentPp() > 0 && !battle.isBattleEnded());
            } else {
                moveButtons[i].setText("-");
                moveButtons[i].setEnabled(false);
            }
        }
        
        // Disabilita i pulsanti se la battaglia è terminata
        switchButton.setEnabled(!battle.isBattleEnded());
        runButton.setEnabled(!battle.isBattleEnded());

        // Aggiorna immagini
        setPokemonImage(enemyPokemonImageLabel, enemyPokemon.getSpecies());
        setPokemonImage(playerPokemonImageLabel, playerPokemon.getSpecies());
    }
    
    /**
     * Esegue una mossa del Pokémon del giocatore
     * 
     * @param moveIndex Indice della mossa da eseguire
     */
    private void executeMove(int moveIndex) {
        // Disabilita i pulsanti durante l'esecuzione della mossa
        for (int i = 0; i < 3; i++) {
            moveButtons[i].setEnabled(false);
        }
        
        // Esegue la mossa
        battle.executePlayerMove(moveIndex);
        
        // Torna al menu principale delle azioni
        ((CardLayout) actionPanel.getLayout()).show(actionPanel, "main");
        
        // Aggiorna la visualizzazione
        updateDisplay();
        
        // Controlla se la battaglia è terminata
        if (battle.isBattleEnded()) {
            if (battle.hasPlayerWon()) {
                showAnimatedMessage("Hai vinto la battaglia!");
                GameState.getInstance().saveBattleResult(true);
                mainScreen.showVictoryScreen();
            } else {
                showAnimatedMessage("Hai perso la battaglia!");
                mainScreen.showGameOverScreen(true);
                GameState.getInstance().saveBattleResult(false);
            }
        } else {
            showAnimatedMessage("Cosa farà " + battle.getPlayerPokemon().getName() + "?");
        }
    }
    
    /**
     * Aggiorna la schermata dopo un cambio di Pokémon
     */
    public void updateAfterSwitch() {
        updateDisplay();
        showAnimatedMessage("Cosa farà " + battle.getPlayerPokemon().getName() + "?");
    }
    
    /**
     * Mostra un messaggio con effetto "macchina da scrivere" e gestisce la coda dei messaggi
     */
    private void showAnimatedMessage(String message) {
        messageQueue.add(message);
        if (!isMessageShowing) {
            showNextMessage();
        }
    }

    /**
     * Mostra il prossimo messaggio nella coda, se presente
     */
    private void showNextMessage() {
        if (messageQueue.isEmpty()) {
            isMessageShowing = false;
            return;
        }
        isMessageShowing = true;
        fullMessage = messageQueue.poll();
        messageCharIndex = 0;
        messageLabel.setText("");
        int delay = 25; // ms per carattere
        messageTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (messageCharIndex < fullMessage.length()) {
                    messageLabel.setText(fullMessage.substring(0, messageCharIndex + 1));
                    messageCharIndex++;
                } else {
                    messageTimer.stop();
                    // Dopo una breve pausa, mostra il prossimo messaggio
                    Timer pause = new Timer(600, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showNextMessage();
                        }
                    });
                    pause.setRepeats(false);
                    pause.start();
                }
            }
        });
        messageTimer.start();
    }
    
    /**
     * Imposta l'immagine del Pokémon nella label, se disponibile e la ridimensiona
     * all'altezza massima della riga.
     */
    private void setPokemonImage(JLabel label, String species) {
        try {
            java.net.URL url = getClass().getClassLoader().getResource("images/" + species + ".png");
            if (url == null) {
                label.setIcon(null);
                return;
            }
            ImageIcon originalIcon = new ImageIcon(url);
            // Calcola l'altezza massima della riga
            int rowHeight = label.getHeight();
            if (rowHeight <= 0) rowHeight = 128; // fallback
            int imgWidth = originalIcon.getIconWidth();
            int imgHeight = originalIcon.getIconHeight();
            // Scala proporzionalmente
            int newHeight = rowHeight;
            int newWidth = (int) ((double) imgWidth / imgHeight * newHeight);
            java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            label.setIcon(null);
        }
    }

    // Implementazione dei metodi dell'interfaccia BattleListener
    
    @Override
    public void onMoveUsed(Pokemon pokemon, Move move, boolean isPlayer) {
        String message = pokemon.getName() + " usa " + move.getName() + "!";
        showAnimatedMessage(message);
    }

    @Override
    public void onMoveFailed(Pokemon pokemon, Move move, String reason, boolean isPlayer) {
        String message = pokemon.getName() + " non può usare " + move.getName() + "! " + reason;
        showAnimatedMessage(message);
    }

    @Override
    public void onMoveMissed(Pokemon attacker, Pokemon defender, Move move, boolean isPlayer) {
        String message = attacker.getName() + " manca il bersaglio!";
        showAnimatedMessage(message);
    }

    @Override
    public void onDamageDealt(Pokemon attacker, Pokemon defender, Move move, int damage, 
                            int effectiveness, boolean critical, boolean fainted, boolean isPlayer) {
        // Aggiorna la visualizzazione
        updateDisplay();
        
        // Mostra un messaggio appropriato in base all'efficacia
        String effectivenessMessage = "";
        switch (effectiveness) {
            case Battle.RESULT_SUPER_EFFECTIVE:
                effectivenessMessage = "È superefficace!";
                break;
            case Battle.RESULT_NOT_VERY_EFFECTIVE:
                effectivenessMessage = "Non è molto efficace...";
                break;
            case Battle.RESULT_NO_EFFECT:
                effectivenessMessage = "Non ha effetto...";
                break;
        }
        
        // Mostra un messaggio per i colpi critici
        String criticalMessage = critical ? "Colpo critico!" : "";
        
        // Mostra un messaggio se il Pokémon è stato messo KO
        String faintedMessage = fainted ? defender.getName() + " è esausto!" : "";
        
        // Combina i messaggi
        StringBuilder message = new StringBuilder();
        if (!effectivenessMessage.isEmpty()) message.append(effectivenessMessage).append(" ");
        if (!criticalMessage.isEmpty()) message.append(criticalMessage).append(" ");
        if (!faintedMessage.isEmpty()) message.append(faintedMessage);
        if (message.length() > 0) {
            showAnimatedMessage(message.toString().trim());
        }
    }

    @Override
    public void onPokemonFainted(Pokemon pokemon, boolean isPlayerPokemon) {
        // Aggiorna la visualizzazione
        updateDisplay();
        if (isPlayerPokemon && !battle.isBattleEnded()) {
            boolean hasValidPokemon = false;
            for (Pokemon p : battle.getPlayerTeam()) {
                if (!p.isKO() && p != pokemon) {
                    hasValidPokemon = true;
                    break;
                }
            }
            
            // Se ci sono altri Pokémon disponibili, mostra la schermata di cambio
            if (hasValidPokemon) {
                // Disabilita i pulsanti durante la selezione
                for (int i = 0; i < 3; i++) {
                    moveButtons[i].setEnabled(false);
                }
                switchButton.setEnabled(false);
                runButton.setEnabled(false);
                showAnimatedMessage(pokemon.getName() + " è KO! Scegli un altro Pokémon!");
                mainScreen.showPokemonSwitchScreen(battle);
            }
        }
    }

    @Override
    public void onPokemonSwitched(Pokemon oldPokemon, Pokemon newPokemon, boolean isPlayer) {
        // Aggiorna la visualizzazione dopo il cambio
        updateDisplay();
        
        // Mostra un messaggio appropriato
        String message;
        if (isPlayer) {
            message = oldPokemon.getName() + " rientra! Vai " + newPokemon.getName() + "!";
        } else {
            message = "L'avversario richiama " + oldPokemon.getName() + " e manda in campo " + newPokemon.getName() + "!";
        }
        showAnimatedMessage(message);
    }

    @Override
    public void onExperienceGained(Pokemon pokemon, int expGained) {
        // Mostra un messaggio per l'esperienza guadagnata
        String message = pokemon.getName() + " guadagna " + expGained + " punti esperienza!";
        showAnimatedMessage(message);
    }

    @Override
    public void onLevelUp(Pokemon pokemon, int newLevel) {
        // Mostra un messaggio per il livello aumentato
        String message = pokemon.getName() + " sale al livello " + newLevel + "!";
        showAnimatedMessage(message);
        updateDisplay();
    }

    @Override
    public void onPokemonEvolved(Pokemon oldPokemon, Pokemon evolvedPokemon) {
        // Mostra un messaggio per l'evoluzione
        String message = "Congratulazioni! " + oldPokemon.getName() + " si è evoluto in " + evolvedPokemon.getName() + "!";
        showAnimatedMessage(message);
        updateDisplay();
    }

    @Override
    public void onBattleStart(List<Pokemon> playerTeam, List<Pokemon> enemyTeam) {
        // Mostra un messaggio per l'inizio della battaglia
        String message = "Inizia la battaglia!";
        showAnimatedMessage(message);
        updateDisplay();
    }

    @Override
    public void onBattleEnd(boolean playerWon) {
        updateDisplay();
    }
}