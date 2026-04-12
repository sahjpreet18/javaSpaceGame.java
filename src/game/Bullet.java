package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Bullet extends StaticBody {

    private static final Shape shape = new CircleShape(0.5f);

    public Bullet(World world) {
        super(world);
        Sensor sensor = new Sensor(this, shape); // Creates a sensor for the bullet item
        sensor.addSensorListener(new BulletPickup(this));
        addImage(new BodyImage("data/Bullet.png", 1.0f)); // Adds the image to bullet
    }

    public void spawn() { // Spawns it in a random location
        float x = ((float)Math.random() * 29f) - 14.5f;
        float y = ((float)Math.random() * 25f) - 14.5f;
        setPosition(new Vec2(x, y));
    }
}
