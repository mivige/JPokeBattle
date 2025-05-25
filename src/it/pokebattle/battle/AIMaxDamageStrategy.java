package it.pokebattle.battle;

import it.pokebattle.model.Pokemon;
import it.pokebattle.model.Move;
import it.pokebattle.model.PokemonType;
import java.util.List;

public class AIMaxDamageStrategy implements AIStrategy {
    @Override
    public int chooseMove(Pokemon self, Pokemon opponent, List<Move> availableMoves) {
        if (availableMoves.isEmpty()) {
            return -1; // Nessuna mossa disponibile
        }
        int bestIndex = 0;
        double bestScore = Double.NEGATIVE_INFINITY;
        int koIndex = -1;

        for (int i = 0; i < availableMoves.size(); i++) {
            Move move = availableMoves.get(i);

            // Evita mosse di stato già attive
            if (move.getEffect() == Move.MoveEffect.ATTACK_DOWN && opponent.getAttack() < opponent.getBaseAttack() * 0.7)
                continue;
            if (move.getEffect() == Move.MoveEffect.DEFENSE_DOWN && opponent.getDefense() < opponent.getBaseDefense() * 0.7)
                continue;
            if (move.getEffect() == Move.MoveEffect.SPECIAL_DOWN && opponent.getSpecial() < opponent.getBaseSpecial() * 0.7)
                continue;
            if (move.getEffect() == Move.MoveEffect.SPEED_DOWN && opponent.getSpeed() < opponent.getBaseSpeed() * 0.7)
                continue;
            if (move.getEffect() == Move.MoveEffect.ATTACK_UP && self.getAttack() > self.getBaseAttack() * 1.5)
                continue;
            if (move.getEffect() == Move.MoveEffect.DEFENSE_UP && self.getDefense() > self.getBaseDefense() * 1.5)
                continue;
            if (move.getEffect() == Move.MoveEffect.SPECIAL_UP && self.getSpecial() > self.getBaseSpecial() * 1.5)
                continue;

            // Risparmia mosse forti se l'avversario ha pochi HP
            if (move.getPower() > 80 && opponent.getCurrentHp() < opponent.getMaxHp() * 0.2)
                continue;

            // Calcola il punteggio della mossa (potenza * efficacia)
            double effectiveness = PokemonType.getTypeEffectiveness(
                move.getType(), opponent.getPrimaryType(), opponent.getSecondaryType());
            double score = move.getPower() * effectiveness;
            // Focus sul KO: se può mandare KO, la usa sempre
            if (move.getPower() > 0 && score >= opponent.getCurrentHp()) {
                koIndex = i;
            }

            // Boost/debuff: se il Pokémon è in svantaggio, preferisce buff/debuff
            if (move.getEffect() == Move.MoveEffect.DEFENSE_UP && self.getCurrentHp() < self.getMaxHp() * 0.4)
                score += 30;
            if (move.getEffect() == Move.MoveEffect.ATTACK_DOWN && opponent.getAttack() > opponent.getBaseAttack())
                score += 20;

            // Preferisce mosse con più PP se il danno stimato è simile
            score += move.getCurrentPp() * 0.1;

            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        if (koIndex != -1) return koIndex;
        return bestIndex;
    }

    @Override
    public int chooseSwitch(Pokemon self, Pokemon opponent, List<Pokemon> team) {
        boolean lowHp = self.getCurrentHp() < self.getMaxHp() * 0.3;
        double weakness = it.pokebattle.model.PokemonType.getTypeEffectiveness(
            opponent.getPrimaryType(), self.getPrimaryType(), self.getSecondaryType());
        boolean weakToOpponent = weakness > 1.5;

        if (lowHp || weakToOpponent) {
            for (int i = 0; i < team.size(); i++) {
                Pokemon p = team.get(i);
                if (!p.isKO() && p != self) {
                    // Cerca un Pokémon con resistenza o vantaggio di tipo
                    double eff = it.pokebattle.model.PokemonType.getTypeEffectiveness(
                        p.getPrimaryType(), opponent.getPrimaryType(), opponent.getSecondaryType());
                    if (eff > 1.0 || p.getCurrentHp() > p.getMaxHp() * 0.5) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}