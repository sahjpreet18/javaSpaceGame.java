package game;

import city.cs.engine.*;

public class AsteroidCollision implements CollisionListener {

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player) {
            Player player = (Player)e.getOtherBody();
            player.loseLife();
            e.getReportingBody().destroy();
        }
    }
}

