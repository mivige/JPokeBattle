package it.pokebattle.model;

/**
 * Enum che rappresenta i tipi di Pokémon disponibili nel gioco.
 * Basato sui tipi della prima generazione di Pokémon.
 */
public enum PokemonType {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON;
    
    /**
     * Calcola il moltiplicatore di danno in base al tipo dell'attacco e al tipo del difensore
     * 
     * @param attackType Tipo dell'attacco
     * @param defenderType Tipo del difensore
     * @return Moltiplicatore di danno (0.0, 0.5, 1.0, 2.0)
     */
    public static double getTypeEffectiveness(PokemonType attackType, PokemonType defenderType) {
        if (attackType == null || defenderType == null) {
            return 1.0; // Nessun vantaggio o svantaggio se uno dei tipi è null
        }
        
        // Implementazione delle relazioni di tipo della prima generazione
        switch (attackType) {
            case NORMAL:
                if (defenderType == ROCK) return 0.5;
                if (defenderType == GHOST) return 0.0;
                return 1.0;
                
            case FIRE:
                if (defenderType == FIRE || defenderType == WATER || defenderType == ROCK || defenderType == DRAGON) return 0.5;
                if (defenderType == GRASS || defenderType == ICE || defenderType == BUG) return 2.0;
                return 1.0;
                
            case WATER:
                if (defenderType == WATER || defenderType == GRASS || defenderType == DRAGON) return 0.5;
                if (defenderType == FIRE || defenderType == GROUND || defenderType == ROCK) return 2.0;
                return 1.0;
                
            case ELECTRIC:
                if (defenderType == ELECTRIC || defenderType == GRASS || defenderType == DRAGON) return 0.5;
                if (defenderType == GROUND) return 0.0;
                if (defenderType == WATER || defenderType == FLYING) return 2.0;
                return 1.0;
                
            case GRASS:
                if (defenderType == FIRE || defenderType == GRASS || defenderType == POISON || 
                    defenderType == FLYING || defenderType == BUG || defenderType == DRAGON) return 0.5;
                if (defenderType == WATER || defenderType == GROUND || defenderType == ROCK) return 2.0;
                return 1.0;
                
            case ICE:
                if (defenderType == WATER || defenderType == ICE) return 0.5;
                if (defenderType == GRASS || defenderType == GROUND || defenderType == FLYING || defenderType == DRAGON) return 2.0;
                return 1.0;
                
            case FIGHTING:
                if (defenderType == POISON || defenderType == FLYING || defenderType == PSYCHIC || defenderType == BUG) return 0.5;
                if (defenderType == GHOST) return 0.0;
                if (defenderType == NORMAL || defenderType == ICE || defenderType == ROCK) return 2.0;
                return 1.0;
                
            case POISON:
                if (defenderType == POISON || defenderType == GROUND || defenderType == ROCK || defenderType == GHOST) return 0.5;
                if (defenderType == GRASS || defenderType == BUG) return 2.0;
                return 1.0;
                
            case GROUND:
                if (defenderType == GRASS || defenderType == BUG) return 0.5;
                if (defenderType == FLYING) return 0.0;
                if (defenderType == FIRE || defenderType == ELECTRIC || defenderType == POISON || 
                    defenderType == ROCK) return 2.0;
                return 1.0;
                
            case FLYING:
                if (defenderType == ELECTRIC || defenderType == ROCK) return 0.5;
                if (defenderType == GRASS || defenderType == FIGHTING || defenderType == BUG) return 2.0;
                return 1.0;
                
            case PSYCHIC:
                if (defenderType == PSYCHIC) return 0.5;
                if (defenderType == FIGHTING || defenderType == POISON) return 2.0;
                return 1.0;
                
            case BUG:
                if (defenderType == FIRE || defenderType == FIGHTING || defenderType == FLYING || 
                    defenderType == GHOST) return 0.5;
                if (defenderType == GRASS || defenderType == POISON || defenderType == PSYCHIC) return 2.0;
                return 1.0;
                
            case ROCK:
                if (defenderType == FIGHTING || defenderType == GROUND) return 0.5;
                if (defenderType == FIRE || defenderType == ICE || defenderType == FLYING || defenderType == BUG) return 2.0;
                return 1.0;
                
            case GHOST:
                if (defenderType == NORMAL) return 0.0;
                if (defenderType == GHOST || defenderType == PSYCHIC) return 2.0;
                return 1.0;
                
            case DRAGON:
                if (defenderType == DRAGON) return 2.0;
                return 1.0;
                
            default:
                return 1.0;
        }
    }
    
    /**
     * Calcola il moltiplicatore di danno considerando anche un eventuale tipo secondario
     * 
     * @param attackType Tipo dell'attacco
     * @param primaryDefenderType Tipo primario del difensore
     * @param secondaryDefenderType Tipo secondario del difensore (può essere null)
     * @return Moltiplicatore di danno combinato
     */
    public static double getTypeEffectiveness(PokemonType attackType, PokemonType primaryDefenderType, PokemonType secondaryDefenderType) {
        double effectiveness = getTypeEffectiveness(attackType, primaryDefenderType);
        
        // Se c'è un tipo secondario, moltiplica per la sua efficacia
        if (secondaryDefenderType != null) {
            effectiveness *= getTypeEffectiveness(attackType, secondaryDefenderType);
        }
        
        return effectiveness;
    }
}