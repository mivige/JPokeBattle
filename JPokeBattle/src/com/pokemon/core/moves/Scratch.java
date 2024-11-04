package com.pokemon.core.moves;

import com.pokemon.core.pokemon.Pokemon;

public class Scratch implements Move {
    @Override
    public String getName() {
        return "Scratch";
    }

    @Override
    public int getPower() {
        return 40;
    }

    @Override
    public int getAccuracy() {
        return 100;
    }

    @Override
    public void execute(Pokemon user, Pokemon target) {
        int damage = (user.getStats().getAttack() * getPower()) / 
                    (50 * target.getStats().getDefense());
        damage = Math.max(1, damage);
        target.takeDamage(damage);
    }
} 