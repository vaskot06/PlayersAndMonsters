package core;

import common.ConstantMessages;
import core.interfaces.ManagerController;
import models.battleFields.BattleFieldImpl;
import models.battleFields.interfaces.Battlefield;
import models.cards.MagicCard;
import models.cards.TrapCard;
import models.cards.interfaces.Card;
import models.players.Advanced;
import models.players.Beginner;
import models.players.interfaces.Player;
import repositories.CardRepositoryImpl;
import repositories.PlayerRepositoryImpl;
import repositories.interfaces.CardRepository;
import repositories.interfaces.PlayerRepository;


public class ManagerControllerImpl implements ManagerController {
    private PlayerRepository playerRepository;
    private CardRepository cardRepository;
    private Battlefield battlefield;

    public ManagerControllerImpl() {
        this.playerRepository = new PlayerRepositoryImpl();
        this.cardRepository = new CardRepositoryImpl();
        this.battlefield = new BattleFieldImpl();
    }

    @Override
    public String addPlayer(String type, String username) {
        Player player = null;

        if (Beginner.class.getSimpleName().equals(type)) {
            player = new Beginner(new CardRepositoryImpl(), username);
        } else if (Advanced.class.getSimpleName().equals(type)) {
            player = new Advanced(new CardRepositoryImpl(), username);
        }

        this.playerRepository.add(player);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER, type, username);
    }



    @Override
    public String addCard(String type, String name) {
        Card card = null;

        if (type.equals("Magic")) {
            card = new MagicCard(name);
        } else if (type.equals("Trap")){
            card = new TrapCard(name);
        }

        this.cardRepository.add(card);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CARD, type, name);
    }

    @Override
    public String addPlayerCard(String username, String cardName) {

        Card cardToAdd = this.cardRepository.find(cardName);
        Player player = this.playerRepository.find(username);

        player.getCardRepository().add(cardToAdd);


        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_WITH_CARDS, cardName, username);
    }

    @Override
    public String fight(String attackUser, String enemyUser) {

        Player attackerPlayer = this.playerRepository.find(attackUser);
        Player enemyPlayer = this.playerRepository.find(enemyUser);

        this.battlefield.fight(attackerPlayer, enemyPlayer);

        return String.format(ConstantMessages.FIGHT_INFO, attackerPlayer.getHealth(), enemyPlayer.getHealth());
    }

    @Override
    public String report() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Player player : this.playerRepository.getPlayers()) {
            stringBuilder.append(player.toString())
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}
