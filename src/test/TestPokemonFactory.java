package test;

import it.pokebattle.model.*;

public class TestPokemonFactory {
    public static final int NUM_TESTS = 9;

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

        // Test creazione Pokémon con livello massimo
        total++;
        Pokemon p4 = PokemonFactory.createPokemon("Bulbasaur", 100);
        if (p4 != null && p4.getLevel() == 100) {
            System.out.println("TestPokemonFactory: createPokemon livello massimo - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: createPokemon livello massimo - FAILED");
        }

        // Test creazione Pokémon con livello fuori range (negativo)
        total++;
        Pokemon p5 = PokemonFactory.createPokemon("Bulbasaur", -5);
        if (p5 != null && p5.getLevel() >= 1) {
            System.out.println("TestPokemonFactory: createPokemon livello negativo - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: createPokemon livello negativo - FAILED");
        }

        // Test Ditto: non deve essere trasformato e deve avere solo le sue mosse base
        total++;
        Pokemon ditto = PokemonFactory.createPokemon("Ditto", 10);
        boolean dittoOk = ditto != null && ditto.getSpecies().equals("Ditto")
            && ditto.getMoves().size() > 0
            && ditto.getMoves().size() <= 4
            && ditto.getMoves().stream().allMatch(m -> !m.getName().equals("Transform") || ditto.getMoves().size() == 1);
        if (dittoOk) {
            System.out.println("TestPokemonFactory: Ditto non trasformato - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: Ditto non trasformato - FAILED");
        }

        // Test mosse non duplicate
        total++;
        Pokemon bulba = PokemonFactory.createPokemon("Bulbasaur", 20);
        long leechCount = bulba.getMoves().stream().filter(m -> m.getName().equals("Leech Seed")).count();
        if (leechCount <= 1) {
            System.out.println("TestPokemonFactory: Leech Seed non duplicata - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: Leech Seed non duplicata - FAILED");
        }

        // Test nessun Pokémon ha più di 4 mosse e nessun duplicato
        total++;
        boolean allOk = true;
        for (String species : PokemonFactory.getAllSpecies()) {
            Pokemon poke = PokemonFactory.createPokemon(species, 50);
            if (poke.getMoves().size() > 4) allOk = false;
            long unique = poke.getMoves().stream().map(Move::getName).distinct().count();
            if (unique != poke.getMoves().size()) allOk = false;
        }
        if (allOk) {
            System.out.println("TestPokemonFactory: nessun Pokémon con più di 4 mosse o duplicati - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: nessun Pokémon con più di 4 mosse o duplicati - FAILED");
        }

        // Test che non si possano creare Pokémon rimossi (Darkrai, Shellos, Gastrodon)
        // Derivato da un errore di implementazione iniziale *
        total++;
        boolean removedOk = PokemonFactory.createPokemon("Darkrai", 50) == null
            && PokemonFactory.createPokemon("Shellos", 50) == null
            && PokemonFactory.createPokemon("Gastrodon", 50) == null;
        if (removedOk) {
            System.out.println("TestPokemonFactory: specie rimosse non creabili - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemonFactory: specie rimosse non creabili - FAILED");
        }

        return passed;
    }
}
