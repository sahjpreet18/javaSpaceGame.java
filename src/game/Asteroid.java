package game;

import city.cs.engine.*;

public class Asteroid extends DynamicBody {

    private static final float MIN_SIZE = 0.5f;
    private static final float MAX_SIZE = 2.5f;

    public Asteroid(World world) {
        super(world, makeShape());
        this.rotate((float)(Math.random() * Math.PI * 2));
    }

    private static Shape makeShape() {
        float size = (float)(Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE);
        return new CircleShape(size);
    }
}
