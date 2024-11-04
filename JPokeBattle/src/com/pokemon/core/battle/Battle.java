package com.pokemon.core.battle;

import com.pokemon.core.pokemon.Pokemon;
import com.pokemon.core.moves.Move;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Battle {
    private Pokemon player1Pokemon;
    private Pokemon player2Pokemon;
    private boolean isPlayerTurn;
    private Random random = new Random();
    private Consumer<String> battleLogger;
    
    // Add Pokemon storage
    private Map<String, Pokemon> playerPokemonStorage;
    
    public Battle(Pokemon p1, Pokemon p2) {
        this.player1Pokemon = p1;
        this.player2Pokemon = p2;
        this.isPlayerTurn = random.nextBoolean(); // Randomly determine the first turn
        this.playerPokemonStorage = new HashMap<>();
        storePokemon(p1); // Store initial Pokemon
    }
    public void setBattleLogger(Consumer<String> logger) {
        this.battleLogger = logger;
    }
    
    private void logBattle(String message) {
        if (battleLogger != null) {
            battleLogger.accept(message);
        }
    }
    
    public void executeMove(Move playerMove) {
        Move enemyMove = selectEnemyMove();
        
        // Queue both moves based on priority
        if (isStatusMove(playerMove) || (!isStatusMove(enemyMove) && isPlayerTurn)) {
            logBattle(player1Pokemon.getName() + " uses " + playerMove.getName() + "!");
            playerMove.execute(player1Pokemon, player2Pokemon);
            
            if (player2Pokemon.getCurrentHP() > 0) {
                logBattle(player2Pokemon.getName() + " uses " + enemyMove.getName() + "!");
                enemyMove.execute(player2Pokemon, player1Pokemon);
            }
        } else {
            logBattle(player2Pokemon.getName() + " uses " + enemyMove.getName() + "!");
            enemyMove.execute(player2Pokemon, player1Pokemon);
            
            if (player1Pokemon.getCurrentHP() > 0) {
                logBattle(player1Pokemon.getName() + " uses " + playerMove.getName() + "!");
                playerMove.execute(player1Pokemon, player2Pokemon);
            }
        }
    }
    
    private boolean isStatusMove(Move move) {
        return move.getPower() == 0;
    }
    
    private Move selectEnemyMove() {
        List<Move> enemyMoves = player2Pokemon.getMoves();
        return enemyMoves.get(random.nextInt(enemyMoves.size()));
    }
    
    public boolean isBattleOver() {
        return player1Pokemon.getCurrentHP() <= 0 || player2Pokemon.getCurrentHP() <= 0;
    }
    
    public void switchPlayerPokemon(Pokemon newPokemon) {
        // Store current Pokemon state
        storePokemon(player1Pokemon);
        
        // Check if we already have this Pokemon stored
        String pokemonKey = getPokemonKey(newPokemon);
        if (playerPokemonStorage.containsKey(pokemonKey)) {
            // Use the stored Pokemon state
            player1Pokemon = playerPokemonStorage.get(pokemonKey);
        } else {
            // Store and use the new Pokemon
            player1Pokemon = newPokemon;
            storePokemon(newPokemon);
        }
    }
    
    private void storePokemon(Pokemon pokemon) {
        playerPokemonStorage.put(getPokemonKey(pokemon), pokemon);
    }
    
    private String getPokemonKey(Pokemon pokemon) {
        return pokemon.getName() + "_" + pokemon.getLevel();
    }
    
    // Add getter for storage to check available Pokemon
    public List<Pokemon> getAvailablePokemon() {
        return new ArrayList<>(playerPokemonStorage.values());
    }
    
    public void executeEnemyTurn() {
        Move enemyMove = selectEnemyMove();
        logBattle(player2Pokemon.getName() + " uses " + enemyMove.getName() + "!");
        enemyMove.execute(player2Pokemon, player1Pokemon);
    }
    
    // Getters
    public Pokemon getPlayer1Pokemon() { 
    	return player1Pokemon; 
    }
    public Pokemon getPlayer2Pokemon() { 
    	return player2Pokemon; 
    }
    public boolean isPlayerTurn() { 
    	return isPlayerTurn; 
    }
    
    public Pokemon getStoredPokemon(Pokemon pokemon) {
        String pokemonKey = getPokemonKey(pokemon);
        return playerPokemonStorage.get(pokemonKey);
    }
}