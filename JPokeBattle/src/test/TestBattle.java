package test;

import it.pokebattle.model.*;
import it.pokebattle.battle.*;
import java.util.*;

public class TestBattle {
    public static final int NUM_TESTS = 4;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test creazione e listeners
        total++;
        // Crea Pokémon con almeno una mossa valida
        Move tackle = new Move("Tackle", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, "Un attacco fisico di base", Move.MoveEffect.NONE, 0);
        Pokemon p1 = new Pokemon("Bulby", "Bulbasaur", 5, 45, 49, 49, 65, 45, PokemonType.GRASS, PokemonType.POISON);
        Pokemon p2 = new Pokemon("Charmy", "Charmander", 5, 39, 52, 43, 50, 65, PokemonType.FIRE, null);
        p1.addMove(tackle);
        p2.addMove(tackle);

        List<Pokemon> team1 = new ArrayList<>(); team1.add(p1);
        List<Pokemon> team2 = new ArrayList<>(); team2.add(p2);
        Battle b = new Battle(team1, team2);
        b.addBattleListener(new BattleListener() {
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
        });
        if (b.getListeners().size() == 1) {
            System.out.println("TestBattle: addBattleListener/getListeners - PASSED");
            passed++;
        } else {
            System.out.println("TestBattle: addBattleListener/getListeners - FAILED");
        }

        // Test esecuzione mossa (non crasha)
        total++;
        try {
            b.executePlayerMove(0);
            System.out.println("TestBattle: executePlayerMove - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestBattle: executePlayerMove - FAILED");
        }

        // Test edge: squadra vuota (la creazione della battaglia deve fallire)
        total++;
        try {
            Battle b2 = new Battle(new ArrayList<>(), new ArrayList<>());
            System.out.println("TestBattle: squadra vuota - FAILED");
        } catch (Exception e) {
            System.out.println("TestBattle: squadra vuota - PASSED");
            passed++;
        }

        // Test edge: mossa non valida
        total++;
        try {
            b.executePlayerMove(99);
            System.out.println("TestBattle: mossa non valida - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestBattle: mossa non valida - FAILED");
        }

        return passed;
    }
}
