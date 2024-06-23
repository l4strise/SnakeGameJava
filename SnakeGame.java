package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {

    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    private final int DELAY = 100;

    private int[] snakeX, snakeY;
    private int snakeLength;
    private int appleX, appleY;
    private int dx, dy;
    private boolean running;

    public SnakeGame() {
        snakeX = new int[WIDTH / 10];
        snakeY = new int[WIDTH / 10];
        snakeLength = 3;
        dx = dy = 10;
        running = true;

        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = WIDTH / 2 - i * 10;
            snakeY[i] = HEIGHT / 2;
        }

        generateApple();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (dy != 10) {
                            dy = -10;
                            dx = 0;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (dy != -10) {
                            dy = 10;
                            dx = 0;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (dx != 10) {
                            dx = -10;
                            dy = 0;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (dx != -10) {
                            dx = 10;
                            dy = 0;
                        }
                        break;
                }
            }
        });

        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateApple() {
        appleX = new Random().nextInt(WIDTH / 10) * 10;
        appleY = new Random().nextInt(HEIGHT / 10) * 10;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) {
            return;
        }

        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        snakeX[0] += dx;
        snakeY[0] += dy;

        if (snakeX[0] < 0 || snakeX[0] >= WIDTH || snakeY[0] < 0 || snakeY[0] >= HEIGHT) {
            running = false;
        }

        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            snakeLength++;
            generateApple();
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.RED);
        for (int i = 0; i < snakeLength; i++) {
            g.fillRect(snakeX[i], snakeY[i], 10, 10);
        }

        g.setColor(Color.GREEN);
        g.fillRect(appleX, appleY, 10, 10);

        if (!running) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", WIDTH / 2 - 50, HEIGHT / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SnakeGame());
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}