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


