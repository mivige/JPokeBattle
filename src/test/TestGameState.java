package test;

import it.pokebattle.game.GameState;
import it.pokebattle.model.Pokemon;
import it.pokebattle.model.PokemonFactory;

public class TestGameState {
    public static final int NUM_TESTS = 4;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test singleton
        total++;
        GameState g1 = GameState.getInstance();
        GameState g2 = GameState.getInstance();
        if (g1 == g2) {
            System.out.println("TestGameState: singleton - PASSED");
            passed++;
        } else {
            System.out.println("TestGameState: singleton - FAILED");
        }

        // Test addPokemonToTeam e getPlayerTeam
        total++;
        Pokemon p = PokemonFactory.createPokemon("Bulbasaur", 5);
        boolean added = (p != null) && g1.addPokemonToTeam(p);
        if (added && g1.getPlayerTeam().contains(p)) {
            System.out.println("TestGameState: addPokemonToTeam/getPlayerTeam - PASSED");
            passed++;
        } else {
            System.out.println("TestGameState: addPokemonToTeam/getPlayerTeam - FAILED");
        }

        // Test consecutiveWins e saveBattleResult
        total++;
        int oldWins = g1.getConsecutiveWins();
        g1.saveBattleResult(true);
        if (g1.getConsecutiveWins() == oldWins + 1) {
            System.out.println("TestGameState: saveBattleResult vittoria - PASSED");
            passed++;
        } else {
            System.out.println("TestGameState: saveBattleResult vittoria - FAILED");
        }

        // Test leaderboard
        total++;
        g1.addToLeaderboard("TestPlayer", 99);
        if (g1.getLeaderboard().containsKey("TestPlayer")) {
            System.out.println("TestGameState: addToLeaderboard/getLeaderboard - PASSED");
            passed++;
        } else {
            System.out.println("TestGameState: addToLeaderboard/getLeaderboard - FAILED");
        }

        return passed;
    }
}
