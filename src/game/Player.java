package game;

import city.cs.engine.*;

public class Player extends DynamicBody {
    private static final Shape playerShape = new PolygonShape(
            0.75f, 0f,
            -0.75f, 0.5f,
            -0.75f, -0.5f);

    private int points;

    private int lives;

    public Player(World world) {
        super(world, playerShape);

        // Makes it so that the player is not affected by gravity
        super.setGravityScale(0);

        this.points = 0;
        this.lives = 3;
    }
}
