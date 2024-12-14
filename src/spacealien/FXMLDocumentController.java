package spacealien;

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


