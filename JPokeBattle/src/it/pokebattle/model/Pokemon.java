package it.pokebattle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe che rappresenta un Pokémon nel gioco.
 * Contiene tutte le informazioni relative alle statistiche, mosse e caratteristiche del Pokémon.
 */
public class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name; // Nome del Pokémon nel gioco
    private String species; // Specie del Pokémon (es. Bulbasaur, Charmander)
    private int level; // Livello attuale del Pokémon
    private int experience; // Esperienza accumulata
    private int experienceToNextLevel; // Esperienza necessaria per salire di livello
    
    // Statistiche base
    private int baseHp;
    private int baseAttack;
    private int baseDefense;
    private int baseSpecial;
    private int baseSpeed;
    
    // Statistiche attuali (influenzate da IV, EV e livello)
    private int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private int special;
    private int speed;
    
    // IV (Individual Values) - valori casuali che rendono unico ogni Pokémon
    private int ivHp;
    private int ivAttack;
    private int ivDefense;
    private int ivSpecial;
    private int ivSpeed;
    
    // EV (Effort Values) - valori che aumentano con l'allenamento
    private int evHp;
    private int evAttack;
    private int evDefense;
    private int evSpecial;
    private int evSpeed;
    
    // Tipo del Pokémon
    private PokemonType primaryType;
    private PokemonType secondaryType;
    
    // Mosse conosciute dal Pokémon (massimo 4)
    private List<Move> moves;
    
    // Informazioni sull'evoluzione
    private String evolvesTo; // Nome della specie in cui evolve
    private int evolutionLevel; // Livello a cui evolve
    
    /**
     * Costruttore per creare un nuovo Pokémon
     * 
     * @param name Nome del Pokémon nel gioco
     * @param species Specie del Pokémon
     * @param level Livello iniziale
     * @param baseHp HP base
     * @param baseAttack Attacco base
     * @param baseDefense Difesa base
     * @param baseSpecial Speciale base
     * @param baseSpeed Velocità base
     * @param primaryType Tipo primario
     * @param secondaryType Tipo secondario (può essere null)
     */
    public Pokemon(String name, String species, int level, int baseHp, int baseAttack, int baseDefense, 
                  int baseSpecial, int baseSpeed, PokemonType primaryType, PokemonType secondaryType) {
        this.name = name;
        this.species = species;
        this.level = level;
        this.experience = 0;
        this.experienceToNextLevel = calculateExperienceToNextLevel();
        
        this.baseHp = baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpecial = baseSpecial;
        this.baseSpeed = baseSpeed;
        
        // Genera valori IV casuali (0-15 nella prima generazione)
        this.ivHp = (int) (Math.random() * 16);
        this.ivAttack = (int) (Math.random() * 16);
        this.ivDefense = (int) (Math.random() * 16);
        this.ivSpecial = (int) (Math.random() * 16);
        this.ivSpeed = (int) (Math.random() * 16);
        
        // Inizializza EV a 0
        this.evHp = 0;
        this.evAttack = 0;
        this.evDefense = 0;
        this.evSpecial = 0;
        this.evSpeed = 0;
        
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        
        this.moves = new ArrayList<>(4); // Massimo 4 mosse
        
        // Calcola le statistiche effettive in base a livello, IV e EV
        calculateStats();
        
        // Imposta HP attuali al massimo
        this.currentHp = this.maxHp;
    }
    
    /**
     * Calcola le statistiche del Pokémon in base a statistiche base, IV, EV e livello
     * Utilizza le formule della prima generazione
     */
    public void calculateStats() {
        // Formula HP: ((Base + IV) * 2 + sqrt(EV)/4) * Level/100 + Level + 10
        this.maxHp = (int) (((baseHp + ivHp) * 2 + Math.sqrt(evHp) / 4) * level / 100 + level + 10);
        
        // Formula altre statistiche: ((Base + IV) * 2 + sqrt(EV)/4) * Level/100 + 5
        this.attack = (int) (((baseAttack + ivAttack) * 2 + Math.sqrt(evAttack) / 4) * level / 100 + 5);
        this.defense = (int) (((baseDefense + ivDefense) * 2 + Math.sqrt(evDefense) / 4) * level / 100 + 5);
        this.special = (int) (((baseSpecial + ivSpecial) * 2 + Math.sqrt(evSpecial) / 4) * level / 100 + 5);
        this.speed = (int) (((baseSpeed + ivSpeed) * 2 + Math.sqrt(evSpeed) / 4) * level / 100 + 5);
    }
    
    /**
     * Calcola l'esperienza necessaria per salire al livello successivo
     * @return Esperienza necessaria
     */
    private int calculateExperienceToNextLevel() {
        // Formula semplificata: level^3
        return (int) Math.pow(level + 1, 3);
    }
    
    /**
     * Aggiunge esperienza al Pokémon e gestisce l'aumento di livello
     * @param exp Esperienza da aggiungere
     * @return true se il Pokémon è salito di livello, false altrimenti
     */
    public boolean addExperience(int exp) {
        if (exp < 0) {
            exp = 0; // Non aggiungere esperienza negativa
        }

        this.experience += exp;
        boolean leveledUp = false;

        // Controlla se il Pokémon sale di livello
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
            leveledUp = true;
        }

        // Dopo eventuali level up, controlla se può evolversi
        if (canEvolve()) {
            evolve();
        }

        return leveledUp;
    }
    
    /**
     * Aumenta il livello del Pokémon e aggiorna le statistiche
     */
    private void levelUp() {
        this.level++;
        this.experienceToNextLevel = calculateExperienceToNextLevel();
        calculateStats();
        this.currentHp = this.maxHp; // Ripristina HP al massimo dopo il level up

        // Controlla se ci sono mosse da apprendere a questo livello
        List<Move> newMoves = PokemonFactory.getLearnableMovesForLevel(this.species, this.level);
        for (Move move : newMoves) {
            this.addMove(move);
        }
    }
    
    /**
     * Aggiunge una mossa al Pokémon
     * @param move Mossa da aggiungere
     * @return true se la mossa è stata aggiunta, false se già presente o se non è possibile aggiungerla
     */
    public boolean addMove(Move move) {
        // Non aggiungere se già presente una mossa con lo stesso nome
        for (Move m : moves) {
            if (m.getName().equals(move.getName())) {
                return false;
            }
        }
        if (moves.size() < 4) {
            moves.add(move);
            return true;
        } else {
            // Chiedi al giocatore se vuole apprendere la nuova mossa
            String[] options = new String[5];
            for (int i = 0; i < 4; i++) {
                options[i] = "Dimentica " + moves.get(i).getName();
            }
            options[4] = "Non imparare " + move.getName();

            int choice = javax.swing.JOptionPane.showOptionDialog(
                null,
                "Vuoi che " + name + " impari la mossa " + move.getName() + "?\nScegli una mossa da dimenticare:",
                "Nuova mossa",
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[4]
            );

            if (choice >= 0 && choice < 4) {
                moves.set(choice, move);
                return true;
            }
            // Se l'utente sceglie "Non imparare", non cambia nulla
            return false;
        }
    }
    
    /**
     * Sostituisce una mossa esistente con una nuova
     * @param oldMoveIndex Indice della mossa da sostituire
     * @param newMove Nuova mossa
     * @return true se la sostituzione è avvenuta con successo
     */
    public boolean replaceMove(int oldMoveIndex, Move newMove) {
        if (oldMoveIndex >= 0 && oldMoveIndex < moves.size()) {
            moves.set(oldMoveIndex, newMove);
            return true;
        }
        return false;
    }
    
    /**
     * Applica danno al Pokémon
     * @param damage Quantità di danno da applicare
     * @return true se il Pokémon è ancora in grado di combattere, false se è KO
     */
    public boolean takeDamage(int damage) {
        this.currentHp = Math.max(0, this.currentHp - damage);
        return this.currentHp > 0;
    }
    
    /**
     * Ripristina gli HP del Pokémon
     * @param amount Quantità di HP da ripristinare
     */
    public void heal(int amount) {
        this.currentHp = Math.min(this.maxHp, this.currentHp + amount);
    }
    
    /**
     * Ripristina completamente gli HP del Pokémon
     */
    public void fullHeal() {
        this.currentHp = this.maxHp;
    }
    
    /**
     * Controlla se il Pokémon può evolversi al livello attuale
     * @return true se il Pokémon può evolversi
     */
    public boolean canEvolve() {
        return evolvesTo != null && level >= evolutionLevel;
    }
    
    /**
     * Imposta le informazioni sull'evoluzione del Pokémon
     * @param evolvesTo Nome della specie in cui evolve
     * @param evolutionLevel Livello a cui evolve
     */
    public void setEvolutionInfo(String evolvesTo, int evolutionLevel) {
        this.evolvesTo = evolvesTo;
        this.evolutionLevel = evolutionLevel;
    }
    
    /**
     * Ripristina tutti i PP delle mosse del Pokémon
     */
    public void restoreAllPP() {
        for (Move move : moves) {
            move.restorePP();
        }
    }
    
    // Getters e setters
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getBaseHp() {
        return baseHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public int getSpecial() {
        return special;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public PokemonType getPrimaryType() {
        return primaryType;
    }
    
    public PokemonType getSecondaryType() {
        return secondaryType;
    }
    
    public List<Move> getMoves() {
        return new ArrayList<>(moves); // Restituisce una copia per evitare modifiche esterne
    }
    
    public boolean isKO() {
        return currentHp <= 0;
    }
    
    public String getEvolvesTo() {
        return evolvesTo;
    }
    
    public int getEvolutionLevel() {
        return evolutionLevel;
    }
    
    /**
     * Aggiunge EV al Pokémon dopo una battaglia
     * @param stat Statistica a cui aggiungere EV
     * @param amount Quantità di EV da aggiungere
     */
    public void addEV(Stat stat, int amount) {
        switch (stat) {
            case HP:
                this.evHp += amount;
                break;
            case ATTACK:
                this.evAttack += amount;
                break;
            case DEFENSE:
                this.evDefense += amount;
                break;
            case SPECIAL:
                this.evSpecial += amount;
                break;
            case SPEED:
                this.evSpeed += amount;
                break;
        }
        // Ricalcola le statistiche dopo l'aggiunta di EV
        calculateStats();
    }
    
    /**
     * Enum per le statistiche del Pokémon
     */
    public enum Stat {
        HP, ATTACK, DEFENSE, SPECIAL, SPEED
    }

    public String getExperience() {
        return String.valueOf(experience);
    }

    public String getExperienceToNextLevel() {
        return String.valueOf(experienceToNextLevel);
    }

    public void setExperience(String experience) {
        this.experience = Integer.parseInt(experience);
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public void setFainted(boolean fainted) {
        if (fainted) {
            this.currentHp = 0;
        }
        else {
            this.currentHp = this.maxHp;
        }
    }

    public int getBaseAttack() { 
        return baseAttack; 
    }

    public int getBaseDefense() { 
        return baseDefense; 
    }

    public int getBaseSpecial() { 
        return baseSpecial; 
    }
   
    public int getBaseSpeed() { 
        return baseSpeed; 
    }

    public void setMoves(List<Move> moves) {
        this.moves = new ArrayList<>(moves);
    }

    // Metodo di evoluzione
    public void evolve() {
        if (canEvolve()) {
            // Salva i dati pre-evoluzione per la notifica
            String oldName = this.name;
            String oldSpecies = this.species;

            // Ottieni il nuovo Pokémon evoluto tramite la factory
            Pokemon evolved = PokemonFactory.createPokemon(evolvesTo, level);
            // Copia le informazioni rilevanti
            this.name = evolved.name;
            this.species = evolved.species;
            this.baseHp = evolved.baseHp;
            this.baseAttack = evolved.baseAttack;
            this.baseDefense = evolved.baseDefense;
            this.baseSpecial = evolved.baseSpecial;
            this.baseSpeed = evolved.baseSpeed;
            this.primaryType = evolved.primaryType;
            this.secondaryType = evolved.secondaryType;
            this.evolvesTo = evolved.evolvesTo;
            this.evolutionLevel = evolved.evolutionLevel;
            this.moves = evolved.getMoves();
            calculateStats();
            this.currentHp = this.maxHp;

            // Notifica la UI tramite BattleListener (se in battaglia)
            it.pokebattle.battle.Battle currentBattle = it.pokebattle.game.GameState.getInstance().getCurrentBattle();
            if (currentBattle != null) {
                for (it.pokebattle.battle.BattleListener listener : currentBattle.getListeners()) {
                    listener.onPokemonEvolved(
                        new Pokemon(oldName, oldSpecies, level, baseHp, baseAttack, baseDefense, baseSpecial, baseSpeed, primaryType, secondaryType),
                        this
                    );
                }
            }
        }
    }

    public void increaseStat(Stat stat, int amount) {
        switch (stat) {
            case HP:
                this.maxHp += amount;
                this.currentHp += amount;
                break;
            case ATTACK:
                this.attack += amount;
                break;
            case DEFENSE:
                this.defense += amount;
                break;
            case SPECIAL:
                this.special += amount;
                break;
            case SPEED:
                this.speed += amount;
                break;
        }
    }

    public void decreaseStat(Stat stat, int amount) {
        switch (stat) {
            case HP:
                this.maxHp -= amount;
                this.currentHp -= amount;
                if (this.maxHp < 0) {
                    this.maxHp = 0; // Assicurati che il max HP non sia negativo
                }
                if (this.currentHp < 0) {
                    this.currentHp = 0; // Assicurati che gli HP non siano negativi
                }
                break;
            case ATTACK:
                this.attack -= amount;
                if (this.attack < 0) {
                    this.attack = 0; // Assicurati che l'attacco non sia negativo
                }
                break;
            case DEFENSE:
                this.defense -= amount;
                if (this.defense < 0) {
                    this.defense = 0; // Assicurati che la difesa non sia negativa
                }
                break;
            case SPECIAL:
                this.special -= amount;
                if (this.special < 0) {
                    this.special = 0; // Assicurati che la speciale non sia negativa
                }
                break;
            case SPEED:
                this.speed -= amount;
                if (this.speed < 0) {
                    this.speed = 0; // Assicurati che la velocità non sia negativa
                }
                break;
        }
    }

    public void healSomeHp() {
        int healAmount = this.maxHp / 2;
        this.currentHp += healAmount;
        if (this.currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        }
    }
}