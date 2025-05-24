package test;

import it.pokebattle.model.*;

public class TestPokemonFactory {
    public static final int NUM_TESTS = 5;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test creazione Pokémon esistente
        total++;
        Pokemon p = PokemonFactory.createPokemon("Bulbasaur", 5);
        if (p != null && p.getSpecies().equals("Bulbasaur")) {
            System.out.println("TestPokemonFactory: createPokemon specie esistente - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: createPokemon specie esistente - FAILED");
        }

        // Test creazione Pokémon non esistente
        total++;
        Pokemon p2 = PokemonFactory.createPokemon("FakeMon", 5);
        if (p2 == null) {
            System.out.println("TestPokemonFactory: createPokemon specie NON esistente - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: createPokemon specie NON esistente - FAILED");
        }

        // Test creazione Pokémon livello minimo
        total++;
        Pokemon p3 = PokemonFactory.createPokemon("Bulbasaur", 1);
        if (p3 != null && p3.getLevel() == 1) {
            System.out.println("TestPokemonFactory: createPokemon livello minimo - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: createPokemon livello minimo - FAILED");
        }

        // Test getAllSpecies
        total++;
        if (PokemonFactory.getAllSpecies().contains("Bulbasaur")) {
            System.out.println("TestPokemonFactory: getAllSpecies - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: getAllSpecies - FAILED");
        }

        // Test getAllSpecies non vuoto
        total++;
        if (!PokemonFactory.getAllSpecies().isEmpty()) {
            System.out.println("TestPokemonFactory: getAllSpecies non vuoto - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: getAllSpecies non vuoto - FAILED");
        }

        return passed;
    }
}
