package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class GameWorld extends World{

    private Player player;

    private Coin coin1;
    private Coin coin2;

    public GameWorld() {
        // Adding the player into the world
        this.player = new Player(this);
        this.player.setPosition(new Vec2(0,0));

        // Adding the first coin into the world into a random location
        this.coin1 = new Coin(this);
        this.coin1.respawnCoin();

        // Adding the second coin into the world into a random location
        this.coin2 = new Coin(this);
        this.coin2.respawnCoin();

        //Listens for collisions between the coins and the player
        coin1.addCollisionListener(new CoinPickup(coin1));
        coin2.addCollisionListener(new CoinPickup(coin2));
    }

    public Player getPlayer() {
        return this.player;
    }
}
