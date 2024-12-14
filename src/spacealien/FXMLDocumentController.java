package spacealien;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

