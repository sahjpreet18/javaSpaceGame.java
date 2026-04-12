package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameView extends UserView implements MouseListener{

    private Player player;
    private Image background;
    private Rectangle menuButton = new Rectangle(0, 0, 60, 30);

    private boolean menuOpen = false;
    private JPanel menuPanel;
    private JPanel gameOverPanel;
    private JTextField nameField;
    private boolean scoreSaved = false;

    private GameWorld world;

    public GameView(World world, Player player, int width, int height) {
        super(world, width, height);
        this.player = player;
        this.world = (GameWorld) world;
        addMouseListener(this);
        setupMenuPanel();
        setupGameOverPanel();
        // Gets the background from the data file
        this.background = new ImageIcon("data/Space_Background.png").getImage();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (menuButton.contains(e.getPoint())) {
            if (menuOpen) {
                closeMenu();
            }
            else {
                openMenu();
            }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

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
        int x = 160;
        int y = 20;
        // Label for the stamina bar
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("STAMINA", x, y - 3);
        // Background bar
        g.setColor(new Color(64, 64, 64, 150));
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
        g.setColor(new Color(64, 64, 64, 150));
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
        g.setColor(new Color(64, 64, 64, 150));
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

        // --- POINTS, LIVES, BULLETS, DIFFICULTY ---
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("POINTS:" + player.getPoints()+ "  LIVES:" + player.getLives(), 20, 580 );
        g.drawString("BULLETS: " + player.getBullets(), 160, 60);
        g.drawString("DIFFICULTY: " + world.getDifficulty(), 390, 580);


        // --- MENU SYSTEM ---
        // Create the button in the top right
        menuButton.x = 20;
        menuButton.y = 20;

        g.setColor(new Color(0, 0, 0, 150)); // semi‑transparent background
        g.fillRect(menuButton.x, menuButton.y, menuButton.width, menuButton.height);

        g.setColor(Color.white);
        g.drawRect(menuButton.x, menuButton.y, menuButton.width, menuButton.height);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("MENU", menuButton.x + 10, menuButton.y + 20);

        // --- GAME OVER PANEL ---
        if (world.isGameOver()) {
            gameOverPanel.setVisible(true);
        }

    }

    private void setupMenuPanel() { // Setup for the menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1, 10, 10));
        menuPanel.setBackground(new Color(0, 0, 0, 180));
        menuPanel.setBounds(200, 150, 200, 200);

        JButton resume = new JButton("Resume");
        JButton restart = new JButton("Restart");
        JButton quit = new JButton("Quit");
        String[] difficulties = {"Easy", "Normal", "Hard"};
        JComboBox<String> difficultyBox = new JComboBox<>(difficulties);
        difficultyBox.setSelectedItem("Normal"); // The default is normal
        JButton highScores = new JButton("High Scores");

        resume.addActionListener(e -> closeMenu()); // Closing the menu resumes the game
        restart.addActionListener(e -> restartGame()); // Restarting the game
        quit.addActionListener(e -> System.exit(0)); // To quit the game
        difficultyBox.addActionListener(e -> { // Drop down to pick difficulty
                    String choice = (String) difficultyBox.getSelectedItem();
                    switch (choice) {
                        case "Easy" -> world.setDifficulty(GameWorld.Difficulty.EASY);
                        case "Normal" -> world.setDifficulty(GameWorld.Difficulty.NORMAL);
                        case "Hard" -> world.setDifficulty(GameWorld.Difficulty.HARD);
                    }
                });
        highScores.addActionListener(e -> showHighScores());

        menuPanel.add(resume);
        menuPanel.add(restart);
        menuPanel.add(quit);
        menuPanel.add(difficultyBox);
        menuPanel.add(highScores);

        menuPanel.setVisible(false);
        this.setLayout(null);
        this.add(menuPanel);
    }

    private void openMenu() {
        menuOpen = true;
        world.setPaused(true);
        menuPanel.setVisible(true);
    }

    private void closeMenu() {
        menuOpen = false;
        world.setPaused(false);
        menuPanel.setVisible(false);
    }

    private void restartGame() {
        SwingUtilities.getWindowAncestor(this).dispose(); // restarts the game by resetting it all
        new Game();
    }

    private void showHighScores() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"))) { // reads the text file
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) { // if there are no scores in the file
            sb.append("No scores yet!");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "High Scores", JOptionPane.PLAIN_MESSAGE);
    }

    private void setupGameOverPanel() { // Setup for the game over panel
        gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridLayout(4, 1, 10, 10));
        gameOverPanel.setBackground(new Color(0, 0, 0, 180));
        gameOverPanel.setBounds(200, 150, 300, 250);

        JLabel over = new JLabel("GAME OVER", SwingConstants.CENTER);
        over.setForeground(Color.white);
        over.setFont(new Font("Arial", Font.BOLD, 24));

        nameField = new JTextField("Enter your name"); // Name input

        JButton save = new JButton("Save Score"); // Can save the score for the scoreboard
        JButton restart = new JButton("Restart");
        JButton quit = new JButton("Quit");

        save.addActionListener(e -> saveScore());
        restart.addActionListener(e -> restartGame());
        quit.addActionListener(e -> System.exit(0));

        gameOverPanel.add(over);
        gameOverPanel.add(nameField);
        gameOverPanel.add(save);
        gameOverPanel.add(restart);

        gameOverPanel.setVisible(false);
        this.setLayout(null);
        this.add(gameOverPanel);
    }

    private void saveScore() { // prevents the user from submitting one score multiple times
        if (scoreSaved) {
            return;
        }

        String name = nameField.getText();
        int score = player.getPoints();

        try (FileWriter fw = new FileWriter("highscores.txt", true)) { // Saves scores to text file
            fw.write(name + " - " + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoreSaved = true;
    }
}