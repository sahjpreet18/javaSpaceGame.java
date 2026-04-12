package game;

import city.cs.engine.*;

public class AsteroidCollision implements CollisionListener {

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player) { // if an asteroid collides with the player it disappears
            Player player = (Player) e.getOtherBody();
            player.loseLife(); // the player loses a life
            e.getReportingBody().destroy();
        }
    }
}
