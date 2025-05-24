package it.pokebattle.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import it.pokebattle.battle.Battle;
import it.pokebattle.game.GameState;
import it.pokebattle.model.Pokemon;
import it.pokebattle.model.PokemonFactory;

/**
 * Classe che rappresenta la schermata principale del gioco.
 * Gestisce il passaggio tra le varie schermate e l'interazione con l'utente.
 */
public class MainScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Costanti per le schermate
    private static final String SCREEN_START = "start";
    private static final String SCREEN_BATTLE = "battle";
    private static final String SCREEN_SWITCH = "switch";
    private static final String SCREEN_VICTORY = "victory";
    private static final String SCREEN_GAME_OVER = "gameOver";
    private static final String SCREEN_LEADERBOARD = "leaderboard";
    
    private JPanel mainPanel; // Panel principale con CardLayout
    private CardLayout cardLayout; // Layout per gestire le schermate
    
    private BattleScreen battleScreen; // Schermata di battaglia
    private Battle currentBattle; // Battaglia corrente
    
    /**
     * Costruttore della schermata principale
     */
    public MainScreen() {
        setTitle("JPokeBattle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inizializza il layout principale
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        // Inizializza le schermate
        initStartScreen();
        initVictoryScreen();
        initGameOverScreen();
        initLeaderboardScreen();

        // Mostra la schermata iniziale
        cardLayout.show(mainPanel, SCREEN_START);
    }
    
    /**
     * Inizializza la schermata iniziale
     */
    private void initStartScreen() {
        JPanel startScreen = new JPanel(new BorderLayout());
        startScreen.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Titolo
        JLabel titleLabel = new JLabel("JPokeBattle");
        titleLabel.setFont(FontManager.getPokemonFont(48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startScreen.add(titleLabel, BorderLayout.NORTH);

        // Pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 20, 20));

        JButton newGameButton = new JButton("Nuova Partita");
        newGameButton.setFont(FontManager.getPokemonFont(24));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        JButton continueButton = new JButton("Continua");
        continueButton.setFont(FontManager.getPokemonFont(24));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueSavedGame();
            }
        });

        JButton leaderboardButton = new JButton("Classifica");
        leaderboardButton.setFont(FontManager.getPokemonFont(24));
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initLeaderboardScreen();
                cardLayout.show(mainPanel, SCREEN_LEADERBOARD);
            }
        });

        JButton exitButton = new JButton("Esci");
        exitButton.setFont(FontManager.getPokemonFont(24));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Bottone per eliminare il salvataggio
        JButton resetButton = new JButton("Reset Salvataggio");
        resetButton.setFont(FontManager.getPokemonFont(10));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.RED);
        resetButton.setPreferredSize(new Dimension(140, 28));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File saveFile = new File(GameState.SAVE_FILE);
                if (saveFile.exists()) {
                    saveFile.delete();
                }
                // Reinizializza lo stato del gioco
                GameState.resetInstance();
                JOptionPane.showMessageDialog(MainScreen.this, "Salvataggio eliminato! Il gioco è stato reimpostato.");
                // Torna al menu principale
                cardLayout.show(mainPanel, SCREEN_START);
            }
        });

        buttonPanel.add(newGameButton);
        buttonPanel.add(continueButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(resetButton); 

        startScreen.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(startScreen, SCREEN_START);
    }
    
    /**
     * Inizializza la schermata di vittoria
     */
    private void initVictoryScreen() {
        JPanel victoryScreen = new JPanel(new BorderLayout());
        victoryScreen.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Titolo
        JLabel titleLabel = new JLabel("Hai Vinto!");
        titleLabel.setFont(FontManager.getPokemonFont(48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        victoryScreen.add(titleLabel, BorderLayout.NORTH);
        
        // Messaggio
        JLabel messageLabel = new JLabel("Congratulazioni! Hai sconfitto l'avversario!");
        messageLabel.setFont(FontManager.getPokemonFont(24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        victoryScreen.add(messageLabel, BorderLayout.CENTER);
        
        // Pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton continueButton = new JButton("Continua");
        continueButton.setFont(FontManager.getPokemonFont(24));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNextBattle();
            }
        });
        
        JButton mainMenuButton = new JButton("Menu Principale");
        mainMenuButton.setFont(FontManager.getPokemonFont(24));
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, SCREEN_START);
            }
        });
        
        buttonPanel.add(continueButton);
        buttonPanel.add(mainMenuButton);
        
        victoryScreen.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(victoryScreen, SCREEN_VICTORY);
    }
    
    /**
     * Inizializza la schermata di game over
     */
    private void initGameOverScreen() {
        JPanel gameOverScreen = new JPanel(new BorderLayout());
        gameOverScreen.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Titolo
        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setFont(FontManager.getPokemonFont(48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverScreen.add(titleLabel, BorderLayout.NORTH);
        
        // Messaggio
        JLabel messageLabel = new JLabel("La partita è terminata.");
        messageLabel.setFont(FontManager.getPokemonFont(24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverScreen.add(messageLabel, BorderLayout.CENTER);
        
        // Pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton newGameButton = new JButton("Nuova Partita");
        newGameButton.setFont(FontManager.getPokemonFont(24));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        
        JButton mainMenuButton = new JButton("Menu Principale");
        mainMenuButton.setFont(FontManager.getPokemonFont(24));
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, SCREEN_START);
            }
        });
        
        buttonPanel.add(newGameButton);
        buttonPanel.add(mainMenuButton);
        
        gameOverScreen.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(gameOverScreen, SCREEN_GAME_OVER);
    }
    
    /**
     * Inizializza la schermata della classifica
     */
    private void initLeaderboardScreen() {
        JPanel leaderboardScreen = new JPanel(new BorderLayout());
        leaderboardScreen.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Titolo
        JLabel titleLabel = new JLabel("Classifica");
        titleLabel.setFont(FontManager.getPokemonFont(48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardScreen.add(titleLabel, BorderLayout.NORTH);

        // Ottieni e ordina la leaderboard
        java.util.Map<String, Integer> leaderboard = GameState.getInstance().getLeaderboard();
        java.util.List<java.util.Map.Entry<String, Integer>> entries = new java.util.ArrayList<>(leaderboard.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Prepara i dati per la tabella
        String[] columnNames = {"Posizione", "Giocatore", "Punteggio"};
        String[][] data = new String[Math.min(10, entries.size())][3];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = entries.get(i).getKey();
            data[i][2] = String.valueOf(entries.get(i).getValue());
        }

        javax.swing.JTable table = new javax.swing.JTable(data, columnNames);
        table.setEnabled(false);
        table.setFont(FontManager.getPokemonFont(18));
        table.getTableHeader().setFont(FontManager.getPokemonFont(20));
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);
        leaderboardScreen.add(scrollPane, BorderLayout.CENTER);

        // Pulsante per tornare al menu principale
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Indietro");
        backButton.setFont(FontManager.getPokemonFont(24));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, SCREEN_START);
            }
        });
        buttonPanel.add(backButton);

        leaderboardScreen.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(leaderboardScreen, SCREEN_LEADERBOARD);
    }
    
    /**
     * Inizia una nuova partita
     */
    private void startNewGame() {
        // Chiede all'utente di scegliere il Pokémon iniziale
        Object[] options = {"Bulbo (Bulbasaur)", "Carmine (Charmander)", "Michelangelo (Squirtle)"};
        int choice = JOptionPane.showOptionDialog(this,
                "Scegli il tuo Pokémon iniziale:",
                "Nuovo Gioco",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        
        String starterSpecies;
        switch (choice) {
            case 0:
                starterSpecies = "Bulbasaur";
                break;
            case 1:
                starterSpecies = "Charmander";
                break;
            case 2:
                starterSpecies = "Squirtle";
                break;
            default:
                return; // L'utente ha annullato
        }
        
        // Inizializza una nuova partita
        GameState.getInstance().startNewGame(starterSpecies);
        
        // Inizia la prima battaglia
        startNextBattle();
    }
    
    /**
     * Continua una partita salvata
     */
    private void continueSavedGame() {
        // Controlla se esiste un salvataggio
        if (GameState.getInstance().getPlayerTeam().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nessun salvataggio trovato. Inizia una nuova partita.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Controlla se tutti i Pokémon sono esausti
        boolean allFainted = true;
        for (Pokemon pokemon : GameState.getInstance().getPlayerTeam()) {
            if (!pokemon.isKO()) {
                allFainted = false;
                break;
            }
        }
        
        if (allFainted) {
            JOptionPane.showMessageDialog(this,
                    "Tutti i tuoi Pokémon sono esausti! Inizia una nuova partita.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Inizia la prossima battaglia
        startNextBattle();
    }
    
    /**
     * Inizia la prossima battaglia
     */
    private void startNextBattle() {
        // Ottiene la squadra del giocatore
        List<Pokemon> playerTeam = GameState.getInstance().getPlayerTeam();
        if (playerTeam == null || playerTeam.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Errore: la tua squadra è vuota! Impossibile continuare.",
                "Errore squadra vuota",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ripristina i PP di tutte le mosse per ogni Pokémon
        for (Pokemon p : playerTeam) {
            p.restoreAllPP();
        }

        // Controlla che ci sia almeno un Pokémon non KO
        boolean allKO = true;
        for (Pokemon p : playerTeam) {
            if (!p.isKO()) {
                allKO = false;
                break;
            }
        }
        if (allKO) {
            JOptionPane.showMessageDialog(this,
                "Tutti i tuoi Pokémon sono esausti! Inizia una nuova partita.",
                "Errore",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Crea una squadra avversaria di livello appropriato
        List<Pokemon> enemyTeam = createEnemyTeam(playerTeam);
        
        // Crea una nuova battaglia
        currentBattle = new Battle(playerTeam, enemyTeam);
        GameState.getInstance().setCurrentBattle(currentBattle);

        // Imposta il Pokémon attivo corretto
        int activeIndex = GameState.getInstance().getActivePokemonIndex();
        if (activeIndex >= 0 && activeIndex < playerTeam.size() && !playerTeam.get(activeIndex).isKO()) {
            currentBattle.setPlayerActivePokemon(activeIndex);
        } else {
            // Se il Pokémon attivo è KO, scegli il primo non KO
            for (int i = 0; i < playerTeam.size(); i++) {
                if (!playerTeam.get(i).isKO()) {
                    currentBattle.setPlayerActivePokemon(i);
                    GameState.getInstance().setActivePokemonIndex(i);
                    break;
                }
            }
        }

        battleScreen = new BattleScreen(this, currentBattle);
        
        // Aggiunge la schermata di battaglia al panel principale
        mainPanel.add(battleScreen, SCREEN_BATTLE);
        
        // Mostra la schermata di battaglia
        cardLayout.show(mainPanel, SCREEN_BATTLE);
    }
    
    /**
     * Crea una squadra avversaria di livello appropriato
     * 
     * @param playerTeam Squadra del giocatore
     * @return Squadra avversaria
     */
    private List<Pokemon> createEnemyTeam(List<Pokemon> playerTeam) {
        if (playerTeam == null || playerTeam.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Errore: la tua squadra è vuota! Impossibile continuare.",
                "Errore squadra vuota",
                JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
        
        List<Pokemon> enemyTeam = new ArrayList<>();
        
        // Calcola il livello medio della squadra del giocatore
        int averageLevel = 5; // default
        if (!playerTeam.isEmpty()) {
            int totalLevel = 0;
            for (Pokemon pokemon : playerTeam) {
                totalLevel += pokemon.getLevel();
            }
            averageLevel = totalLevel / playerTeam.size();
        }
        
        // Crea una squadra avversaria con Pokémon di livello simile
        List<String> availableSpecies = PokemonFactory.getAllSpecies();
        
        // Crea da 3 a 5 Pokémon avversari
        int enemyTeamSize = Math.min(3 + GameState.getInstance().getConsecutiveWins() / 2, 5);
        if (enemyTeamSize < 3) enemyTeamSize = 3;
        for (int i = 0; i < enemyTeamSize; i++) {
            // Sceglie una specie casuale
            String species = availableSpecies.get((int) (Math.random() * availableSpecies.size()));
            
            // Crea un Pokémon di livello appropriato
            int level = averageLevel + (int) (Math.random() * 3) - 1; // Livello medio ±1
            Pokemon enemy = PokemonFactory.createPokemon(species, level);
            
            enemyTeam.add(enemy);
        }
        
        return enemyTeam;
    }
    
    /**
     * Mostra la schermata di cambio Pokémon
     * 
     * @param battle Battaglia corrente
     */
    public void showPokemonSwitchScreen(Battle battle) {
        // Crea la schermata di cambio Pokémon
        JPanel switchScreen = new JPanel(new BorderLayout());
        switchScreen.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Titolo
        JLabel titleLabel = new JLabel("Cambia Pokémon");
        titleLabel.setFont(FontManager.getPokemonFont(24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        switchScreen.add(titleLabel, BorderLayout.NORTH);
        
        // Ottieni tutti i Pokémon disponibili al giocatore
        List<Pokemon> teamPokemon = battle.getPlayerTeam();
        // Assicurati di ottenere la lista aggiornata dei Pokémon sbloccati
        List<String> unlockedSpecies = GameState.getInstance().getUnlockedSpecies();
        // Assicurati che i Pokémon iniziali siano sempre disponibili
        if (!unlockedSpecies.contains("Bulbasaur")) unlockedSpecies.add("Bulbasaur");
        if (!unlockedSpecies.contains("Charmander")) unlockedSpecies.add("Charmander");
        if (!unlockedSpecies.contains("Squirtle")) unlockedSpecies.add("Squirtle");
        
        // Crea un pannello con scroll per contenere tutti i Pokémon
        JPanel pokemonListPanel = new JPanel();
        pokemonListPanel.setLayout(new BoxLayout(pokemonListPanel, BoxLayout.Y_AXIS));
        
        // Aggiungi prima i Pokémon nel team attuale
        JLabel teamLabel = new JLabel("Il tuo team:");
        teamLabel.setFont(FontManager.getPokemonFont(18));
        teamLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        pokemonListPanel.add(teamLabel);
        pokemonListPanel.add(Box.createVerticalStrut(10));
        
        for (int i = 0; i < teamPokemon.size(); i++) {
            final Pokemon pokemon = teamPokemon.get(i);
            final int index = i;
            
            // Crea un panel per ogni Pokémon
            JPanel pokemonPanel = new JPanel(new BorderLayout());
            pokemonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            // Nome e livello
            JLabel nameLabel = new JLabel(pokemon.getName() + " (Lv. " + pokemon.getLevel() + ")");
            nameLabel.setFont(FontManager.getPokemonFont(16));
            pokemonPanel.add(nameLabel, BorderLayout.WEST);
            
            // HP
            JLabel hpLabel = new JLabel(pokemon.getCurrentHp() + "/" + pokemon.getMaxHp() + " HP");
            hpLabel.setFont(FontManager.getPokemonFont(14));
            pokemonPanel.add(hpLabel, BorderLayout.EAST);
            
            // Disabilita il pulsante se il Pokémon è KO o è già in campo
            boolean isKO = pokemon.isKO();
            boolean isActive = pokemon == battle.getPlayerPokemon();
            
            // Rende il panel cliccabile solo se il Pokémon non è KO e non è già in campo
            if (!isKO && !isActive) {
                pokemonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                pokemonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Cambia il Pokémon
                        battle.executePlayerSwitch(index);
                        
                        // Torna alla schermata di battaglia
                        cardLayout.show(mainPanel, SCREEN_BATTLE);
                        
                        // Aggiorna la schermata di battaglia
                        battleScreen.updateAfterSwitch();
                    }
                });
            } else {
                // Grigia il panel se il Pokémon è KO o è già in campo
                pokemonPanel.setEnabled(false);
                nameLabel.setEnabled(false);
                hpLabel.setEnabled(false);
                
                if (isActive) {
                    nameLabel.setText(nameLabel.getText() + " (In campo)");
                } else if (isKO) {
                    nameLabel.setText(nameLabel.getText() + " (KO)");
                }
            }
            
            pokemonListPanel.add(pokemonPanel);
            pokemonListPanel.add(Box.createVerticalStrut(5));
        }
        
        // Aggiungi una separazione tra il team e i Pokémon sbloccati
        pokemonListPanel.add(Box.createVerticalStrut(20));
        
        // Aggiungi i Pokémon sbloccati che non sono nel team
        JLabel unlockedLabel = new JLabel("Pokémon disponibili:");
        unlockedLabel.setFont(FontManager.getPokemonFont(18));
        unlockedLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        pokemonListPanel.add(unlockedLabel);
        pokemonListPanel.add(Box.createVerticalStrut(10));
        
        // Crea un pannello con scroll per contenere tutti i Pokémon
        JScrollPane scrollPane = new JScrollPane(pokemonListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Aggiungi i Pokémon sbloccati che non sono nel team
        for (String species : unlockedSpecies) {
            // Verifica se questa specie è già nel team
            boolean alreadyInTeam = false;
            for (Pokemon pokemon : teamPokemon) {
                if (pokemon.getSpecies().equals(species)) {
                    alreadyInTeam = true;
                    break;
                }
            }
            
            // Se non è già nel team, mostralo come opzione disponibile
            if (!alreadyInTeam) {
                final String pokemonSpecies = species;
                
                // Crea un panel per ogni Pokémon sbloccato
                JPanel pokemonPanel = new JPanel(new BorderLayout());
                pokemonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                
                // Nome della specie
                JLabel nameLabel = new JLabel(species + " (Nuovo)");
                nameLabel.setFont(FontManager.getPokemonFont(16));
                pokemonPanel.add(nameLabel, BorderLayout.WEST);
                
                // Rendi il panel cliccabile per aggiungere il Pokémon al team
                pokemonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                pokemonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Crea un nuovo Pokémon di questa specie
                        Pokemon newPokemon = PokemonFactory.createPokemon(pokemonSpecies, 5);
                        
                        // Aggiungi il Pokémon al team del giocatore
                        if (GameState.getInstance().addPokemonToTeam(newPokemon)) {
                            // Aggiorna la battaglia con il nuovo team
                            List<Pokemon> updatedTeam = GameState.getInstance().getPlayerTeam();
                            int newPokemonIndex = updatedTeam.size() - 1;
                            
                            // Cambia al nuovo Pokémon
                            battle.executePlayerSwitch(newPokemonIndex);
                            
                            // Torna alla schermata di battaglia
                            cardLayout.show(mainPanel, SCREEN_BATTLE);
                            
                            // Aggiorna la schermata di battaglia
                            battleScreen.updateAfterSwitch();
                        } else {
                            // Mostra un messaggio se il team è pieno
                            JOptionPane.showMessageDialog(MainScreen.this, 
                                "Il tuo team è già pieno! Rimuovi un Pokémon prima di aggiungerne uno nuovo.", 
                                "Team Pieno", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                
                pokemonListPanel.add(pokemonPanel);
                pokemonListPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        switchScreen.add(scrollPane, BorderLayout.CENTER);
        
        // Pulsante per tornare alla battaglia
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton backButton = new JButton("Indietro");
        backButton.setFont(FontManager.getPokemonFont(16));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, SCREEN_BATTLE);
            }
        });
        
        buttonPanel.add(backButton);
        
        switchScreen.add(buttonPanel, BorderLayout.SOUTH);
        
        // Aggiunge la schermata di cambio Pokémon al panel principale
        mainPanel.add(switchScreen, SCREEN_SWITCH);
        
        // Mostra la schermata di cambio Pokémon
        cardLayout.show(mainPanel, SCREEN_SWITCH);
    }
    
    /**
     * Mostra la schermata di vittoria
     */
    public void showVictoryScreen() {
        cardLayout.show(mainPanel, SCREEN_VICTORY);
    }
    
    /**
     * Mostra la schermata di game over
     * 
     * @param checkLeaderboard true se deve controllare se il punteggio entra in classifica
     */
    public void showGameOverScreen(boolean checkLeaderboard) {
        if (checkLeaderboard && GameState.getInstance().getConsecutiveWins() > 0) {
            int score = GameState.getInstance().getConsecutiveWins();
            // Ottieni la leaderboard attuale
            java.util.Map<String, Integer> leaderboard = GameState.getInstance().getLeaderboard();
            // Crea una lista ordinata dei punteggi
            java.util.List<Integer> scores = new java.util.ArrayList<>(leaderboard.values());
            scores.sort(java.util.Collections.reverseOrder());

            boolean isNewRecord = scores.size() < 10 || score > scores.get(scores.size() - 1);

            if (isNewRecord) {
                String playerName = JOptionPane.showInputDialog(this,
                        "Hai ottenuto " + score + " vittorie consecutive!\n" +
                        "Inserisci il tuo nome per la classifica:",
                        "Nuovo Record",
                        JOptionPane.PLAIN_MESSAGE);

                if (playerName != null && !playerName.trim().isEmpty()) {
                    GameState.getInstance().addToLeaderboard(playerName, score);
                }
            }
        }
        cardLayout.show(mainPanel, SCREEN_GAME_OVER);
    }
}