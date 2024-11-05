package com.pokemon.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontManager {
    private static Font pokemonFont;
    
    static {
        try {
            File fontFile = new File("resources/fonts/Pokemon Classic.ttf");
            pokemonFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            pokemonFont = new Font("SansSerif", Font.PLAIN, 12); // Fallback font
        }
    }
    
    public static Font getPokemonFont(int size) {
        return pokemonFont.deriveFont(Font.PLAIN, size);
    }
} 