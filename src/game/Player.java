package game;

import city.cs.engine.*;

public class Player extends DynamicBody {
    private static final Shape playerShape = new PolygonShape( // Creates the shape for the player rocket
            -0.75f, 0.55f,
            -0.75f, -0.5f,
            -0.5f, -0.7f,
            0.75f, -0.45f,
            1.15f, -0.1f,
            1.15f, 0.15f,
            0.75f, 0.5f,
            -0.5f, 0.75f);
    private final BodyImage rocketStill = new BodyImage("data/Rocket_Still.png", 1.5f);
    private final BodyImage rocketMoving = new BodyImage("data/Rocket_Moving.png", 1.5f); // The two images for the rocket

    private int points;

    private int lives;

    public Player(World world) {
        super(world, playerShape);
        addImage(rocketStill);

        this.points = 0;
        this.lives = 3;
    }

    public void addPoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    public void loseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public BodyImage getRocketStill() {
        return rocketStill;
    }

    public BodyImage getRocketMoving() {
        return rocketMoving;
    }
}
