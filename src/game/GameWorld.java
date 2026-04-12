package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.HashMap;
import java.util.Map;

public class GameWorld extends World implements StepListener {

    private Player player;
    private Coin coin1;
    private Coin coin2;

    private boolean paused = false;
    private Map<Body, Vec2> savedVelocities = new HashMap<>();
    private Map<Body, Float> savedAngular = new HashMap<>();

    int asteroidTimer = 0;
    int bulletTimer = 0;

    public enum Difficulty {
        EASY,
        NORMAL,
        HARD
    }
    private Difficulty difficulty = Difficulty.NORMAL; // The base difficulty is normal

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
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;

        if (!paused) {
            for (DynamicBody b : getDynamicBodies()) { // restore velocities
                if (savedVelocities.containsKey(b)) {
                    b.setLinearVelocity(savedVelocities.get(b));
                    b.setAngularVelocity(savedAngular.get(b));
                }
            }

            savedVelocities.clear();
            savedAngular.clear();
        }
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
        float speedMultiplier = switch (difficulty) { // Changes the speeds based on difficulty
            case EASY -> 0.7f;
            case NORMAL -> 1.0f;
            case HARD -> 1.5f;
        };
        a.setLinearVelocity(direction.mul(speed * speedMultiplier));

        // collision listener
        a.addCollisionListener(new AsteroidCollision());
    }

    public void setDifficulty(Difficulty d) {
        this.difficulty = d;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void preStep(StepEvent e) {
        if (paused) { // freezes the game
            if (savedVelocities.isEmpty()) { // Saves the velocities of all bodies to resume them
                for (DynamicBody b : getDynamicBodies()) {
                    savedVelocities.put(b, b.getLinearVelocity());
                    savedAngular.put(b, b.getAngularVelocity());
                }
            }

            for (DynamicBody b : getDynamicBodies()) { // freeze all the bodies
                b.setLinearVelocity(new Vec2(0,0));
                b.setAngularVelocity(0);
            }
            return;
        }

        asteroidTimer++;
        int spawnThreshold = switch (difficulty) { // changes how fast asteroids spawn based on the difficulty
            case EASY -> 300;   // slower spawns
            case NORMAL -> 200;
            case HARD -> 100;   // faster spawns
        };
        if (asteroidTimer > spawnThreshold) { // spawns asteroids at regular intervals
            spawnAsteroid();
            asteroidTimer = 0;
        }

        bulletTimer++;
        if (bulletTimer > 200) {
            if (Math.random()<0.003) {
                new Bullet(this).spawn();
                bulletTimer = 0;
            }
        }
    }

    @Override
    public void postStep (StepEvent e){}

}
