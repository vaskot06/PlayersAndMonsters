package models.players;

import common.ConstantMessages;
import models.players.interfaces.Player;
import repositories.interfaces.CardRepository;

public abstract class BasePlayer implements Player {
    private String username;
    private int health;
    private CardRepository cardRepository;
    private boolean isDead;


    BasePlayer(CardRepository cardRepository, String username, int health) {
        this.cardRepository = cardRepository;
        this.setUsername(username);
        this.setHealth(health);
        this.setDead(false);
    }

    @Override
    public void takeDamage(int damagePoints) {

        if (damagePoints < 0) {
            throw new IllegalArgumentException("Damage points cannot be less than zero.");
        }

        this.health -=damagePoints;

        if (this.health <= 0) {
            this.health = 0;
            isDead = true;
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    private void setUsername(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Player's username cannot be null or an empty string.");
        }

        this.username = username;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {

        if (health < 0) {
            throw new IllegalArgumentException("Player's health bonus cannot be less than zero.");
        }

        this.health = health;
    }

    @Override
    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    private void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public String toString() {

        StringBuilder sb =new StringBuilder();

        sb
                .append( String.format(ConstantMessages.PLAYER_REPORT_INFO, this.getUsername(), this.getHealth(), this.getCardRepository().getCount()))
                .append(System.lineSeparator());

        this.getCardRepository().getCards().forEach(c->sb.append(c.toString()).append(System.lineSeparator()));
        sb.append(ConstantMessages.DEFAULT_REPORT_SEPARATOR);
        return sb.toString().trim();
    }
}
