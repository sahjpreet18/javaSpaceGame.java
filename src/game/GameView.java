package game;

import city.cs.engine.UserView;
import city.cs.engine.World;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {

    private Image background;

    public GameView(World world, int width, int height) {
        super(world, width, height);
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

        // Stamina bar
        float stamina = PlayerController.getStamina();
        float max = 3000;
        int barWidth = 300;
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
    }
}