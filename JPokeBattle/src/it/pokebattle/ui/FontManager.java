package it.pokebattle.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe che gestisce i font utilizzati nel gioco.
 * Fornisce metodi per ottenere il font Pokémon in diverse dimensioni.
 */
public class FontManager {
    private static Font pokemonFont;
    
    static {
        try {
            // Carica il font Pokémon dal file
            InputStream is = FontManager.class.getResourceAsStream("/fonts/pokemon.ttf");
            if (is != null) {
                pokemonFont = Font.createFont(Font.TRUETYPE_FONT, is);
            } else {
                // Se il font non è disponibile, usa un font di fallback
                System.err.println("Font Pokémon non trovato, uso font di fallback");
                pokemonFont = new Font("Arial", Font.PLAIN, 12);
            }
        } catch (FontFormatException | IOException e) {
            System.err.println("Errore nel caricamento del font: " + e.getMessage());
            // Usa un font di fallback
            pokemonFont = new Font("Arial", Font.PLAIN, 12);
        }
    }
    
    /**
     * Ottiene il font Pokémon nella dimensione specificata
     * 
     * @param size Dimensione del font
     * @return Font Pokémon nella dimensione specificata
     */
    public static Font getPokemonFont(int size) {
        return pokemonFont.deriveFont(Font.PLAIN, size);
    }
    
    /**
     * Ottiene il font Pokémon in grassetto nella dimensione specificata
     * 
     * @param size Dimensione del font
     * @return Font Pokémon in grassetto nella dimensione specificata
     */
    public static Font getPokemonBoldFont(int size) {
        return pokemonFont.deriveFont(Font.BOLD, size);
    }
}