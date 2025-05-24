package test;

import it.pokebattle.model.*;
import it.pokebattle.battle.*;

import java.util.*;

public class TestAIStrategy {
    public static final int NUM_TESTS = 6;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test AIRandomStrategy: sempre restituisce indice valido
        total++;
        Pokemon p = new Pokemon("AI", "Bulbasaur", 5, 45, 49, 49, 65, 45, PokemonType.GRASS, PokemonType.POISON);
        Pokemon opp = new Pokemon("Player", "Charmander", 5, 39, 52, 43, 50, 65, PokemonType.FIRE, null);
        Move m1 = new Move("Tackle", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, "Un attacco fisico di base", Move.MoveEffect.NONE, 0);
        Move m2 = new Move("Growl", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 40, "Abbassa l'attacco", Move.MoveEffect.ATTACK_DOWN, 100);
        p.addMove(m1); p.addMove(m2);
        List<Move> moves = p.getMoves();
        AIRandomStrategy aiRand = new AIRandomStrategy();
        int idx = aiRand.chooseMove(p, opp, moves);
        if (idx >= 0 && idx < moves.size()) {
            System.out.println("TestAIStrategy: AIRandomStrategy indice valido - PASSED");
            passed++;
        } else {
            System.out.println("TestAIStrategy: AIRandomStrategy indice valido - FAILED");
        }

        // Test AIMaxDamageStrategy: preferisce mossa con danno massimo
        total++;
        AIMaxDamageStrategy aiMax = new AIMaxDamageStrategy();
        int idx2 = aiMax.chooseMove(p, opp, moves);
        if (moves.get(idx2).getName().equals("Tackle")) {
            System.out.println("TestAIStrategy: AIMaxDamageStrategy preferisce danno - PASSED");
            passed++;
        } else {
            System.out.println("TestAIStrategy: AIMaxDamageStrategy preferisce danno - FAILED");
        }

        // Test AIRandomStrategy: chooseSwitch (non cambia se non necessario)
        total++;
        List<Pokemon> team = new ArrayList<>();
        team.add(p); team.add(opp);
        int sw = aiRand.chooseSwitch(p, opp, team);
        if (sw == -1) {
            System.out.println("TestAIStrategy: AIRandomStrategy chooseSwitch - PASSED");
            passed++;
        } else {
            System.out.println("TestAIStrategy: AIRandomStrategy chooseSwitch - FAILED");
        }

        // Test AIMaxDamageStrategy: chooseSwitch (cambia se HP bassi)
        total++;
        p.setCurrentHp(1);
        int sw2 = aiMax.chooseSwitch(p, opp, team);
        if (sw2 == 1) {
            System.out.println("TestAIStrategy: AIMaxDamageStrategy chooseSwitch - PASSED");
            passed++;
        } else {
            System.out.println("TestAIStrategy: AIMaxDamageStrategy chooseSwitch - FAILED");
        }

        // Test edge: nessuna mossa disponibile
        total++;
        List<Move> emptyMoves = new ArrayList<>();
        try {
            aiRand.chooseMove(p, opp, emptyMoves);
            System.out.println("TestAIStrategy: nessuna mossa disponibile - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestAIStrategy: nessuna mossa disponibile - FAILED");
        }

        // Test edge: tutte le mosse senza PP
        total++;
        for (Move m : moves) while (m.getCurrentPp() > 0) m.use();
        try {
            aiRand.chooseMove(p, opp, moves);
            System.out.println("TestAIStrategy: tutte le mosse senza PP - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestAIStrategy: tutte le mosse senza PP - FAILED");
        }

        return passed;
    }
}
