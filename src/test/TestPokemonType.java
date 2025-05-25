package test;

import it.pokebattle.model.PokemonType;

public class TestPokemonType {
    public static final int NUM_TESTS = 4;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test efficacia tipo (edge: superefficace)
        total++;
        double eff = PokemonType.getTypeEffectiveness(PokemonType.FIRE, PokemonType.GRASS, null);
        if (eff > 1.0) {
            System.out.println("TestPokemonType: efficacia superefficace - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonType: efficacia superefficace - FAILED");
        }

        // Test efficacia tipo (edge: inefficace)
        total++;
        double eff2 = PokemonType.getTypeEffectiveness(PokemonType.ELECTRIC, PokemonType.GROUND, null);
        if (eff2 == 0.0) {
            System.out.println("TestPokemonType: efficacia inefficace - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonType: efficacia inefficace - FAILED");
        }

        // Test efficacia tipo doppio
        total++;
        double eff3 = PokemonType.getTypeEffectiveness(PokemonType.FIRE, PokemonType.GRASS, PokemonType.POISON);
        if (eff3 > 0) {
            System.out.println("TestPokemonType: efficacia doppio tipo - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonType: efficacia doppio tipo - FAILED");
        }

        // Test efficacia tipo null
        total++;
        double eff4 = PokemonType.getTypeEffectiveness(null, null, null);
        if (eff4 == 1.0) {
            System.out.println("TestPokemonType: efficacia tipo null - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonType: efficacia tipo null - FAILED");
        }

        return passed;
    }
}
