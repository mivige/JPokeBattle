package it.pokebattle.battle;

import it.pokebattle.model.Pokemon;
import it.pokebattle.model.Move;
import java.util.List;

public interface AIStrategy {
    /**
     * Sceglie la mossa da usare per l'NPC.
     * @param self Il Pokémon controllato dall'IA
     * @param opponent Il Pokémon avversario
     * @param availableMoves Le mosse disponibili (con PP > 0)
     * @return L'indice della mossa scelta nella lista availableMoves
     */
    int chooseMove(Pokemon self, Pokemon opponent, List<Move> availableMoves);

    /**
     * Decide se cambiare Pokémon e restituisce l'indice del Pokémon da mandare in campo,
     * oppure -1 per non cambiare.
     */
    default int chooseSwitch(Pokemon self, Pokemon opponent, List<Pokemon> team) {
        return -1; // Default: non cambia mai
    }
}
