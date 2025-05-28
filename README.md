# JPokeBattle

A Pokémon battle clone implemented in Java with custom graphics and battle mechanics.

**🚀 Built for:** *Programming Methods – Sapienza University of Rome*  
📚 **Course Year:** 2024/25  

---

## About

JPokeBattle is a Java implementation of the classic Pokémon battle system. It features custom Pokémon names, evolution mechanics, turn-based battles, and a save system.

## Getting Started

### Playing the Game

If you just want to play the game on Windows without worrying about dependencies:

1. Go to the [Releases](https://github.com/mivige/JPokeBattle/releases) section on GitHub
2. Download the latest `.exe` file
3. Run the executable - no Java installation required (the executable includes a packaged JVM created with jpackage)

### Using the JAR File

If you prefer to use the JAR file (works on any platform with Java installed):

1. Download the JPokeBattle.jar file
2. Run it with: java -jar JPokeBattle.jar

### Building from Source

If you want to build the project yourself:

```
git clone https://github.com/mivige/JPokeBattle.git
cd JPokeBattle
javac src/JPokeBattle.java
java -cp src JPokeBattle
```

## Features

- Turn-based battle system similar to the original Pokémon games
- Custom Pokémon with Italian-inspired names
- Evolution system
- Experience and leveling mechanics
- Move learning system
- Game state saving and loading
- Leaderboard tracking

## Premade Save File

The included pokebattle_save.dat contains:
- A Charmander (Carmine) just 1 experience point away from evolving
- An Ivysaur (Ignazio) 1 experience point away from unlocking its fifth move

This premade save allows you to quickly experience the evolution and move-learning mechanics of the game.

## Custom Pokémon Names

JPokeBattle features custom Italian-inspired names for the Pokémon:

| Original    | JPokeBattle Name |
| ----------- | ---------------- |
| Bulbasaur   | Bulbo            |
| Ivysaur     | Ignazio          |
| Venusaur    | Vincenzo         |
| Charmander  | Carmine          |
| Charmeleon  | Carmelo          |
| Charizard   | Caruso           |
| Squirtle    | Michelangelo     |
| Warturtle   | Walter           |
| Blastoise   | Blanco           |
| Magikarp    | Martina          |
| Gyarados    | Gianmarco        |
| Ditto       | Pongo            |
| Mew         | Miù              |
| Mewtwo      | Tony             |

## Credits

- All illustrations used in this project are created by [@BlackInkWeaver](https://drive.google.com/drive/folders/1FJKf4OkBoxlVyKWVBWs-OPnSvEmgNXrY)
- Pokémon is a registered trademark of Nintendo, Game Freak, and Creatures Inc.
- This is a fan project for educational purposes only

## License

See the LICENSE file for details.