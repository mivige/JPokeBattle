package com.pokemon.core.pokemon;

import com.pokemon.core.stats.Stats;
import com.pokemon.core.moves.Tackle;
import com.pokemon.core.moves.Growl;

public class Bulbo extends Pokemon {
    private static final Stats BASE_STATS = new Stats(45, 49, 49, 65, 65, 45);
    
    public Bulbo(int level) {
        super("Bulbo", level, BASE_STATS, PokemonType.GRASS);
    }
    
    @Override
    protected void initializeDefaultMoves() {
        addMove(new Tackle());
        addMove(new Growl());
    }
}