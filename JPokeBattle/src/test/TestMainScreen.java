package test;

import it.pokebattle.ui.MainScreen;

public class TestMainScreen {
    public static final int NUM_TESTS = 2;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test creazione MainScreen (non deve lanciare eccezioni)
        total++;
        try {
            MainScreen ms = new MainScreen();
            System.out.println("TestMainScreen: costruttore - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestMainScreen: costruttore - FAILED");
        }

        // Test showVictoryScreen e showGameOverScreen (non devono lanciare eccezioni)
        total++;
        try {
            MainScreen ms = new MainScreen();
            ms.showVictoryScreen();
            ms.showGameOverScreen(false);
            System.out.println("TestMainScreen: showVictoryScreen/showGameOverScreen - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("TestMainScreen: showVictoryScreen/showGameOverScreen - FAILED");
        }

        return passed;
    }
}
