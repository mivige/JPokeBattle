package com.pokemon.core.moves;

import com.pokemon.core.pokemon.Pokemon;

public interface Move {
    String getName();
    int getPower();
    int getAccuracy();
    void execute(Pokemon user, Pokemon target);
}