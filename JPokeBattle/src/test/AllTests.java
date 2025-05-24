package test;

public class AllTests {
    public static void main(String[] args) {
        int totalPassed = 0, totalTests = 0;

        int passed, tests;

        // TestMove
        tests = TestMove.NUM_TESTS;
        passed = TestMove.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest Move: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest Move: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestPokemon
        tests = TestPokemon.NUM_TESTS;
        passed = TestPokemon.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest Pokemon: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest Pokemon: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestPokemonFactory
        tests = TestPokemonFactory.NUM_TESTS;
        passed = TestPokemonFactory.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest PokemonFactory: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest PokemonFactory: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestPokemonType
        tests = TestPokemonType.NUM_TESTS;
        passed = TestPokemonType.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest PokemonType: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest PokemonType: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestGameState
        tests = TestGameState.NUM_TESTS;
        passed = TestGameState.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest GameState: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest GameState: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestBattle
        tests = TestBattle.NUM_TESTS;
        passed = TestBattle.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest Battle: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest Battle: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestAIStrategy
        tests = TestAIStrategy.NUM_TESTS;
        passed = TestAIStrategy.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest AIStrategy: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest AIStrategy: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestBattleListener
        tests = TestBattleListener.NUM_TESTS;
        passed = TestBattleListener.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest BattleListener: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest BattleListener: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestFontManager
        tests = TestFontManager.NUM_TESTS;
        passed = TestFontManager.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest FontManager: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest FontManager: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        // TestMainScreen
        tests = TestMainScreen.NUM_TESTS;
        passed = TestMainScreen.runTests();
        totalPassed += passed;
        totalTests += tests;
        if (passed == tests) {
            // Green text
            System.out.println("\u001B[32mTest MainScreen: " + passed + "/" + tests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest MainScreen: " + passed + "/" + tests + " tests passed.\u001B[0m");
        }

        System.out.println("\n==============================");
        if (totalPassed == totalTests) {
            // Green text
            System.out.println("\u001B[32mTest summary: " + totalPassed + "/" + totalTests + " tests passed.\u001B[0m");
        } else {
            // Yellow text
            System.out.println("\u001B[33mTest summary: " + totalPassed + "/" + totalTests + " tests passed.\u001B[0m");
        }
        System.out.println("==============================");
    }
}
