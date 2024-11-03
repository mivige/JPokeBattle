package com.pokemon.core.moves;

import com.pokemon.core.pokemon.Pokemon;

public class Tackle implements Move {
    @Override
    public String getName() {
        return "Tackle";
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
        // Basic damage formula: (Attack * Power) / (50 * Defense)
        int damage = (user.getStats().getAttack() * getPower()) / 
                    (50 * target.getStats().getDefense());
        damage = Math.max(1, damage); // Minimum 1 damage
        target.takeDamage(damage);
    }
} 