package spacealien;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

abstract class GameEntity {
    protected int x;
    protected int y;
    protected boolean isActive;
    
    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.isActive = true;
    }
    
    abstract void update();
    abstract void render(GraphicsContext gc);
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}

public class FXMLDocumentController {

    private static final Random RAND = new Random();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 60;
    private final Image PLAYER_IMG = new Image(getClass().getResource("/Assets/player.png").toExternalForm());
    private final Image EXPLOSION_IMG = new Image(getClass().getResource("/Assets/explosion.png").toExternalForm());
    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_H = 128;
    static final int EXPLOSION_STEPS = 15;

    private final Image BOMBS_IMG[] = {
            new Image(getClass().getResource("/Assets/1.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/2.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/3.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/4.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/5.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/6.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/7.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/8.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/9.png").toExternalForm()),
            new Image(getClass().getResource("/Assets/10.png").toExternalForm()),
    };

    final int MAX_BOMBS = 10, MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    private GraphicsContext gc;

    Rocket player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> Bombs;

    private double mouseX;
    private int score;
    
    @FXML
    private Canvas gameCanvas;

    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        gameCanvas.setOnMouseMoved(e -> mouseX = e.getX());
        gameCanvas.setOnMouseClicked(this::onMouseClicked);
        gameCanvas.setOnKeyPressed(this::handleKeyboardInput);
        setup();
        gameCanvas.setFocusTraversable(true);
    }


    private void onMouseClicked(MouseEvent e) {
        if (shots.size() < MAX_SHOTS) shots.add(player.shoot());
        if (gameOver) {
            gameOver = false;
            setup();
        }
    }

public abstract class GameEntity {
    protected int x;
    protected int y;
    protected boolean isActive;

    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.isActive = true;
    }
        abstract void update();
    abstract void render(GraphicsContext gc);

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}

public class Rocket {
    int posX, posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionStep = 0;

    public Rocket(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        img = image;
    }

    public Shot shoot() {
        return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
    }

    public void update() {
        if (exploding) explosionStep++;
        destroyed = explosionStep > FXMLDocumentController.EXPLOSION_STEPS;
    }

    public void draw(GraphicsContext gc) {
        if (exploding) {
            gc.drawImage(FXMLDocumentController.EXPLOSION_IMG, 
                explosionStep % FXMLDocumentController.EXPLOSION_COL * FXMLDocumentController.EXPLOSION_W, 
                (explosionStep / FXMLDocumentController.EXPLOSION_ROWS) * FXMLDocumentController.EXPLOSION_H + 1,
                FXMLDocumentController.EXPLOSION_W, FXMLDocumentController.EXPLOSION_H,
                posX, posY, size, size);
        } else {
            gc.drawImage(img, posX, posY, size, size);
        }
    }

        public boolean colide(GameEntity other) {
        int d = distance(this.posX + size / 2, this.posY + size / 2,
                other.getX() + other.size / 2, other.getY() + other.size / 2);
        return d < other.size / 2 + this.size / 2;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}

public class Shot {
    public boolean toRemove;
    int posX, posY, speed = 10;
    static final int size = 6;

    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        posY -= speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(posX, posY, size, size);
    }
}




public class Universe extends GameEntity {
    private int h, w, r, g, b;
    private double opacity;
    private static final Random RAND = new Random();

    public Universe() {
        super(RAND.nextInt(FXMLDocumentController.WIDTH), 0);
        w = RAND.nextInt(5) + 1;
        h = RAND.nextInt(5) + 1;
        r = RAND.nextInt(100) + 150;
        g = RAND.nextInt(100) + 150;
        b = RAND.nextInt(100) + 150;
        opacity = RAND.nextFloat();
        if (opacity < 0) opacity *= -1;
        if (opacity > 0.5) opacity = 0.5;
    }


    @Override
    void update() {
        if (opacity > 0.8) opacity -= 0.01;
        if (opacity < 0.1) opacity += 0.01;
        y += 20;
        if (y > FXMLDocumentController.HEIGHT) {
            setActive(false);
        }
    }

    @Override
    void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(x, y, w, h);
    }
}

