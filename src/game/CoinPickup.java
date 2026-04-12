package game;

import city.cs.engine.*;

public class CoinPickup implements SensorListener {

    private Coin coin;

    public CoinPickup(Coin coin) {
        this.coin = coin;
    }

    @Override
    public void beginContact(SensorEvent e) {
        // If the coin and player collide then the respawnCoin method is called
        if (e.getContactBody() instanceof Player) {
            coin.respawnCoin();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
    }
}
