package it.pokebattle.model;

import java.io.Serializable;

/**
 * Classe che rappresenta una mossa Pokémon.
 * Contiene tutte le informazioni relative a una mossa, come potenza, precisione, tipo, ecc.
 */
public class Move implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name; // Nome della mossa
    private PokemonType type; // Tipo della mossa
    private MoveCategory category; // Categoria della mossa (fisico, speciale, stato)
    private int power; // Potenza della mossa (0 per mosse di stato)
    private int accuracy; // Precisione della mossa (0-100, 0 = non fallisce mai)
    private int pp; // Punti Potenza (numero di volte che la mossa può essere usata)
    private int currentPp; // PP attuali
    private int priority; // Priorità della mossa (default 0, valori più alti agiscono prima)
    private String description; // Descrizione della mossa
    private MoveEffect effect; // Effetto speciale della mossa
    private int effectChance; // Probabilità che l'effetto si verifichi (0-100)
    
    /**
     * Costruttore per una mossa di danno
     * 
     * @param name Nome della mossa
     * @param type Tipo della mossa
     * @param category Categoria della mossa
     * @param power Potenza della mossa
     * @param accuracy Precisione della mossa (0-100, 0 = non fallisce mai)
     * @param pp Punti Potenza totali
     * @param description Descrizione della mossa
     */
    public Move(String name, PokemonType type, MoveCategory category, int power, int accuracy, int pp, String description) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.currentPp = pp;
        this.priority = 0;
        this.description = description;
        this.effect = MoveEffect.NONE;
        this.effectChance = 0;
    }
    
    /**
     * Costruttore per una mossa con effetto
     * 
     * @param name Nome della mossa
     * @param type Tipo della mossa
     * @param category Categoria della mossa
     * @param power Potenza della mossa
     * @param accuracy Precisione della mossa (0-100, 0 = non fallisce mai)
     * @param pp Punti Potenza totali
     * @param description Descrizione della mossa
     * @param effect Effetto della mossa
     * @param effectChance Probabilità che l'effetto si verifichi (0-100)
     */
    public Move(String name, PokemonType type, MoveCategory category, int power, int accuracy, int pp, 
               String description, MoveEffect effect, int effectChance) {
        this(name, type, category, power, accuracy, pp, description);
        this.effect = effect;
        this.effectChance = effectChance;
    }
    
    /**
     * Costruttore per una mossa con priorità
     * 
     * @param name Nome della mossa
     * @param type Tipo della mossa
     * @param category Categoria della mossa
     * @param power Potenza della mossa
     * @param accuracy Precisione della mossa (0-100, 0 = non fallisce mai)
     * @param pp Punti Potenza totali
     * @param description Descrizione della mossa
     * @param priority Priorità della mossa
     */
    public Move(String name, PokemonType type, MoveCategory category, int power, int accuracy, int pp, 
               String description, int priority) {
        this(name, type, category, power, accuracy, pp, description);
        this.priority = priority;
    }
    
    /**
     * Costruttore per una mossa con effetto e priorità
     * 
     * @param name Nome della mossa
     * @param type Tipo della mossa
     * @param category Categoria della mossa
     * @param power Potenza della mossa
     * @param accuracy Precisione della mossa (0-100, 0 = non fallisce mai)
     * @param pp Punti Potenza totali
     * @param description Descrizione della mossa
     * @param effect Effetto della mossa
     * @param effectChance Probabilità che l'effetto si verifichi (0-100)
     * @param priority Priorità della mossa
     */
    public Move(String name, PokemonType type, MoveCategory category, int power, int accuracy, int pp, 
               String description, MoveEffect effect, int effectChance, int priority) {
        this(name, type, category, power, accuracy, pp, description, effect, effectChance);
        this.priority = priority;
    }
    
    /**
     * Usa la mossa, riducendo i PP attuali
     * @return true se la mossa può essere usata, false se non ci sono più PP
     */
    public boolean use() {
        if (currentPp > 0) {
            currentPp--;
            return true;
        }
        return false;
    }
    
    /**
     * Ripristina i PP della mossa al valore massimo
     */
    public void restorePP() {
        this.currentPp = this.pp;
    }
    
    /**
     * Controlla se la mossa colpisce in base alla sua precisione
     * @return true se la mossa colpisce, false se manca
     */
    public boolean hits() {
        // Se la precisione è 0, la mossa non fallisce mai
        if (accuracy == 0) {
            return true;
        }
        
        // Altrimenti, calcola in base alla precisione
        return Math.random() * 100 < accuracy;
    }
    
    /**
     * Controlla se l'effetto della mossa si attiva in base alla probabilità
     * @return true se l'effetto si attiva, false altrimenti
     */
    public boolean effectActivates() {
        if (effect == MoveEffect.NONE || effectChance == 0) {
            return false;
        }
        
        return Math.random() * 100 < effectChance;
    }
    
    // Getters
    
    public String getName() {
        return name;
    }
    
    public PokemonType getType() {
        return type;
    }
    
    public MoveCategory getCategory() {
        return category;
    }
    
    public int getPower() {
        return power;
    }
    
    public int getAccuracy() {
        return accuracy;
    }
    
    public int getPp() {
        return pp;
    }
    
    public int getCurrentPp() {
        return currentPp;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public String getDescription() {
        return description;
    }
    
    public MoveEffect getEffect() {
        return effect;
    }
    
    public int getEffectChance() {
        return effectChance;
    }
    
    /**
     * Enum che rappresenta la categoria della mossa
     */
    public enum MoveCategory {
        PHYSICAL, // Mosse fisiche (usano Attacco e Difesa)
        SPECIAL,  // Mosse speciali (usano Speciale)
        STATUS    // Mosse di stato (non infliggono danno diretto)
    }
    
    /**
     * Enum che rappresenta gli effetti speciali delle mosse
     */
    public enum MoveEffect {
        NONE,           // Nessun effetto speciale
        ATTACK_UP,      // Aumenta l'attacco
        ATTACK_DOWN,    // Diminuisce l'attacco
        DEFENSE_UP,     // Aumenta la difesa
        DEFENSE_DOWN,   // Diminuisce la difesa
        SPEED_UP,       // Aumenta la velocità
        SPEED_DOWN,     // Diminuisce la velocità
        SPECIAL_UP,     // Aumenta lo speciale
        SPECIAL_DOWN,   // Diminuisce lo speciale
        ACCURACY_UP,    // Aumenta la precisione
        ACCURACY_DOWN,  // Diminuisce la precisione
        EVASION_UP,     // Aumenta l'evasione
        EVASION_DOWN,   // Diminuisce l'evasione
        CRITICAL_UP,    // Aumenta probabilità di colpo critico
        HEAL,           // Cura HP
        RECOIL,         // Danno da contraccolpo all'utilizzatore
        HIGH_CRITICAL   // Alta probabilità di colpo critico
    }
}