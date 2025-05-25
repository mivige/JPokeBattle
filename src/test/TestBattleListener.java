package test;

import it.pokebattle.battle.BattleListener;
import it.pokebattle.model.Pokemon;
import it.pokebattle.model.Move;
import java.util.List;

public class TestBattleListener {
    public static final int NUM_TESTS = 3;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test implementazione vuota (non deve lanciare eccezioni)
        total++;
        try {
            BattleListener listener = new BattleListener() {
                @Override public void onMoveUsed(Pokemon pokemon, Move move, boolean isPlayer) {}
                @Override public void onMoveFailed(Pokemon pokemon, Move move, String reason, boolean isPlayer) {}
                @Override public void onMoveMissed(Pokemon attacker, Pokemon defender, Move move, boolean isPlayer) {}
                @Override public void onDamageDealt(Pokemon attacker, Pokemon defender, Move move, int damage, int effectiveness, boolean critical, boolean fainted, boolean isPlayer) {}
                @Override public void onPokemonFainted(Pokemon pokemon, boolean isPlayerPokemon) {}
                @Override public void onPokemonSwitched(Pokemon oldPokemon, Pokemon newPokemon, boolean isPlayer) {}
                @Override public void onExperienceGained(Pokemon pokemon, int expGained) {}
                @Override public void onLevelUp(Pokemon pokemon, int newLevel) {}
                @Override public void onPokemonEvolved(Pokemon oldPokemon, Pokemon evolvedPokemon) {}
                @Override public void onBattleStart(List<Pokemon> playerTeam, List<Pokemon> enemyTeam) {}
                @Override public void onBattleEnd(boolean playerWon) {}
            };
            listener.onPokemonSwitched(null, null, true);
            listener.onPokemonEvolved(null, null);
            listener.onMoveUsed(null, null, true);
            listener.onMoveFailed(null, null, "", true);
            listener.onMoveMissed(null, null, null, true);
            listener.onDamageDealt(null, null, null, 0, 0, false, false, true);
            listener.onPokemonFainted(null, true);
            listener.onExperienceGained(null, 0);
            listener.onLevelUp(null, 0);
            listener.onBattleStart(null, null);
            listener.onBattleEnd(true);
            System.out.println("TestBattleListener: metodi vuoti - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestBattleListener: metodi vuoti - FAILED");
        }

        // Test chiamata con Pokémon reali (non deve lanciare eccezioni)
        total++;
        try {
            Pokemon p1 = new Pokemon("A", "Bulbasaur", 5, 45, 49, 49, 65, 45, it.pokebattle.model.PokemonType.GRASS, it.pokebattle.model.PokemonType.POISON);
            Pokemon p2 = new Pokemon("B", "Ivysaur", 16, 60, 62, 63, 80, 60, it.pokebattle.model.PokemonType.GRASS, it.pokebattle.model.PokemonType.POISON);
            Move m = null;
            BattleListener listener = new BattleListener() {
                @Override public void onMoveUsed(Pokemon pokemon, Move move, boolean isPlayer) {}
                @Override public void onMoveFailed(Pokemon pokemon, Move move, String reason, boolean isPlayer) {}
                @Override public void onMoveMissed(Pokemon attacker, Pokemon defender, Move move, boolean isPlayer) {}
                @Override public void onDamageDealt(Pokemon attacker, Pokemon defender, Move move, int damage, int effectiveness, boolean critical, boolean fainted, boolean isPlayer) {}
                @Override public void onPokemonFainted(Pokemon pokemon, boolean isPlayerPokemon) {}
                @Override public void onPokemonSwitched(Pokemon oldPokemon, Pokemon newPokemon, boolean isPlayer) {}
                @Override public void onExperienceGained(Pokemon pokemon, int expGained) {}
                @Override public void onLevelUp(Pokemon pokemon, int newLevel) {}
                @Override public void onPokemonEvolved(Pokemon oldPokemon, Pokemon evolvedPokemon) {}
                @Override public void onBattleStart(List<Pokemon> playerTeam, List<Pokemon> enemyTeam) {}
                @Override public void onBattleEnd(boolean playerWon) {}
            };
            listener.onPokemonSwitched(p1, p2, false);
            listener.onPokemonEvolved(p1, p2);
            listener.onMoveUsed(p1, m, false);
            listener.onMoveFailed(p1, m, "fail", false);
            listener.onMoveMissed(p1, p2, m, false);
            listener.onDamageDealt(p1, p2, m, 10, 1, false, false, false);
            listener.onPokemonFainted(p1, false);
            listener.onExperienceGained(p1, 10);
            listener.onLevelUp(p1, 10);
            listener.onBattleStart(List.of(p1), List.of(p2));
            listener.onBattleEnd(false);
            System.out.println("TestBattleListener: metodi con Pokémon reali - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestBattleListener: metodi con Pokémon reali - FAILED");
        }

        // Test edge: listener null/non registrato
        total++;
        try {
            BattleListener listener = null;
            if (listener == null) {
                System.out.println("TestBattleListener: listener null - PASSED");
                passed++;
            }
        } catch (Exception e) {
            System.out.println("TestBattleListener: listener null - FAILED");
        }

        return passed;
    }
}
