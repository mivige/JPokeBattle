package com.pokemon.core.pokemon;

import java.util.ArrayList;
import java.util.List;
import com.pokemon.core.stats.Stats;
import com.pokemon.core.moves.Move;

public abstract class Pokemon {
    private String name;
    private int level;
    private Stats stats;
    private List<Move> moves;
    private int currentHP;
    private PokemonType type;
    
    public Pokemon(String name, int level, Stats baseStats, PokemonType type) {
        this.name = name;
        this.level = level;
        this.stats = baseStats;
        this.moves = new ArrayList<>();
        this.currentHP = stats.getHP();
        this.type = type;
        initializeDefaultMoves();
    }
    
    // New abstract method that each Pokemon implementation must define
    protected abstract void initializeDefaultMoves();
    
    // Method to add moves safely
    protected void addMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
        }
    }
    
    // Getters
    public String getName() { 
    	return name; 
    }
    public int getLevel() { 
    	return level; 
    }
    public Stats getStats() { 
    	return stats; 
    }
    public int getCurrentHP() { 
    	return currentHP; 
    }
    public List<Move> getMoves() { 
    	return moves; 
    }
    public PokemonType getType() {
        return type;
    }
    
    public boolean learnMove(Move newMove) {
        if (moves.size() < 4) {
            moves.add(newMove);
            return true;
        }
        return false;
    }

    public boolean replaceMove(Move oldMove, Move newMove) {
        int index = moves.indexOf(oldMove);
        if (index != -1) {
            moves.set(index, newMove);
            return true;
        }
        return false;
    }

    public void takeDamage(int damage) {
        currentHP = Math.max(0, currentHP - damage);
    }

    public void reduceAttack() {
        stats = new Stats(
            stats.getHP(),
            (int)(stats.getAttack() * 0.85), // Reduce attack by 15%
            stats.getDefense(),
            stats.getSpecialAttack(),
            stats.getSpecialDefense(),
            stats.getSpeed()
        );
    }

    public void reduceDefense() {
        stats = new Stats(
            stats.getHP(),
            stats.getAttack(),
            (int)(stats.getDefense() * 0.85), // Reduce defense by 15%
            stats.getSpecialAttack(),
            stats.getSpecialDefense(),
            stats.getSpeed()
        );
    }
}