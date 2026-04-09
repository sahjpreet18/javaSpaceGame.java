package game;

import city.cs.engine.UserView;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {

    private Player player;

    private Image background;

    public GameView(World world, Player player, int width, int height) {
        super(world, width, height);
        this.player = player;
        // Gets the background from the data file
        this.background = new ImageIcon("data/Space_Background.png").getImage();
    }

    @Override
    protected void paintBackground(Graphics2D graphics) {
        // Adds the background onto the world
        graphics.drawImage(background, 0, 0, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {

        // --- STAMINA BAR ---
        float stamina = PlayerController.getStamina();
        float max = 3000;
        int barWidth = 200;
        int barHeight = 20;
        int x = 20;
        int y = 20;
        // Label for the stamina bar
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("STAMINA", x, y - 3);
        // Background bar
        g.setColor(Color.darkGray);
        g.fillRect(x, y, barWidth, barHeight);
        // Blue bar for filled part
        float percent = stamina / max;
        g.setColor(Color.blue);
        g.fillRect(x, y, (int)(barWidth * percent), barHeight);
        // Border
        g.setColor(Color.black);
        g.drawRect(x, y, barWidth, barHeight);

        // --- X-SPEED BAR ---
        float xSpeed;
        Vec2 speed = player.getLinearVelocity();
        if (speed.x >= 0) {
            xSpeed = Math.min(speed.x, PlayerController.getMaxSpeed());
        } else {
            xSpeed = Math.max(speed.x, -PlayerController.getMaxSpeed());
        }
        float maxSpeed = PlayerController.getMaxSpeed();
        x = 380;
        int half = barWidth / 2;

        // Label for the x-speed bar
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("X-SPEED", x, y - 3);
        // Background bar
        g.setColor(Color.darkGray);
        g.fillRect(x, y, barWidth, barHeight);
        // Fill amount based on speed
        percent = xSpeed / maxSpeed;
        // X-Speed bar fill
        g.setColor(Color.green);
        int fill = (int)(half * Math.abs(percent));
        if (percent >= 0) {
            g.fillRect(x + half, y, fill, barHeight);
        } else {
            g.fillRect(x + half - fill, y, fill, barHeight);
        }
        // Border
        g.setColor(Color.black);
        g.drawRect(x, y, barWidth, barHeight);

        // --- Y-SPEED BAR ---
        float ySpeed;
        if (speed.y >= 0) {
            ySpeed = Math.min(speed.y, PlayerController.getMaxSpeed());
        } else {
            ySpeed = Math.max(speed.y, -PlayerController.getMaxSpeed());
        }
        y = 60;

        // Label for the y-speed bar
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Y-SPEED", x, y - 3);
        // Background bar
        g.setColor(Color.darkGray);
        g.fillRect(x, y, barWidth, barHeight);
        // Fill amount based on speed
        percent = ySpeed / maxSpeed;
        // X-Speed bar fill
        g.setColor(Color.green);
        fill = (int)(half * Math.abs(percent));
        if (percent >= 0) {
            g.fillRect(x + half, y, fill, barHeight);
        } else {
            g.fillRect(x + half - fill, y, fill, barHeight);
        }
        // Border
        g.setColor(Color.black);
        g.drawRect(x, y, barWidth, barHeight);
    }
}