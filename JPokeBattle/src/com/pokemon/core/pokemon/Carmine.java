package com.pokemon.core.pokemon;

import com.pokemon.core.stats.Stats;
import com.pokemon.core.moves.Scratch;
import com.pokemon.core.moves.Growl;

public class Carmine extends Pokemon {
    private static final Stats BASE_STATS = new Stats(39, 52, 43, 60, 50, 65);
    
    public Carmine(int level) {
        super("Carmine", level, BASE_STATS, PokemonType.FIRE);
    }
    
    @Override
    protected void initializeDefaultMoves() {
        addMove(new Scratch());
        addMove(new Growl());
    }
} 