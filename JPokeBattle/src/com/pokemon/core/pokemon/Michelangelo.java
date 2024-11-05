package com.pokemon.core.pokemon;

import com.pokemon.core.stats.Stats;
import com.pokemon.core.moves.Tackle;
import com.pokemon.core.moves.TailWhip;

public class Michelangelo extends Pokemon {
    private static final Stats BASE_STATS = new Stats(44, 48, 65, 50, 64, 43);
    
    public Michelangelo(int level) {
        super("Michelangelo", level, BASE_STATS, PokemonType.WATER, 66);
    }
    
    @Override
    protected void initializeDefaultMoves() {
        addMove(new Tackle());
        addMove(new TailWhip());
    }
    
    @Override
    protected int getXPToNextLevel() {
        // Medium slow experience group
        return (int)((6/5) * Math.pow(getLevel(), 3) - 15 * Math.pow(getLevel(), 2) + (100 * getLevel()) - 140);
    }
} 