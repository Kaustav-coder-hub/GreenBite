import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100;

    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 1;
    private int applesEaten = 0;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;

    private JButton tryAgainButton;
    private JButton exitButton;

    private boolean paused = false;

    private Clip backgroundMusicClip;
    private Clip eatAppleClip;
    private Clip gameOverClip;

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

        tryAgainButton = new JButton("Try Again");
        exitButton = new JButton("Exit");

        tryAgainButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));

        this.setLayout(null);
        tryAgainButton.setBounds(SCREEN_WIDTH / 2 - 75, (SCREEN_HEIGHT / 2 + 50) + 25, 150, 40);
        exitButton.setBounds(SCREEN_WIDTH / 2 - 75, (SCREEN_HEIGHT / 2 + 50) + 75, 150, 40);
        this.add(tryAgainButton);
        this.add(exitButton);

        tryAgainButton.setVisible(false);
        exitButton.setVisible(false);

        loadSounds();
        playBackgroundMusic();
    }

    private void loadSounds() {
        try {
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(AudioSystem.getAudioInputStream(new File("E:\\JAVA SNAKE-GAME\\SNAKE-GAME JAVA\\src\\sounds\\background_music.wav")));

            eatAppleClip = AudioSystem.getClip();
            eatAppleClip.open(AudioSystem.getAudioInputStream(new File("E:\\JAVA SNAKE-GAME\\SNAKE-GAME JAVA\\src\\sounds\\eat_apple.wav")));

            gameOverClip = AudioSystem.getClip();
            gameOverClip.open(AudioSystem.getAudioInputStream(new File("E:\\JAVA SNAKE-GAME\\SNAKE-GAME JAVA\\src\\sounds\\game_over.wav")));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private void playEatAppleSound() {
        stopBackgroundMusic(); // Changed
        if (eatAppleClip != null) {
            eatAppleClip.stop(); // Changed
            eatAppleClip.setFramePosition(0);
            eatAppleClip.start();
            eatAppleClip.addLineListener(event -> { // Changed
                if (!eatAppleClip.isRunning()) { // Changed
                    playBackgroundMusic(); // Changed
                }
            });
        }
    }

    private void playGameOverSound() {
        stopBackgroundMusic(); // Changed
        if (gameOverClip != null) {
            gameOverClip.stop(); // Changed
            gameOverClip.setFramePosition(0);
            gameOverClip.start();
            gameOverClip.addLineListener(event -> { // Changed
                if (!gameOverClip.isRunning()) { // Changed
                    playBackgroundMusic(); // Changed
                }
            });
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
        }
    }

    public void startGame() {
        playBackgroundMusic();
        x[0] = SCREEN_WIDTH / 2;
        y[0] = SCREEN_HEIGHT / 2;
        direction = 'U';
        bodyParts = 3;
        applesEaten = 0;

        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void restartGame() {
        stopBackgroundMusic();
        playBackgroundMusic();
        x[0] = SCREEN_WIDTH / 2;
        y[0] = SCREEN_HEIGHT / 2;
        direction = 'U';
        bodyParts = 3;
        applesEaten = 0;

        tryAgainButton.setVisible(false);
        exitButton.setVisible(false);

        newApple();
        running = true;
        timer.start();
        repaint();
    }

    public void togglePause() {
        paused = !paused;
        if (paused) {
            timer.stop();
        } else {
            timer.start();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

            if (paused) {
                g.setColor(Color.white);
                g.setFont(new Font("Ink Free", Font.BOLD, 75));
                FontMetrics metricsPause = getFontMetrics(g.getFont());
                g.drawString("Paused", (SCREEN_WIDTH - metricsPause.stringWidth("Paused")) / 2, SCREEN_HEIGHT / 2);
            }
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

        for (int i = 0; i < bodyParts; i++) {
            if (x[i] == appleX && y[i] == appleY) {
                newApple();
            }
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
            playEatAppleSound();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        tryAgainButton.setVisible(true);
        exitButton.setVisible(true);
        playGameOverSound();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    togglePause();
                    break;
            }
        }
    }
}
