package test;

import it.pokebattle.ui.FontManager;
import java.awt.Font;

public class TestFontManager {
    public static final int NUM_TESTS = 3;

    public static int runTests() {
        int passed = 0, total = 0;

        // Test caricamento font di dimensione standard
        total++;
        Font f = FontManager.getPokemonFont(24);
        if (f != null && f.getSize() == 24) {
            System.out.println("TestFontManager: getPokemonFont dimensione - PASSED");
            passed++;
        } else {
            System.out.println("TestFontManager: getPokemonFont dimensione - FAILED");
        }

        // Test caricamento font di dimensione diversa
        total++;
        Font f2 = FontManager.getPokemonFont(48);
        if (f2 != null && f2.getSize() == 48) {
            System.out.println("TestFontManager: getPokemonFont dimensione diversa - PASSED");
            passed++;
        } else {
            System.out.println("TestFontManager: getPokemonFont dimensione diversa - FAILED");
        }

        // Test edge: font dimensione 0
        total++;
        Font f3 = FontManager.getPokemonFont(0);
        if (f3 != null && f3.getSize() == 0) {
            System.out.println("TestFontManager: getPokemonFont dimensione 0 - PASSED");
            passed++;
        } else {
            System.out.println("TestFontManager: getPokemonFont dimensione 0 - FAILED");
        }

        return passed;
    }
}
