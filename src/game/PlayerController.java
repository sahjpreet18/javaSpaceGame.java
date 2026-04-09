package game;

import org.jbox2d.common.Vec2;
import city.cs.engine.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener, StepListener {

    private static final float ROTATION_SPEED = 4;
    private static final float THRUST = 8;
    private static final float BOOST = 6;
    private static final float MAX_SPEED = 20;
    private static float stamina = 3000;
    private static final float staminaRecover = 2;
    private static final float staminaUse = 5+staminaRecover;
    private static float acceleration;

    private final Player player;

    private boolean leftDown = false;
    private boolean rightDown = false;
    private boolean upDown = false;
    private boolean shiftDown = false;

    public PlayerController(Player player, World world) {
        this.player = player;
        world.addStepListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Checks if a key is being pressed and turns the appropriate boolean variable true in response
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                leftDown = true;
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                rightDown = true;
                break;
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                upDown = true;
                break;
            case KeyEvent.VK_SHIFT:
                shiftDown = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Checks if a key has been released and turns the appropriate boolean false in response
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                leftDown = false;
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                rightDown = false;
                break;
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                upDown = false;
                break;
            case KeyEvent.VK_SHIFT:
                shiftDown = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void preStep(StepEvent e) {
        // Stops the player from spinning out of control after hitting an object
        player.setAngularVelocity(0);

        if (leftDown) {
            // Rotates the player anticlockwise
            player.rotateDegrees(ROTATION_SPEED);
        }
        if (rightDown) {
            // Rotates the player clockwise
            player.rotateDegrees(-ROTATION_SPEED);
        }
        if (upDown) {
            applyThrust();
        }

        recoverStamina();

        friction();

        capSpeed();

        wrapAround();
    }

    @Override
    public void postStep(StepEvent e) {}

    private void friction() {
        player.setLinearVelocity(player.getLinearVelocity().mul(0.99f));
    }

    private void applyThrust() {
        if (shiftDown && stamina > 0) {
            // Checks if the player is trying to boost and if they have stamina to apply it
            acceleration = THRUST + BOOST;
            stamina -= staminaUse;
        } else {
            acceleration = THRUST;
        }
        // Finds the players angle and uses trigonometry to apply the correct force to both the x and y axis
        float angle = player.getAngle();
        float x = (float) Math.cos(angle);
        float y = (float) Math.sin(angle);
        player.applyForce(new Vec2(x * acceleration, y * acceleration));

    }

    private void recoverStamina() {
        if (stamina <= 3000) {
            stamina += staminaRecover;
        }
    }

    public static float getStamina() {
        return stamina;
    }

    private void capSpeed() {
        Vec2 v = player.getLinearVelocity();
        float speed = v.length();

        if (speed > MAX_SPEED) {
            Vec2 limited = v.mul(MAX_SPEED / speed);
            player.setLinearVelocity(limited);
        }
    }

    private void wrapAround() {
        Vec2 pos = player.getPosition();

        float limit = 15f;
        float buffer = 0.75f;
        float x = pos.x;
        float y = pos.y;

        if (x > limit + buffer) {
            x = -(limit + buffer);
        } else if (x < -limit - buffer){
            x = limit + buffer;
        }
        if (y > limit + buffer) {
            y = -(limit + buffer);
        }
        if (y < -limit - buffer){
            y = limit + buffer;
        }

        player.setPosition(new Vec2(x, y));
    }

    public static float getMaxSpeed() {
        return MAX_SPEED;
    }
}