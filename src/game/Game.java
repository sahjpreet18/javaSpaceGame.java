package game;

import city.cs.engine.*;

import javax.swing.*;

public class Game {

    // The world where the player and other objects will be in
    private GameWorld world;

    // The view of the world that allows us to see everything
    private GameView view;

    public Game() {
        // Creating the world
        GameWorld world = new GameWorld();

        // Creating the view
        view = new GameView(world, 600, 600);
        view.setZoom(20);

        // Creating the controller for the player
        PlayerController controller = new PlayerController(world.getPlayer(), world);
        view.addKeyListener(controller);
        view.setFocusable(true);
        view.requestFocus();

        // Created the frame for the game
        final JFrame frame = new JFrame("Project Game");
        frame.add(view);
        // Closes the game when the 'x' is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // Prevents the view from being resized
        frame.setResizable(false);
        // Fits the frame to the view
        frame.pack();
        // Makes it visible
        frame.setVisible(true);

        world.start();
    }


    public static void main(String[] args) {

        new Game();
    }
}