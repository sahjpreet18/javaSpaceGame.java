package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Coin extends StaticBody {

    private static final Shape coinShape = new CircleShape(0.5f);

    public Coin(World world) {
        super(world);

        // Creates an invisible sensor for the coin
        Sensor sensor = new Sensor(this, coinShape);
        sensor.addSensorListener(new CoinPickup(this));

        // Visible image placed on top of the sensor
        addImage(new BodyImage("data/coin.png", 1.0f));
    }

    public void respawnCoin() {

        // Spawns the coin in a random position within the borders
        float x = ((float) Math.random() * 29f) - 14.5f;
        float y = ((float) Math.random() * 25f) - 14.5f;
        this.setPosition(new Vec2(x, y));
        System.out.println(x + " " + y);
    }
}
