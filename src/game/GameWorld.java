package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class GameWorld extends World implements StepListener {

    private Player player;

    private Coin coin1;
    private Coin coin2;

    public GameWorld() {
        this.addStepListener(this);
        this.setGravity(0);

        // Adding the player into the world
        this.player = new Player(this);
        this.player.setPosition(new Vec2(0, 0));

        // Adding the first coin into the world into a random location
        this.coin1 = new Coin(this);
        this.coin1.respawnCoin();

        // Adding the second coin into the world into a random location
        this.coin2 = new Coin(this);
        this.coin2.respawnCoin();

        //Listens for collisions between the coins and the player
        //coin1.addCollisionListener(new CoinPickup(coin1));
        //coin2.addCollisionListener(new CoinPickup(coin2));
    }

    public Player getPlayer() {
        return this.player;
    }

    public void spawnAsteroid() {
        Asteroid a = new Asteroid(this);

        float spawnDist = 17f; // just outside visible world
        float range = 15f;     // visible world
        float x, y;

        int side = (int) (Math.random() * 4);

        switch (side) {
            case 0: // left
                x = -spawnDist;
                y = (float)(Math.random() * (range * 2) - range);
                break;
            case 1: // right
                x = spawnDist;
                y = (float)(Math.random() * (range * 2) - range);
                break;
            case 2: // top
                x = (float)(Math.random() * (range * 2) - range);
                y = spawnDist;
                break;
            default: // bottom
                x = (float)(Math.random() * (range * 2) - range);
                y = -spawnDist;
                break;
        }

        a.setPosition(new Vec2(x, y));
        Vec2 direction = new Vec2(-x, -y); // makes asteroids go towards the centre
        direction.normalize();
        // random speed between 3 and 8
        float speed = (float)(Math.random() * 5 + 3);

        a.setLinearVelocity(direction.mul(speed));

        // collision listener
        a.addCollisionListener(new AsteroidCollision());
    }

    private int asteroidTimer = 0;

    @Override
    public void preStep(StepEvent e) {
        asteroidTimer++;

        if (asteroidTimer > 200) {
            spawnAsteroid();
            asteroidTimer = 0;
        }
    }

    @Override
    public void postStep (StepEvent e){}

}
