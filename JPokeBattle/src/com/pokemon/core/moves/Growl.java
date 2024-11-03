package com.pokemon.core.moves;

import com.pokemon.core.pokemon.Pokemon;

public class Growl implements Move {
    @Override
    public String getName() {
        return "Growl";
    }

    @Override
    public int getPower() {
        return 0;
    }

    @Override
    public int getAccuracy() {
        return 100;
    }

    @Override
    public void execute(Pokemon user, Pokemon target) {
        target.reduceAttack();
    }
} 