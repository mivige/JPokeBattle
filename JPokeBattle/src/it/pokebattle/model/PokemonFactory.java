package it.pokebattle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory class per la creazione di Pokémon predefiniti.
 * Contiene metodi per creare i Pokémon iniziali e altri Pokémon specificati nei requisiti.
 */
public class PokemonFactory {
    // Mappa che associa il nome della specie al Pokémon corrispondente
    private static final Map<String, PokemonTemplate> pokemonTemplates = new HashMap<>();
    
    // Inizializza i template dei Pokémon
    static {
        initializeTemplates();
    }
    
    /**
     * Inizializza i template dei Pokémon con le loro statistiche base e mosse
     */
    private static void initializeTemplates() {
        // Bulbasaur (Bulbo)
        PokemonTemplate bulbasaur = new PokemonTemplate(
            "Bulbo", "Bulbasaur", 
            45, 49, 49, 65, 45, // HP, Atk, Def, Spc, Spd
            PokemonType.GRASS, PokemonType.POISON
        );
        bulbasaur.addLearnableMove(1, createMove("Growl"));
        bulbasaur.addLearnableMove(1, createMove("Tackle"));
        bulbasaur.addLearnableMove(7, createMove("Leech Seed"));
        bulbasaur.addLearnableMove(13, createMove("Vine Whip"));
        bulbasaur.addLearnableMove(20, createMove("PoisonPowder"));
        bulbasaur.addLearnableMove(27, createMove("Razor Leaf"));
        bulbasaur.addLearnableMove(34, createMove("Growth"));
        bulbasaur.addLearnableMove(41, createMove("Sleep Powder"));
        bulbasaur.addLearnableMove(48, createMove("SolarBeam"));
        bulbasaur.setEvolutionInfo("Ivysaur", 16);
        pokemonTemplates.put("Bulbasaur", bulbasaur);

        // Ivysaur (Ignazio)
        PokemonTemplate ivysaur = new PokemonTemplate(
            "Ignazio", "Ivysaur", 
            60, 62, 63, 80, 60, // HP, Atk, Def, Spc, Spd
            PokemonType.GRASS, PokemonType.POISON
        );
        ivysaur.addLearnableMove(1, createMove("Growl"));
        ivysaur.addLearnableMove(1, createMove("Tackle"));
        ivysaur.addLearnableMove(1, createMove("Leech Seed"));
        ivysaur.addLearnableMove(1, createMove("Vine Whip"));
        ivysaur.addLearnableMove(22, createMove("Razor Leaf"));
        ivysaur.addLearnableMove(30, createMove("Sleep Powder"));
        ivysaur.setEvolutionInfo("Venusaur", 32);
        pokemonTemplates.put("Ivysaur", ivysaur);

        // Venusaur (Vincenzo)
        PokemonTemplate venusaur = new PokemonTemplate(
            "Vincenzo", "Venusaur", 
            80, 82, 83, 100, 80, // HP, Atk, Def, Spc, Spd
            PokemonType.GRASS, PokemonType.POISON
        );
        venusaur.addLearnableMove(1, createMove("Growl"));
        venusaur.addLearnableMove(1, createMove("Tackle"));
        venusaur.addLearnableMove(1, createMove("Leech Seed"));
        venusaur.addLearnableMove(1, createMove("Vine Whip"));
        venusaur.addLearnableMove(1, createMove("Razor Leaf"));
        venusaur.addLearnableMove(43, createMove("Solar Beam"));
        pokemonTemplates.put("Venusaur", venusaur);

        // Charmander (Carmine)
        PokemonTemplate charmander = new PokemonTemplate(
            "Carmine", "Charmander", 
            39, 52, 43, 50, 65, // HP, Atk, Def, Spc, Spd
            PokemonType.FIRE, null
        );
        charmander.addLearnableMove(1, createMove("Growl"));
        charmander.addLearnableMove(1, createMove("Scratch"));
        charmander.addLearnableMove(9, createMove("Ember"));
        charmander.addLearnableMove(15, createMove("Leer"));
        charmander.setEvolutionInfo("Charmeleon", 16);
        pokemonTemplates.put("Charmander", charmander);

        // Charmeleon (Carmelo)
        PokemonTemplate charmeleon = new PokemonTemplate(
            "Carmelo", "Charmeleon", 
            58, 64, 58, 65, 80, // HP, Atk, Def, Spc, Spd
            PokemonType.FIRE, null
        );
        charmeleon.addLearnableMove(1, createMove("Growl"));
        charmeleon.addLearnableMove(1, createMove("Scratch"));
        charmeleon.addLearnableMove(1, createMove("Ember"));
        charmeleon.addLearnableMove(24, createMove("Rage"));
        charmeleon.addLearnableMove(33, createMove("Slash"));
        charmeleon.setEvolutionInfo("Charizard", 36);
        pokemonTemplates.put("Charmeleon", charmeleon);

        // Charizard (Caruso)
        PokemonTemplate charizard = new PokemonTemplate(
            "Caruso", "Charizard", 
            78, 84, 78, 85, 100, // HP, Atk, Def, Spc, Spd
            PokemonType.FIRE, PokemonType.FLYING
        );
        charizard.addLearnableMove(1, createMove("Growl"));
        charizard.addLearnableMove(1, createMove("Scratch"));
        charizard.addLearnableMove(1, createMove("Ember"));
        charizard.addLearnableMove(1, createMove("Rage"));
        charizard.addLearnableMove(46, createMove("Fire Spin"));
        pokemonTemplates.put("Charizard", charizard);

        // Squirtle (Michelangelo)
        PokemonTemplate squirtle = new PokemonTemplate(
            "Michelangelo", "Squirtle", 
            44, 48, 65, 50, 43, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, null
        );
        squirtle.addLearnableMove(1, createMove("Tail Whip"));
        squirtle.addLearnableMove(1, createMove("Tackle"));
        squirtle.addLearnableMove(8, createMove("Bubble"));
        squirtle.addLearnableMove(15, createMove("Water Gun"));
        squirtle.setEvolutionInfo("Wartortle", 16);
        pokemonTemplates.put("Squirtle", squirtle);

        // Wartortle (Walter)
        PokemonTemplate wartortle = new PokemonTemplate(
            "Walter", "Wartortle", 
            59, 63, 80, 65, 58, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, null
        );
        wartortle.addLearnableMove(1, createMove("Tail Whip"));
        wartortle.addLearnableMove(1, createMove("Tackle"));
        wartortle.addLearnableMove(1, createMove("Bubble"));
        wartortle.addLearnableMove(24, createMove("Bite"));
        wartortle.addLearnableMove(31, createMove("Withdraw"));
        wartortle.setEvolutionInfo("Blastoise", 36);
        pokemonTemplates.put("Wartortle", wartortle);

        // Blastoise (Blanco)
        PokemonTemplate blastoise = new PokemonTemplate(
            "Blanco", "Blastoise", 
            79, 83, 100, 85, 78, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, null
        );
        blastoise.addLearnableMove(1, createMove("Tail Whip"));
        blastoise.addLearnableMove(1, createMove("Tackle"));
        blastoise.addLearnableMove(1, createMove("Bubble"));
        blastoise.addLearnableMove(1, createMove("Bite"));
        blastoise.addLearnableMove(42, createMove("Hydro Pump"));
        pokemonTemplates.put("Blastoise", blastoise);

        // Darkrai (Dazio)
        PokemonTemplate darkrai = new PokemonTemplate(
            "Dazio", "Darkrai", 
            70, 90, 90, 135, 125, // HP, Atk, Def, Spc, Spd
            PokemonType.GHOST, null
        );
        darkrai.addLearnableMove(1, createMove("Night Shade"));
        darkrai.addLearnableMove(1, createMove("Confuse Ray"));
        darkrai.addLearnableMove(20, createMove("Dream Eater"));
        darkrai.addLearnableMove(40, createMove("Hypnosis"));
        pokemonTemplates.put("Darkrai", darkrai);

        // Shellos (Silvia)
        PokemonTemplate shellos = new PokemonTemplate(
            "Silvia", "Shellos", 
            76, 48, 48, 57, 34, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, null
        );
        shellos.addLearnableMove(1, createMove("Mud Slap"));
        shellos.addLearnableMove(5, createMove("Water Gun"));
        shellos.addLearnableMove(15, createMove("Mud Bomb"));
        shellos.setEvolutionInfo("Gastrodon", 30);
        pokemonTemplates.put("Shellos", shellos);

        // Gastrodon (Gustavo)
        PokemonTemplate gastrodon = new PokemonTemplate(
            "Gustavo", "Gastrodon", 
            111, 83, 68, 92, 39, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, PokemonType.GROUND
        );
        gastrodon.addLearnableMove(1, createMove("Mud Slap"));
        gastrodon.addLearnableMove(1, createMove("Water Gun"));
        gastrodon.addLearnableMove(1, createMove("Mud Bomb"));
        gastrodon.addLearnableMove(35, createMove("Water Pulse"));
        pokemonTemplates.put("Gastrodon", gastrodon);

        // Magikarp (Martina)
        PokemonTemplate magikarp = new PokemonTemplate(
            "Martina", "Magikarp", 
            20, 10, 55, 20, 80, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, null
        );
        magikarp.addLearnableMove(1, createMove("Splash"));
        magikarp.addLearnableMove(15, createMove("Tackle"));
        magikarp.setEvolutionInfo("Gyarados", 20);
        pokemonTemplates.put("Magikarp", magikarp);

        // Gyarados (Gianmarco)
        PokemonTemplate gyarados = new PokemonTemplate(
            "Gianmarco", "Gyarados", 
            95, 125, 79, 100, 81, // HP, Atk, Def, Spc, Spd
            PokemonType.WATER, PokemonType.FLYING
        );
        gyarados.addLearnableMove(1, createMove("Tackle"));
        gyarados.addLearnableMove(20, createMove("Bite"));
        gyarados.addLearnableMove(25, createMove("Dragon Rage"));
        gyarados.addLearnableMove(32, createMove("Hydro Pump"));
        pokemonTemplates.put("Gyarados", gyarados);

        // Ditto (Pongo)
        PokemonTemplate ditto = new PokemonTemplate(
            "Pongo", "Ditto", 
            48, 48, 48, 48, 48, // HP, Atk, Def, Spc, Spd
            PokemonType.NORMAL, null
        );
        ditto.addLearnableMove(1, createMove("Transform"));
        pokemonTemplates.put("Ditto", ditto);

        // Mew (Miù)
        PokemonTemplate mew = new PokemonTemplate(
            "Miù", "Mew", 
            100, 100, 100, 100, 100, // HP, Atk, Def, Spc, Spd
            PokemonType.PSYCHIC, null
        );
        mew.addLearnableMove(1, createMove("Pound"));
        mew.addLearnableMove(10, createMove("Transform"));
        mew.addLearnableMove(20, createMove("Psychic"));
        mew.addLearnableMove(40, createMove("Metronome"));
        pokemonTemplates.put("Mew", mew);

        // Mewtwo (Tony)
        PokemonTemplate mewtwo = new PokemonTemplate(
            "Tony", "Mewtwo", 
            106, 110, 90, 154, 130, // HP, Atk, Def, Spc, Spd
            PokemonType.PSYCHIC, null
        );
        mewtwo.addLearnableMove(1, createMove("Confusion"));
        mewtwo.addLearnableMove(20, createMove("Swift"));
        mewtwo.addLearnableMove(40, createMove("Psychic"));
        mewtwo.addLearnableMove(70, createMove("Recover"));
        pokemonTemplates.put("Mewtwo", mewtwo);
    }
    
    /**
     * Crea una mossa con i parametri specificati
     * Questo è un metodo di supporto per semplificare la creazione delle mosse
     * 
     * @param name Nome della mossa
     * @return Mossa creata
     */
    private static Move createMove(String name) {
        // Implementazione semplificata per le mosse di base
        switch (name) {
            case "Growl":
                return new Move("Growl", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 40, 
                               "Abbassa l'Attacco dell'avversario", Move.MoveEffect.ATTACK_DOWN, 100);
            case "Tackle":
                return new Move("Tackle", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, 
                               "Un attacco fisico di base");
            case "Scratch":
                return new Move("Scratch", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, 
                               "Graffia l'avversario con artigli affilati");
            case "Tail Whip":
                return new Move("Tail Whip", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 30, 
                               "Abbassa la Difesa dell'avversario", Move.MoveEffect.DEFENSE_DOWN, 100);
            case "Leech Seed":
                return new Move("Leech Seed", PokemonType.GRASS, Move.MoveCategory.STATUS, 0, 90, 10, 
                               "Pianta un seme che drena HP dall'avversario");
            case "Vine Whip":
                return new Move("Vine Whip", PokemonType.GRASS, Move.MoveCategory.PHYSICAL, 45, 100, 25, 
                               "Colpisce l'avversario con sottili viticci");
            case "Ember":
                return new Move("Ember", PokemonType.FIRE, Move.MoveCategory.SPECIAL, 40, 100, 25, 
                               "Attacca con piccole fiamme");
            case "Bubble":
                return new Move("Bubble", PokemonType.WATER, Move.MoveCategory.SPECIAL, 40, 100, 30, 
                               "Spara bolle che possono ridurre la Velocità", Move.MoveEffect.SPEED_DOWN, 10);
            case "Water Gun":
                return new Move("Water Gun", PokemonType.WATER, Move.MoveCategory.SPECIAL, 40, 100, 25, 
                               "Spara acqua ad alta pressione");
            case "Leer":
                return new Move("Leer", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 30, 
                               "Abbassa la Difesa dell'avversario", Move.MoveEffect.DEFENSE_DOWN, 100);
            case "Razor Leaf":
                return new Move("Razor Leaf", PokemonType.GRASS, Move.MoveCategory.PHYSICAL, 55, 95, 25, 
                               "Lancia foglie affilate con alta probabilità di colpo critico", Move.MoveEffect.HIGH_CRITICAL, 0);
            case "Sleep Powder":
                return new Move("Sleep Powder", PokemonType.GRASS, Move.MoveCategory.STATUS, 0, 75, 15, 
                               "Sparge una polvere che induce il sonno");
            case "Solar Beam":
                return new Move("Solar Beam", PokemonType.GRASS, Move.MoveCategory.SPECIAL, 120, 100, 10, 
                               "Potente raggio di energia solare");
            case "Rage":
                return new Move("Rage", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 20, 100, 20, 
                               "Aumenta l'Attacco quando subisce danni", Move.MoveEffect.ATTACK_UP, 100);
            case "Slash":
                return new Move("Slash", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 70, 100, 20, 
                               "Taglia con artigli con alta probabilità di colpo critico", Move.MoveEffect.HIGH_CRITICAL, 0);
            case "Fire Spin":
                return new Move("Fire Spin", PokemonType.FIRE, Move.MoveCategory.SPECIAL, 35, 85, 15, 
                               "Intrappola l'avversario in un vortice di fuoco");
            case "Bite":
                return new Move("Bite", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 60, 100, 25, 
                               "Morde con denti affilati");
            case "Withdraw":
                return new Move("Withdraw", PokemonType.WATER, Move.MoveCategory.STATUS, 0, 100, 40, 
                               "Si ritira nel guscio aumentando la Difesa", Move.MoveEffect.DEFENSE_UP, 100);
            case "Hydro Pump":
                return new Move("Hydro Pump", PokemonType.WATER, Move.MoveCategory.SPECIAL, 110, 80, 5, 
                               "Potente getto d'acqua ad alta pressione");
            case "Night Shade":
                return new Move("Night Shade", PokemonType.GHOST, Move.MoveCategory.SPECIAL, 0, 100, 15, 
                               "Infligge danni pari al livello dell'utilizzatore");
            case "Confuse Ray":
                return new Move("Confuse Ray", PokemonType.GHOST, Move.MoveCategory.STATUS, 0, 100, 10, 
                               "Raggio che confonde l'avversario");
            case "Dream Eater":
                return new Move("Dream Eater", PokemonType.PSYCHIC, Move.MoveCategory.SPECIAL, 100, 100, 15, 
                               "Assorbe HP da un avversario addormentato");
            case "Hypnosis":
                return new Move("Hypnosis", PokemonType.PSYCHIC, Move.MoveCategory.STATUS, 0, 60, 20, 
                               "Ipnotizza l'avversario facendolo addormentare");
            case "Mud Slap":
                return new Move("Mud Slap", PokemonType.GROUND, Move.MoveCategory.SPECIAL, 20, 100, 10, 
                               "Lancia fango che riduce la precisione", Move.MoveEffect.ACCURACY_DOWN, 100);
            case "Mud Bomb":
                return new Move("Mud Bomb", PokemonType.GROUND, Move.MoveCategory.SPECIAL, 65, 85, 10, 
                               "Lancia una bomba di fango che può ridurre la precisione", Move.MoveEffect.ACCURACY_DOWN, 30);
            case "Water Pulse":
                return new Move("Water Pulse", PokemonType.WATER, Move.MoveCategory.SPECIAL, 60, 100, 20, 
                               "Onde d'acqua che possono confondere l'avversario");
            case "Splash":
                return new Move("Splash", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 40, 
                               "Non ha alcun effetto");
            case "Dragon Rage":
                return new Move("Dragon Rage", PokemonType.DRAGON, Move.MoveCategory.SPECIAL, 0, 100, 10, 
                               "Infligge sempre 40 HP di danno");
            case "Transform":
                return new Move("Transform", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 10, 
                               "Si trasforma nel Pokémon avversario");
            case "Pound":
                return new Move("Pound", PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 35, 
                               "Colpisce con le zampe o la coda");
            case "Psychic":
                return new Move("Psychic", PokemonType.PSYCHIC, Move.MoveCategory.SPECIAL, 90, 100, 10, 
                               "Potente attacco psichico che può abbassare lo Speciale", Move.MoveEffect.SPECIAL_DOWN, 30);
            case "Metronome":
                return new Move("Metronome", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 10, 
                               "Usa una mossa casuale");
            case "Confusion":
                return new Move("Confusion", PokemonType.PSYCHIC, Move.MoveCategory.SPECIAL, 50, 100, 25, 
                               "Attacco psichico che può confondere");
            case "Swift":
                return new Move("Swift", PokemonType.NORMAL, Move.MoveCategory.SPECIAL, 60, 0, 20, 
                               "Lancia stelle che non mancano mai il bersaglio");
            case "Recover":
                return new Move("Recover", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 100, 10, 
                               "Recupera fino a metà dei propri HP massimi", Move.MoveEffect.HEAL, 0);
            case "Growth":
                return new Move("Growth", PokemonType.NORMAL, Move.MoveCategory.STATUS, 0, 0, 40,
                    "Aumenta l'Attacco Speciale", Move.MoveEffect.SPECIAL_UP, 100);
            default:
                // Mossa generica di fallback
                return new Move(name, PokemonType.NORMAL, Move.MoveCategory.PHYSICAL, 40, 100, 30, 
                               "Una mossa generica");
        }
    }
    
    /**
     * Crea un nuovo Pokémon della specie specificata al livello specificato
     * 
     * @param species Specie del Pokémon (es. "Bulbasaur", "Charmander")
     * @param level Livello del Pokémon
     * @return Nuovo Pokémon o null se la specie non esiste
     */
    public static Pokemon createPokemon(String species, int level) {
        PokemonTemplate template = pokemonTemplates.get(species);
        if (template == null) {
            return null;
        }
        
        return template.createPokemon(level);
    }
    
    /**
     * Ottiene la lista di tutte le specie di Pokémon disponibili
     * 
     * @return Lista di nomi delle specie
     */
    public static List<String> getAllSpecies() {
        return new ArrayList<>(pokemonTemplates.keySet());
    }
    
    /**
     * Classe interna che rappresenta un template per la creazione di Pokémon
     */
    private static class PokemonTemplate {
        private String name;
        private String species;
        private int baseHp;
        private int baseAttack;
        private int baseDefense;
        private int baseSpecial;
        private int baseSpeed;
        private PokemonType primaryType;
        private PokemonType secondaryType;
        private String evolvesTo;
        private int evolutionLevel;
        
        // Mappa che associa il livello alle mosse apprese a quel livello
        private Map<Integer, List<Move>> learnableMoves = new HashMap<>();
        
        /**
         * Costruttore per un template di Pokémon
         */
        public PokemonTemplate(String name, String species, int baseHp, int baseAttack, int baseDefense, 
                              int baseSpecial, int baseSpeed, PokemonType primaryType, PokemonType secondaryType) {
            this.name = name;
            this.species = species;
            this.baseHp = baseHp;
            this.baseAttack = baseAttack;
            this.baseDefense = baseDefense;
            this.baseSpecial = baseSpecial;
            this.baseSpeed = baseSpeed;
            this.primaryType = primaryType;
            this.secondaryType = secondaryType;
        }
        
        /**
         * Aggiunge una mossa apprendibile a un certo livello
         * 
         * @param level Livello a cui la mossa viene appresa
         * @param move Mossa da apprendere
         */
        public void addLearnableMove(int level, Move move) {
            if (!learnableMoves.containsKey(level)) {
                learnableMoves.put(level, new ArrayList<>());
            }
            learnableMoves.get(level).add(move);
        }
        
        /**
         * Imposta le informazioni sull'evoluzione del Pokémon
         * 
         * @param evolvesTo Nome della specie in cui evolve
         * @param evolutionLevel Livello a cui evolve
         */
        public void setEvolutionInfo(String evolvesTo, int evolutionLevel) {
            this.evolvesTo = evolvesTo;
            this.evolutionLevel = evolutionLevel;
        }
        
        /**
         * Crea un nuovo Pokémon basato su questo template al livello specificato
         * 
         * @param level Livello del Pokémon
         * @return Nuovo Pokémon
         */
        public Pokemon createPokemon(int level) {
            Pokemon pokemon = new Pokemon(name, species, level, baseHp, baseAttack, baseDefense, 
                                        baseSpecial, baseSpeed, primaryType, secondaryType);
            
            // Imposta le informazioni sull'evoluzione
            if (evolvesTo != null) {
                pokemon.setEvolutionInfo(evolvesTo, evolutionLevel);
            }
            
            // Aggiunge le mosse apprendibili fino al livello specificato
            for (int i = 1; i <= level; i++) {
                if (learnableMoves.containsKey(i)) {
                    for (Move move : learnableMoves.get(i)) {
                        // Se il Pokémon ha già 4 mosse, sostituisce la prima
                        if (!pokemon.addMove(move) && !pokemon.getMoves().isEmpty()) {
                            pokemon.replaceMove(0, move);
                        }
                    }
                }
            }
            
            return pokemon;
        }
    }
}