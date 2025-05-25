package test;

import it.pokebattle.model.*;

public class TestPokemon {
    public static final int NUM_TESTS = 10;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test costruttore e getter
        total++;
        Pokemon p = new Pokemon("Test", "Bulbasaur", 5, 45, 49, 49, 65, 45, PokemonType.GRASS, PokemonType.POISON);
        if (p.getName().equals("Test") && p.getSpecies().equals("Bulbasaur") && p.getLevel() == 5) {
            System.out.println("TestPokemon: costruttore e getter - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: costruttore e getter - FAILED");
        }

        // Test addMove e getMoves
        total++;
        Move m = new Move("Tackle", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, "Un attacco fisico di base", Move.MoveEffect.NONE, 0);
        boolean added = p.addMove(m);
        if (added && p.getMoves().size() == 1 && p.getMoves().get(0).getName().equals("Tackle")) {
            System.out.println("TestPokemon: addMove/getMoves - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: addMove/getMoves - FAILED");
        }

        // Test addExperience e level up
        total++;
        int oldLevel = p.getLevel();
        p.addExperience(1000);
        if (p.getLevel() > oldLevel) {
            System.out.println("TestPokemon: addExperience/levelUp - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: addExperience/levelUp - FAILED");
        }

        // Test canEvolve e evolve (edge: non evolve se non può)
        total++;
        p.setEvolutionInfo("Ivysaur", 100); // troppo alto
        p.addExperience(10000);
        if (!p.getSpecies().equals("Ivysaur")) {
            System.out.println("TestPokemon: evolve (non evolve se non può) - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: evolve (non evolve se non può) - FAILED");
        }

        // Test evolve (edge: evolve se può)
        total++;
        p.setEvolutionInfo("Ivysaur", p.getLevel());
        p.evolve();
        if (p.getSpecies().equals("Ivysaur")) {
            System.out.println("TestPokemon: evolve (evolve se può) - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: evolve (evolve se può) - FAILED");
        }

        // Test isKO
        total++;
        p.setCurrentHp(0);
        if (p.isKO()) {
            System.out.println("TestPokemon: isKO - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: isKO - FAILED");
        }

        // Test restoreAllPP
        total++;
        p.getMoves().get(0).use();
        p.restoreAllPP();
        if (p.getMoves().get(0).getCurrentPp() == p.getMoves().get(0).getPp()) {
            System.out.println("TestPokemon: restoreAllPP - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: restoreAllPP - FAILED");
        }

        // Test KO dopo danno
        total++;
        p.setCurrentHp(1);
        p.takeDamage(1);
        if (p.isKO()) {
            System.out.println("TestPokemon: KO dopo danno - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: KO dopo danno - FAILED");
        }

        // Test esperienza negativa (non scende sotto 0)
        total++;
        p.addExperience(-1000);
        if (Integer.parseInt(p.getExperience()) >= 0) {
            System.out.println("TestPokemon: esperienza negativa - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: esperienza negativa - FAILED");
        }

        // Test evoluzione multipla
        total++;
        p.setEvolutionInfo("Venusaur", p.getLevel());
        p.evolve();
        if (p.getSpecies().equals("Venusaur")) {
            System.out.println("TestPokemon: evoluzione multipla - PASSED");
            passed++;
        } else {
            System.out.println("TestPokemon: evoluzione multipla - FAILED");
        }

        return passed;
    }
}
