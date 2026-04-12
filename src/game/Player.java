package game;

import city.cs.engine.*;

public class Player extends DynamicBody {
    private static final Shape playerShape = new PolygonShape(
            -0.75f, 0.55f,
            -0.75f, -0.5f,
            -0.5f, -0.7f,
            0.75f, -0.45f,
            1.15f, -0.1f,
            1.15f, 0.15f,
            0.75f, 0.5f,
            -0.5f, 0.75f);
    private final BodyImage rocketStill = new BodyImage("data/Rocket_Still.png", 1.5f);
    private final BodyImage rocketMoving = new BodyImage("data/Rocket_Moving.png", 1.5f);

    private int points;

    private int lives;

    public Player(World world) {
        super(world, playerShape);
        addImage(rocketStill);
        System.out.println(getImages());

        // Makes it so that the player is not affected by gravity
        super.setGravityScale(0);

        this.points = 0;
        this.lives = 3;
    }

    public void loseLife() {
        lives--;
        System.out.println("Lives left: " + lives);

        if (lives <= 0) {
            System.out.println("GAME OVER");
        }
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
