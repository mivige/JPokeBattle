package com.pokemon.core.stats;

public class Stats {
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    
    public Stats(int hp, int attack, int defense, 
                int specialAttack, int specialDefense, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }
    
    // Getters
    public int getHP() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpecialAttack() { return specialAttack; }
    public int getSpecialDefense() { return specialDefense; }
    public int getSpeed() { return speed; }
}