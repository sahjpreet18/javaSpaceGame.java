package game;

import city.cs.engine.*;

public class BulletPickup implements SensorListener {

    private Bullet bullet;

    public BulletPickup(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public void beginContact(SensorEvent e) { // Checks for contact
        if (e.getContactBody() instanceof Player player) {
            player.addBullet();
            bullet.destroy();
        }
    }

    @Override
    public void endContact(SensorEvent e) {}
}

