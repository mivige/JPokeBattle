package it.pokebattle.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.pokebattle.battle.Battle;
import it.pokebattle.model.Pokemon;
import it.pokebattle.model.PokemonFactory;

/**
 * Classe che gestisce lo stato del gioco, inclusi i Pokémon del giocatore,
 * i progressi e il salvataggio dei dati.
 * Implementa il pattern Singleton per garantire un'unica istanza.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SAVE_FILE = "pokebattle_save.dat"; 
    
    private static GameState instance;
    
    private List<Pokemon> playerTeam; // Squadra del giocatore
    private List<String> unlockedSpecies; // Specie di Pokémon sbloccate
    private int consecutiveWins; // Numero di vittorie consecutive
    private int highestConsecutiveWins; // Record di vittorie consecutive
    private Map<String, Integer> leaderboard; // Classifica dei migliori giocatori
    private List<String> battleLog; // Log delle mosse usate durante la battaglia
    private int activePokemonIndex = 0; // Indice del Pokémon attivo
    private transient Battle currentBattle; // Battaglia attuale
    
    /**
     * Costruttore privato (pattern Singleton)
     */
    private GameState() {
        playerTeam = new ArrayList<>();
        unlockedSpecies = new ArrayList<>();
        consecutiveWins = 0;
        highestConsecutiveWins = 0;
        leaderboard = new HashMap<>();
        battleLog = new ArrayList<>();
        
        // Sblocca i Pokémon iniziali
        unlockedSpecies.add("Bulbasaur");
        unlockedSpecies.add("Charmander");
        unlockedSpecies.add("Squirtle");
    }
    
    /**
     * Ottiene l'istanza del GameState (pattern Singleton)
     * 
     * @return Istanza del GameState
     */
    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = loadGame();
            if (instance == null) {
                instance = new GameState();
            }
        }
        return instance;
    }
    
    /**
     * Inizializza una nuova partita
     * 
     * @param starterSpecies Specie del Pokémon iniziale scelto
     */
    public void startNewGame(String starterSpecies) {
        // Salva la lista dei Pokémon sbloccati prima di resettare
        List<Pokemon> oldTeam = new ArrayList<>(playerTeam);
        int oldActiveIndex = activePokemonIndex; // Salva l'indice attivo precedente

        // Resetta la squadra del giocatore
        playerTeam.clear();

        // Per ogni specie sbloccata, ripristina livello ed esperienza se già presenti
        for (String species : unlockedSpecies) {
            Pokemon found = null;
            for (Pokemon p : oldTeam) {
                if (p.getSpecies().equals(species)) {
                    found = p;
                    break;
                }
            }
            Pokemon poke;
            if (found != null) {
                // Ricrea il Pokémon con livello ed esperienza mantenuti, HP massimi e stato sano
                poke = PokemonFactory.createPokemon(species, found.getLevel());
                poke.setExperience(found.getExperience());
                poke.setCurrentHp(poke.getMaxHp());
                poke.setFainted(false);
            } else {
                // Nuovo Pokémon base
                poke = PokemonFactory.createPokemon(species, 5);
            }
            playerTeam.add(poke);
        }

        // Resetta le statistiche
        consecutiveWins = 0;

        // Assicurati che i Pokémon iniziali siano sempre sbloccati
        if (!unlockedSpecies.contains("Bulbasaur")) unlockedSpecies.add("Bulbasaur");
        if (!unlockedSpecies.contains("Charmander")) unlockedSpecies.add("Charmander");
        if (!unlockedSpecies.contains("Squirtle")) unlockedSpecies.add("Squirtle");

        // Se si tratta di una nuova partita (starter scelto), metti lo starter in prima posizione e attivo
        int starterIndex = -1;
        for (int i = 0; i < playerTeam.size(); i++) {
            if (playerTeam.get(i).getSpecies().equals(starterSpecies)) {
                starterIndex = i;
                break;
            }
        }
        if (starterIndex > 0) {
            Pokemon starter = playerTeam.remove(starterIndex);
            playerTeam.add(0, starter);
            activePokemonIndex = 0;
        } else if (starterIndex == 0) {
            activePokemonIndex = 0;
        } else {
            // Se non trovato, fallback
            activePokemonIndex = 0;
        }

        // Salva il gioco
        saveGame();
    }

    /**
     * Aggiorna la squadra e l'indice attivo dopo una vittoria, mantenendo l'ordine e il Pokémon attivo
     * 
     * @param newTeam nuova lista di Pokémon (ordine aggiornato)
     * @param newActiveIndex indice del Pokémon attivo dopo la battaglia
     */
    public void updateTeamAfterBattle(List<Pokemon> newTeam, int newActiveIndex) {
        this.playerTeam = new ArrayList<>(newTeam);
        setActivePokemonIndex(newActiveIndex);
        saveGame();
    }
    
    /**
     * Aggiunge un Pokémon alla squadra del giocatore
     * 
     * @param pokemon Pokémon da aggiungere
     * @return true se il Pokémon è stato aggiunto, false se la squadra è piena
     */
    public boolean addPokemonToTeam(Pokemon pokemon) {
        playerTeam.add(pokemon);
        saveGame();
        return true;
    }
    
    /**
     * Sblocca una nuova specie di Pokémon
     * 
     * @param species Specie da sbloccare
     */
    public void unlockSpecies(String species) {
        if (!unlockedSpecies.contains(species)) {
            unlockedSpecies.add(species);
            saveGame();
        }
    }
    
    /**
     * Salva il risultato di una battaglia
     * 
     * @param won true se la battaglia è stata vinta, false altrimenti
     */
    public void saveBattleResult(boolean won) {
        if (won) {
            consecutiveWins++;
            if (consecutiveWins > highestConsecutiveWins) {
                highestConsecutiveWins = consecutiveWins;
            }
        } else {
            consecutiveWins = 0;
        }
        saveGame();
    }
    
    /**
     * Aggiunge un punteggio alla leaderboard
     * 
     * @param playerName Nome del giocatore
     * @param score Punteggio (numero di vittorie consecutive)
     */
    public void addToLeaderboard(String playerName, int score) {
        leaderboard.put(playerName, score);
        saveGame();
    }
    
    /**
     * Ottiene la leaderboard ordinata per punteggio decrescente
     * 
     * @return Mappa ordinata con i migliori 10 punteggi
     */
    public Map<String, Integer> getLeaderboard() {
        return leaderboard;
    }
    
    /**
     * Salva lo stato del gioco su file
     */
    public void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del gioco: " + e.getMessage());
        }
    }
    
    /**
     * Carica lo stato del gioco da file
     * 
     * @return Stato del gioco caricato o null se non esiste
     */
    private static GameState loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Nessun salvataggio trovato o errore nel caricamento: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Aggiunge una voce al log della battaglia
     * 
     * @param logEntry Voce da aggiungere al log
     */
    public void addBattleLogEntry(String logEntry) {
        if (battleLog == null) {
            battleLog = new ArrayList<>();
        }
        battleLog.add(logEntry);
    }
    
    /**
     * Pulisce il log della battaglia
     */
    public void clearBattleLog() {
        if (battleLog == null) {
            battleLog = new ArrayList<>();
        } else {
            battleLog.clear();
        }
    }
    
    /**
     * Sblocca una nuova specie di Pokémon dopo averla sconfitta
     * 
     * @param defeatedPokemon Pokémon sconfitto
     */
    public void unlockDefeatedPokemon(Pokemon defeatedPokemon) {
        String species = defeatedPokemon.getSpecies();
        if (!unlockedSpecies.contains(species)) {
            unlockedSpecies.add(species);
            saveGame();
        }
    }
    
    /**
     * Resetta l'istanza del GameState (per il testing)
     */
    public static synchronized void resetInstance() {
        instance = null;
    }
    
    // Getters
    
    public List<Pokemon> getPlayerTeam() {
        return new ArrayList<>(playerTeam);
    }
    
    public List<String> getUnlockedSpecies() {
        return new ArrayList<>(unlockedSpecies);
    }
    
    public int getConsecutiveWins() {
        return consecutiveWins;
    }
    
    public int getHighestConsecutiveWins() {
        return highestConsecutiveWins;
    }
    
    public List<String> getBattleLog() {
        return new ArrayList<>(battleLog);
    }
    
    public int getActivePokemonIndex() {
        return activePokemonIndex;
    }

    public void setActivePokemonIndex(int index) {
        if (index >= 0 && index < playerTeam.size()) {
            activePokemonIndex = index;
        }
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(Battle battle) {
        this.currentBattle = battle;
    }
}