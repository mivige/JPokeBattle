package it.pokebattle.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.pokebattle.model.Move;
import it.pokebattle.model.Pokemon;
import it.pokebattle.model.PokemonType;
import it.pokebattle.game.GameState;

/**
 * Classe che gestisce la logica di combattimento tra Pokémon.
 * Implementa le regole di combattimento della prima generazione di Pokémon.
 */
public class Battle {
    // Costanti per i risultati di efficacia delle mosse
    public static final int RESULT_NORMAL = 0; // Efficacia normale
    public static final int RESULT_SUPER_EFFECTIVE = 1; // Superefficace
    public static final int RESULT_NOT_VERY_EFFECTIVE = 2; // Poco efficace
    public static final int RESULT_NO_EFFECT = 3; // Nessun effetto
    
    private Pokemon playerPokemon; // Pokémon attualmente in campo del giocatore
    private Pokemon enemyPokemon; // Pokémon attualmente in campo dell'avversario
    private List<Pokemon> playerTeam; // Squadra completa del giocatore
    private List<Pokemon> enemyTeam; // Squadra completa dell'avversario
    private List<BattleListener> listeners; // Ascoltatori degli eventi di battaglia
    private Random random; // Generatore di numeri casuali
    private boolean battleEnded; // Flag che indica se la battaglia è terminata
    private boolean playerWon; // Flag che indica se il giocatore ha vinto
    
    /**
     * Costruttore per una battaglia tra due squadre di Pokémon
     * 
     * @param playerTeam Squadra del giocatore
     * @param enemyTeam Squadra dell'avversario
     */
    public Battle(List<Pokemon> playerTeam, List<Pokemon> enemyTeam) {
        if (playerTeam == null || playerTeam.isEmpty() || enemyTeam == null || enemyTeam.isEmpty()) {
            throw new IllegalArgumentException("Le squadre non possono essere vuote");
        }
        
        this.playerTeam = new ArrayList<>(playerTeam);
        this.enemyTeam = new ArrayList<>(enemyTeam);
        this.playerPokemon = this.playerTeam.get(0); // Il primo Pokémon della squadra va in campo
        this.enemyPokemon = this.enemyTeam.get(0); // Il primo Pokémon della squadra avversaria va in campo
        this.listeners = new ArrayList<>();
        this.random = new Random();
        this.battleEnded = false;
        this.playerWon = false;
    }
    
    /**
     * Aggiunge un ascoltatore degli eventi di battaglia
     * 
     * @param listener Ascoltatore da aggiungere
     */
    public void addBattleListener(BattleListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }
    
    /**
     * Rimuove un ascoltatore degli eventi di battaglia
     * 
     * @param listener Ascoltatore da rimuovere
     */
    public void removeBattleListener(BattleListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Esegue un turno di battaglia in cui il giocatore usa una mossa
     * 
     * @param moveIndex Indice della mossa da utilizzare
     * @return true se la battaglia continua, false se è terminata
     */
    public boolean executePlayerMove(int moveIndex) {
        if (battleEnded) {
            return false;
        }
        
        // Controlla se l'indice della mossa è valido
        if (moveIndex < 0 || moveIndex >= playerPokemon.getMoves().size()) {
            return true; // Mossa non valida, il turno viene saltato
        }
        
        Move playerMove = playerPokemon.getMoves().get(moveIndex);
        
        // Scelta casuale della mossa dell'avversario
        List<Move> enemyMoves = enemyPokemon.getMoves();
        Move enemyMove = enemyMoves.get(random.nextInt(enemyMoves.size()));
        
        // Determina chi attacca per primo in base alla velocità e alla priorità delle mosse
        boolean playerFirst = determineFirstAttacker(playerPokemon, enemyPokemon, playerMove, enemyMove);
        
        if (playerFirst) {
            // Il giocatore attacca per primo
            executeMove(playerPokemon, enemyPokemon, playerMove, true);
            
            // Se l'avversario è ancora in grado di combattere, attacca
            if (!enemyPokemon.isKO() && !battleEnded) {
                executeMove(enemyPokemon, playerPokemon, enemyMove, false);
            }
        } else {
            // L'avversario attacca per primo
            executeMove(enemyPokemon, playerPokemon, enemyMove, false);
            
            // Se il giocatore è ancora in grado di combattere, attacca
            if (!playerPokemon.isKO() && !battleEnded) {
                executeMove(playerPokemon, enemyPokemon, playerMove, true);
            }
        }
        
        // Controlla se la battaglia è terminata
        checkBattleEnd();
        
        return !battleEnded;
    }
    
    /**
     * Esegue un turno di battaglia in cui il giocatore cambia Pokémon
     * 
     * @param newPokemonIndex Indice del nuovo Pokémon da mandare in campo
     * @return true se la battaglia continua, false se è terminata
     */
    public boolean executePlayerSwitch(int newPokemonIndex) {
        if (battleEnded) {
            return false;
        }
        
        // Controlla se l'indice del Pokémon è valido
        if (newPokemonIndex < 0 || newPokemonIndex >= playerTeam.size()) {
            return true; // Cambio non valido, il turno viene saltato
        }
        
        // Controlla se il Pokémon selezionato è già in campo
        if (playerTeam.get(newPokemonIndex) == playerPokemon) {
            return true; // Il Pokémon è già in campo, il turno viene saltato
        }
        
        // Controlla se il Pokémon selezionato è KO
        if (playerTeam.get(newPokemonIndex).isKO()) {
            return true; // Il Pokémon è KO, il turno viene saltato
        }
        
        // Salva il vecchio Pokémon per notificare il cambio
        Pokemon oldPokemon = playerPokemon;
        
        // Cambia il Pokémon del giocatore
        playerPokemon = playerTeam.get(newPokemonIndex);
        
        // Notifica il cambio di Pokémon
        for (BattleListener listener : listeners) {
            listener.onPokemonSwitched(oldPokemon, playerPokemon, true);
        }
        
        // L'avversario attacca dopo il cambio solo se il cambio non è dovuto a un KO
        if (!oldPokemon.isKO()) {
            List<Move> enemyMoves = enemyPokemon.getMoves();
            Move enemyMove = enemyMoves.get(random.nextInt(enemyMoves.size()));
            executeMove(enemyPokemon, playerPokemon, enemyMove, false);
        }
        
        // Controlla se la battaglia è terminata
        checkBattleEnd();
        
        return !battleEnded;
    }
    
    /**
     * Determina quale Pokémon attacca per primo in base alla velocità e alla priorità delle mosse
     * 
     * @param pokemon1 Primo Pokémon
     * @param pokemon2 Secondo Pokémon
     * @param move1 Mossa del primo Pokémon
     * @param move2 Mossa del secondo Pokémon
     * @return true se il primo Pokémon attacca per primo, false altrimenti
     */
    private boolean determineFirstAttacker(Pokemon pokemon1, Pokemon pokemon2, Move move1, Move move2) {
        // Controlla la priorità delle mosse
        if (move1.getPriority() > move2.getPriority()) {
            return true;
        } else if (move1.getPriority() < move2.getPriority()) {
            return false;
        }
        
        // Se la priorità è uguale, controlla la velocità
        if (pokemon1.getSpeed() > pokemon2.getSpeed()) {
            return true;
        } else if (pokemon1.getSpeed() < pokemon2.getSpeed()) {
            return false;
        }
        
        // Se anche la velocità è uguale, scelta casuale
        return random.nextBoolean();
    }
    
    /**
     * Esegue una mossa di un Pokémon contro un altro
     * 
     * @param attacker Pokémon attaccante
     * @param defender Pokémon difensore
     * @param move Mossa utilizzata
     * @param isPlayer true se l'attaccante è del giocatore, false se è dell'avversario
     */
    private void executeMove(Pokemon attacker, Pokemon defender, Move move, boolean isPlayer) {
        // Notifica l'uso della mossa
        for (BattleListener listener : listeners) {
            listener.onMoveUsed(attacker, move, isPlayer);
        }
        
        // Controlla se la mossa ha PP sufficienti
        if (!move.use()) {
            // Notifica il fallimento della mossa per PP esauriti
            for (BattleListener listener : listeners) {
                listener.onMoveFailed(attacker, move, "PP esauriti", isPlayer);
            }
            return;
        }
        
        // Controlla se la mossa colpisce in base alla precisione
        if (!move.hits()) {
            // Notifica il fallimento della mossa per mancato bersaglio
            for (BattleListener listener : listeners) {
                listener.onMoveMissed(attacker, defender, move, isPlayer);
            }
            return;
        }
        
        // Se è una mossa di danno, calcola e applica il danno
        if (move.getPower() > 0) {
            // Calcola il danno
            int damage = calculateDamage(attacker, defender, move);
            
            // Calcola l'efficacia della mossa
            double typeEffectiveness = PokemonType.getTypeEffectiveness(
                move.getType(), defender.getPrimaryType(), defender.getSecondaryType());
            
            int effectivenessResult;
            if (typeEffectiveness == 0) {
                effectivenessResult = RESULT_NO_EFFECT;
                damage = 0; // Nessun danno se la mossa non ha effetto
            } else if (typeEffectiveness < 1) {
                effectivenessResult = RESULT_NOT_VERY_EFFECTIVE;
            } else if (typeEffectiveness > 1) {
                effectivenessResult = RESULT_SUPER_EFFECTIVE;
            } else {
                effectivenessResult = RESULT_NORMAL;
            }
            
            // Determina se è un colpo critico (probabilità 1/16 nella prima generazione)
            boolean critical = random.nextInt(16) == 0;
            if (critical) {
                damage = (int) (damage * 1.5); // I colpi critici infliggono 1.5x danno
            }
            
            // Applica il danno al difensore
            boolean fainted = !defender.takeDamage(damage);
            
            // Notifica il danno inflitto
            for (BattleListener listener : listeners) {
                listener.onDamageDealt(attacker, defender, move, damage, 
                                      effectivenessResult, critical, fainted, isPlayer);
            }
            
            // Se il difensore è KO, gestisci la situazione
            if (fainted) {
                handleFaintedPokemon(defender, isPlayer);
            }
        }
        
        // Gestisci gli effetti della mossa (per ora solo mosse di danno)
    }
    
    /**
     * Calcola il danno inflitto da una mossa
     * Utilizza la formula della prima generazione di Pokémon
     * 
     * @param attacker Pokémon attaccante
     * @param defender Pokémon difensore
     * @param move Mossa utilizzata
     * @return Danno calcolato
     */
    private int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Formula del danno della prima generazione:
        // ((2 * Level / 5 + 2) * Power * A / D / 50 + 2) * Modifier
        
        int level = attacker.getLevel();
        int power = move.getPower();
        int attack, defense;
        
        // Determina quali statistiche usare in base alla categoria della mossa
        switch (move.getCategory()) {
            case PHYSICAL:
                attack = attacker.getAttack();
                defense = defender.getDefense();
                break;
            case SPECIAL:
                attack = attacker.getSpecial();
                defense = defender.getSpecial();
                break;
            default:
                return 0; // Le mosse di stato non infliggono danno diretto
        }
        
        // Calcola il danno base
        double baseDamage = ((2.0 * level / 5.0 + 2.0) * power * attack / defense / 50.0 + 2.0);
        
        // Calcola il modificatore di tipo (STAB - Same Type Attack Bonus)
        double stab = 1.0;
        if (move.getType() == attacker.getPrimaryType() || 
            (attacker.getSecondaryType() != null && move.getType() == attacker.getSecondaryType())) {
            stab = 1.5; // Bonus del 50% se la mossa è dello stesso tipo del Pokémon
        }
        
        // Calcola l'efficacia del tipo
        double typeEffectiveness = PokemonType.getTypeEffectiveness(
            move.getType(), defender.getPrimaryType(), defender.getSecondaryType());
        
        // Applica una variazione casuale (85-100% nella prima generazione)
        double randomFactor = 0.85 + (random.nextDouble() * 0.15);
        
        // Calcola il danno finale
        int finalDamage = (int) (baseDamage * stab * typeEffectiveness * randomFactor);
        
        // Assicura che il danno sia almeno 1 (a meno che la mossa non abbia effetto)
        return (finalDamage > 0 && typeEffectiveness > 0) ? Math.max(1, finalDamage) : 0;
    }
    
    /**
     * Gestisce la situazione in cui un Pokémon è stato messo KO
     * 
     * @param faintedPokemon Pokémon messo KO
     * @param playerAttacked true se l'attaccante era del giocatore, false se era dell'avversario
     */
    private void handleFaintedPokemon(Pokemon faintedPokemon, boolean playerAttacked) {
        // Notifica che il Pokémon è stato messo KO
        for (BattleListener listener : listeners) {
            listener.onPokemonFainted(faintedPokemon, faintedPokemon == playerPokemon);
        }
        
        // Se il Pokémon KO è del giocatore
        if (faintedPokemon == playerPokemon) {
            // Cerca un altro Pokémon nella squadra del giocatore che possa combattere
            boolean hasValidPokemon = false;
            for (Pokemon pokemon : playerTeam) {
                if (!pokemon.isKO()) {
                    hasValidPokemon = true;
                    break;
                }
            }
            
            // Se non ci sono più Pokémon validi, la battaglia è terminata
            if (!hasValidPokemon) {
                battleEnded = true;
                playerWon = false;
            }
            // Altrimenti, il giocatore deve scegliere un nuovo Pokémon
            // La scelta verrà gestita dall'interfaccia utente
        }
        // Se il Pokémon KO è dell'avversario
        else if (faintedPokemon == enemyPokemon) {
            // Assegna esperienza al Pokémon del giocatore
            int expGained = calculateExperienceGain(enemyPokemon);
            playerPokemon.addExperience(expGained);

            // Sblocca la specie del Pokémon sconfitto per le partite future (1 possibilità su 3)
            if (random.nextInt(3) == 1) {
                GameState.getInstance().unlockDefeatedPokemon(faintedPokemon);
            }
            
            // Cerca un altro Pokémon nella squadra dell'avversario che possa combattere
            boolean hasValidPokemon = false;
            for (int i = 0; i < enemyTeam.size(); i++) {
                Pokemon pokemon = enemyTeam.get(i);
                if (!pokemon.isKO()) {
                    Pokemon oldPokemon = enemyPokemon;
                    enemyPokemon = pokemon;
                    hasValidPokemon = true;
                    
                    // Notifica il cambio di Pokémon dell'avversario
                    for (BattleListener listener : listeners) {
                        listener.onPokemonSwitched(oldPokemon, enemyPokemon, false);
                    }
                    
                    break;
                }
            }
            
            // Se non ci sono più Pokémon validi, la battaglia è terminata
            if (!hasValidPokemon) {
                battleEnded = true;
                playerWon = true;
            }
        }
    }
    
    /**
     * Calcola l'esperienza guadagnata sconfiggendo un Pokémon
     * 
     * @param defeatedPokemon Pokémon sconfitto
     * @return Esperienza guadagnata
     */
    private int calculateExperienceGain(Pokemon defeatedPokemon) {
        // Formula semplificata: (livello del Pokémon sconfitto) * 3
        return defeatedPokemon.getLevel() * 30;
    }
    
    /**
     * Controlla se la battaglia è terminata
     */
    private void checkBattleEnd() {
        // Controlla se tutti i Pokémon del giocatore sono KO
        boolean allPlayerPokemonFainted = true;
        for (Pokemon pokemon : playerTeam) {
            if (!pokemon.isKO()) {
                allPlayerPokemonFainted = false;
                break;
            }
        }
        
        // Controlla se tutti i Pokémon dell'avversario sono KO
        boolean allEnemyPokemonFainted = true;
        for (Pokemon pokemon : enemyTeam) {
            if (!pokemon.isKO()) {
                allEnemyPokemonFainted = false;
                break;
            }
        }
        
        // Determina il risultato della battaglia
        if (allPlayerPokemonFainted) {
            battleEnded = true;
            playerWon = false;
        } else if (allEnemyPokemonFainted) {
            battleEnded = true;
            playerWon = true;
        }
    }
    
    /**
     * Verifica se la battaglia è terminata
     * 
     * @return true se la battaglia è terminata, false altrimenti
     */
    public boolean isBattleEnded() {
        return battleEnded;
    }
    
    /**
     * Verifica se il giocatore ha vinto la battaglia
     * 
     * @return true se il giocatore ha vinto, false altrimenti
     */
    public boolean hasPlayerWon() {
        return playerWon;
    }
    
    /**
     * Ottiene il Pokémon attualmente in campo del giocatore
     * 
     * @return Pokémon del giocatore
     */
    public Pokemon getPlayerPokemon() {
        return playerPokemon;
    }
    
    /**
     * Ottiene il Pokémon attualmente in campo dell'avversario
     * 
     * @return Pokémon dell'avversario
     */
    public Pokemon getEnemyPokemon() {
        return enemyPokemon;
    }
    
    /**
     * Ottiene la squadra completa del giocatore
     * 
     * @return Squadra del giocatore
     */
    public List<Pokemon> getPlayerTeam() {
        return new ArrayList<>(playerTeam);
    }
    
    /**
     * Ottiene la squadra completa dell'avversario
     * 
     * @return Squadra dell'avversario
     */
    public List<Pokemon> getEnemyTeam() {
        return new ArrayList<>(enemyTeam);
    }
    
    public void setPlayerActivePokemon(int index) {
        if (index >= 0 && index < playerTeam.size() && !playerTeam.get(index).isKO()) {
            this.playerPokemon = playerTeam.get(index);
        }
    }

    // Metodo per ottenere la lista dei listener della battaglia
    public List<BattleListener> getListeners() {
        return listeners;
    }
}