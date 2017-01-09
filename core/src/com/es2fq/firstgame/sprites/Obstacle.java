package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by es2fq on 1/4/2017.
 */

public class Obstacle {
    private Vector3 position;

    private Texture texture;

    private Rectangle bounds;

    private boolean passed;
    private int size;

    public Obstacle(float x, float y) {
        texture = new Texture("fence.png");

        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());

        passed = false;
        size = 2;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void reposition(float x, int score) {
        if (score > 1) {
            texture = new Texture("building.png");
            bounds.setWidth(texture.getWidth());
            bounds.setHeight(texture.getHeight());
        }

        position.set(x, position.y, 0);
        bounds.setPosition(position.x, position.y);
    }

    public void destroy() {

    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean bool) {
        passed = bool;
    }

    public int getSize() {
        return size;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position = new Vector3(x, y, z);
    }
}
