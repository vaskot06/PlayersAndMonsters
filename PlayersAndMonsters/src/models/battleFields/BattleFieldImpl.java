package models.battleFields;

import models.battleFields.interfaces.Battlefield;
import models.cards.interfaces.Card;
import models.players.Beginner;
import models.players.interfaces.Player;

import java.util.DuplicateFormatFlagsException;

public class BattleFieldImpl implements Battlefield {
    @Override
    public void fight(Player attackPlayer, Player enemyPlayer) {

        if (attackPlayer.isDead() || enemyPlayer.isDead()) {
            throw new IllegalArgumentException("Player is dead!");
        }

        preFightPreparation(attackPlayer);
        preFightPreparation(enemyPlayer);


//
//        if (attackPlayer.getClass().getSimpleName().equals("Beginner")) {
//            attackPlayer.setHealth(attackPlayer.getHealth() + 40);
//
//            for (Card card : attackPlayer.getCardRepository().getCards()) {
//                card.setDamagePoints(30);
//            }
//        }
//
//        if (enemyPlayer.getClass().getSimpleName().equals("Beginner")) {
//            enemyPlayer.setHealth(enemyPlayer.getHealth() + 40);
//
//            for (Card card : enemyPlayer.getCardRepository().getCards()) {
//                card.setDamagePoints(30);
//            }
//        }

        getHealthPointsFromDeck(attackPlayer);
        getHealthPointsFromDeck(enemyPlayer);

        processFight(attackPlayer, enemyPlayer);

    }


    private void processFight(Player attackPlayer, Player enemyPlayer) {

        while (true) {
        int attackDamagePoints = 0;
        int enemyDamagePoints = 0;

        for (Card card : attackPlayer.getCardRepository().getCards()) {
            attackDamagePoints += card.getDamagePoints();
        }
        enemyPlayer.takeDamage(attackDamagePoints);

        if (enemyPlayer.isDead()) {
            return;
        }

        for (Card card : enemyPlayer.getCardRepository().getCards()) {
            enemyDamagePoints += card.getDamagePoints();
        }

        attackPlayer.takeDamage(enemyDamagePoints);

        if (attackPlayer.isDead()) {
            return;
        }
    }

//            int attackDamagePoints = attackPlayer
//                    .getCardRepository()
//                    .getCards()
//                    .stream()
//                    .mapToInt(Card::getDamagePoints)
//                    .sum();
//
//            enemyPlayer.takeDamage(attackDamagePoints);
//
//            if (enemyPlayer.isDead()) {
//                return;
//            }
//
//            int enemyDamagePoints = enemyPlayer
//                    .getCardRepository()
//                    .getCards()
//                    .stream()
//                    .mapToInt(Card::getDamagePoints)
//                    .sum();
//
//            attackPlayer.takeDamage(enemyDamagePoints);
//
//            if (attackPlayer.isDead()) {
//                return;
//            }

    }

    private void getHealthPointsFromDeck(Player player) {
        int healthPoints = player.getCardRepository()
                .getCards()
                .stream()
                .mapToInt(Card::getHealthPoints)
                .sum();

        player.setHealth(player.getHealth() + healthPoints);
    }

    private void preFightPreparation(Player player) {
        if (!Beginner.class.getSimpleName().equals(player.getClass().getSimpleName())) {
            return;
        }

        player.setHealth(player.getHealth() + 40);
        player.getCardRepository().getCards().forEach(c -> c.setDamagePoints(c.getDamagePoints() + 30));
    }
}