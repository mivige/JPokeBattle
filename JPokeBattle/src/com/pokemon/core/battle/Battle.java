package com.pokemon.core.battle;

import com.pokemon.core.pokemon.Pokemon;
import com.pokemon.core.moves.Move;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Battle {
    private Pokemon player1Pokemon;
    private Pokemon player2Pokemon;
    private boolean isPlayerTurn;
    private Random random = new Random();
    private Consumer<String> battleLogger;
    
    public Battle(Pokemon p1, Pokemon p2) {
        this.player1Pokemon = p1;
        this.player2Pokemon = p2;
        //this.isPlayerTurn = determineFirstTurn();
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
}