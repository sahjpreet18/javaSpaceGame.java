package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Projectile extends DynamicBody {

    private static final Shape shape = new CircleShape(0.2f);

    public Projectile(World world, Vec2 position, float angle) {
        super(world, shape);

        addImage(new BodyImage("data/Bullet.png", 0.5f)); // Uses the bullet image
        setPosition(position);

        float speed = 25f; // applies the speed and direction
        float x = (float)Math.cos(angle);
        float y = (float)Math.sin(angle);

        setLinearVelocity(new Vec2(x * speed, y * speed));
    }
}