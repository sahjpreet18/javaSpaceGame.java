package game;

import city.cs.engine.*;

public class Asteroid extends DynamicBody {

    private static final float MIN_SIZE = 0.5f;
    private static final float MAX_SIZE = 2.5f;

    public Asteroid(World world) {
        float CircleSize = (float)(Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE);;
        super(world, new CircleShape(CircleSize));
        addImage(new BodyImage("data/asteroid.png", CircleSize*2));
        this.rotate((float)(Math.random() * Math.PI * 2)); // rotates the asteroid randomly
    }
}
