package com.pokemon.core.pokemon;

import com.pokemon.core.stats.Stats;
import com.pokemon.core.moves.Tackle;
import com.pokemon.core.moves.TailWhip;

public class Michelangelo extends Pokemon {
    private static final Stats BASE_STATS = new Stats(44, 48, 65, 50, 64, 43);
    
    public Michelangelo(int level) {
        super("Michelangelo", level, BASE_STATS, PokemonType.WATER);
    }
    
    @Override
    protected void initializeDefaultMoves() {
        addMove(new Tackle());
        addMove(new TailWhip());
    }
} 