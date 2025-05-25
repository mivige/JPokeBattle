package test;

import it.pokebattle.model.Move;
import it.pokebattle.model.PokemonType;

public class TestMove {
    public static final int NUM_TESTS = 8;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test costruttore e getter
        total++;
        Move m = new Move("Tackle", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, "Un attacco fisico di base", Move.MoveEffect.NONE, 0);
        if (m.getName().equals("Tackle") && m.getPower() == 40 && m.getAccuracy() == 100) {
            System.out.println("TestMove: costruttore e getter - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: costruttore e getter - FAILED");
        }

        // Test PP decrement/increment
        total++;
        m.use();
        if (m.getCurrentPp() == 34) {
            System.out.println("TestMove: use() decrementa PP - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: use() decrementa PP - FAILED");
        }

        // Test restorePP
        total++;
        m.restorePP();
        if (m.getCurrentPp() == 35) {
            System.out.println("TestMove: restorePP() - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: restorePP() - FAILED");
        }

        // Test hits() con accuracy 0 (should always hit)
        total++;
        Move alwaysHit = new Move("SureHit", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 10, 0, 10, "Colpisce sempre", Move.MoveEffect.NONE, 0);
        boolean allHit = true;
        for (int i = 0; i < 10; i++) if (!alwaysHit.hits()) allHit = false;
        if (allHit) {
            System.out.println("TestMove: hits() accuracy 0 - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: hits() accuracy 0 - FAILED");
        }

        // Test hits() con accuracy 100 (should hit most of the time)
        total++;
        Move highAcc = new Move("HighAcc", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 10, 100, 10, "Alta precisione", Move.MoveEffect.NONE, 0);
        int hitCount = 0;
        for (int i = 0; i < 100; i++) if (highAcc.hits()) hitCount++;
        if (hitCount > 90) {
            System.out.println("TestMove: hits() accuracy 100 - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: hits() accuracy 100 - FAILED");
        }

        // Test hits() con accuracy 50 (statistica)
        total++;
        Move fiftyAcc = new Move("Fifty", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 10, 50, 10, "Mezza precisione", Move.MoveEffect.NONE, 0);
        int hit50 = 0;
        for (int i = 0; i < 100; i++) if (fiftyAcc.hits()) hit50++;
        if (hit50 > 30 && hit50 < 70) {
            System.out.println("TestMove: hits() accuracy 50 - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: hits() accuracy 50 - FAILED");
        }

        // Test PP non scende sotto 0
        total++;
        Move lowPP = new Move("LowPP", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 10, 100, 1, "PP basso", Move.MoveEffect.NONE, 0);
        lowPP.use();
        lowPP.use();
        if (lowPP.getCurrentPp() == 0) {
            System.out.println("TestMove: PP non scende sotto 0 - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: PP non scende sotto 0 - FAILED");
        }

        // Test uso mossa senza PP
        total++;
        boolean used = lowPP.use();
        if (!used) {
            System.out.println("TestMove: uso mossa senza PP - PASSED");
            passed++;
        } else {
            System.out.println("TestMove: uso mossa senza PP - FAILED");
        }

        return passed;
    }
}
