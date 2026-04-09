package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

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

    public float getSpeed() { // Gets the speed of the player
        // --- FORWARD SPEED CALCULATION ---
        float angle = this.getAngle();
        float fx = (float) Math.cos(angle);
        float fy = (float) Math.sin(angle);
        Vec2 vel = this.getLinearVelocity();
        float forwardSpeed = vel.x * fx + vel.y * fy;
        forwardSpeed = Math.min(forwardSpeed, PlayerController.getMaxSpeed()); // 5. Clamp above max speed
        if (forwardSpeed < 0) { // Ignores backwards drift
            forwardSpeed = 0;
        };
        return forwardSpeed;
    }
}
