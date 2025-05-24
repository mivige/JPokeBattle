package it.pokebattle.battle;

import it.pokebattle.model.Move;
import it.pokebattle.model.Pokemon;
import java.util.List;

/**
 * Interfaccia per ascoltare gli eventi durante una battaglia Pokémon.
 * Permette di reagire a vari eventi come l'uso di mosse, danni inflitti, cambi di Pokémon, ecc.
 */
public interface BattleListener {
    /**
     * Chiamato quando un Pokémon usa una mossa
     * 
     * @param pokemon Pokémon che usa la mossa
     * @param move Mossa utilizzata
     * @param isPlayer true se il Pokémon è del giocatore, false se è dell'avversario
     */
    void onMoveUsed(Pokemon pokemon, Move move, boolean isPlayer);
    
    /**
     * Chiamato quando una mossa fallisce
     * 
     * @param pokemon Pokémon che ha tentato di usare la mossa
     * @param move Mossa che è fallita
     * @param reason Motivo del fallimento
     * @param isPlayer true se il Pokémon è del giocatore, false se è dell'avversario
     */
    void onMoveFailed(Pokemon pokemon, Move move, String reason, boolean isPlayer);
    
    /**
     * Chiamato quando una mossa manca il bersaglio
     * 
     * @param attacker Pokémon attaccante
     * @param defender Pokémon difensore
     * @param move Mossa utilizzata
     * @param isPlayer true se l'attaccante è del giocatore, false se è dell'avversario
     */
    void onMoveMissed(Pokemon attacker, Pokemon defender, Move move, boolean isPlayer);
    
    /**
     * Chiamato quando viene inflitto danno
     * 
     * @param attacker Pokémon attaccante
     * @param defender Pokémon difensore
     * @param move Mossa utilizzata
     * @param damage Danno inflitto
     * @param effectiveness Codice di efficacia (da Battle.RESULT_*)
     * @param critical true se è un colpo critico
     * @param fainted true se il difensore è stato messo KO
     * @param isPlayer true se l'attaccante è del giocatore, false se è dell'avversario
     */
    void onDamageDealt(Pokemon attacker, Pokemon defender, Move move, int damage, 
                      int effectiveness, boolean critical, boolean fainted, boolean isPlayer);
    
    /**
     * Chiamato quando un Pokémon viene messo KO
     * 
     * @param pokemon Pokémon messo KO
     * @param isPlayerPokemon true se il Pokémon è del giocatore, false se è dell'avversario
     */
    void onPokemonFainted(Pokemon pokemon, boolean isPlayerPokemon);
    
    /**
     * Chiamato quando un giocatore cambia Pokémon
     * 
     * @param oldPokemon Pokémon ritirato
     * @param newPokemon Pokémon mandato in campo
     * @param isPlayer true se è il giocatore a cambiare, false se è l'avversario
     */
    void onPokemonSwitched(Pokemon oldPokemon, Pokemon newPokemon, boolean isPlayer);
    
    /**
     * Chiamato quando un Pokémon guadagna esperienza
     * 
     * @param pokemon Pokémon che guadagna esperienza
     * @param expGained Quantità di esperienza guadagnata
     */
    void onExperienceGained(Pokemon pokemon, int expGained);
    
    /**
     * Chiamato quando un Pokémon sale di livello
     * 
     * @param pokemon Pokémon che è salito di livello
     * @param newLevel Nuovo livello raggiunto
     */
    void onLevelUp(Pokemon pokemon, int newLevel);
    
    /**
     * Chiamato quando un Pokémon evolve
     * 
     * @param oldPokemon Pokémon prima dell'evoluzione
     * @param evolvedPokemon Pokémon dopo l'evoluzione
     */
    void onPokemonEvolved(Pokemon oldPokemon, Pokemon evolvedPokemon);
    
    /**
     * Chiamato quando inizia una battaglia
     * 
     * @param playerTeam Squadra del giocatore
     * @param enemyTeam Squadra dell'avversario
     */
    void onBattleStart(List<Pokemon> playerTeam, List<Pokemon> enemyTeam);
    
    /**
     * Chiamato quando termina una battaglia
     * 
     * @param playerWon true se il giocatore ha vinto, false se ha perso
     */
    void onBattleEnd(boolean playerWon);
}