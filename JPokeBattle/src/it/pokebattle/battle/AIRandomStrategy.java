package it.pokebattle.battle;

import it.pokebattle.model.Pokemon;
import it.pokebattle.model.Move;
import java.util.List;
import java.util.Random;

public class AIRandomStrategy implements AIStrategy {
    private final Random random = new Random();

    @Override
    public int chooseMove(Pokemon self, Pokemon opponent, List<Move> availableMoves) {
        if (availableMoves.isEmpty()) {
            return -1; // Nessuna mossa disponibile
        }
        return random.nextInt(availableMoves.size());
    }

    @Override
    public int chooseSwitch(Pokemon self, Pokemon opponent, List<Pokemon> team) {
        if (self.getCurrentHp() < self.getMaxHp() * 0.2) {
            for (int i = 0; i < team.size(); i++) {
                Pokemon p = team.get(i);
                if (!p.isKO() && p != self) {
                    return i;
                }
            }
        }
        return -1;
    }
}