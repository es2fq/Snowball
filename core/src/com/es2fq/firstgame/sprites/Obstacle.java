package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.es2fq.firstgame.*;

/**
 * Created by es2fq on 1/4/2017.
 */

public class Obstacle {
    private Vector3 position;

    private Texture texture;
    private Animation textureAnimation;

    private Rectangle bounds;

    private boolean passed;
    private boolean destroyed;
    private int size;

    public Obstacle(float x, float y) {
        texture = new Texture("fence.png");
        textureAnimation = new Animation(new TextureRegion(texture), 1, 1f);

        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());

        passed = false;
        destroyed = false;
        size = 2;
    }

    public void update(float dt) {
        if (textureAnimation != null) {
            textureAnimation.update(dt);
        }
    }

    public void reposition(float x, int snowCount) {
        if (snowCount > 1) {
            texture = new Texture("building.png");
            textureAnimation = new Animation(new TextureRegion(texture), 1, 1f);
            bounds.setWidth(texture.getWidth());
            bounds.setHeight(texture.getHeight());

            destroyed = false;
            size = 4;
        }

        position.set(x, position.y, 0);
        bounds.setPosition(position.x, position.y);
    }

    public void destroy() {
        if (destroyed)
            return;

        destroyed = true;

        if (size == 2) {
            texture = new Texture("fencedestroy.png");
            textureAnimation = new Animation(new TextureRegion(texture), 4, 0.1f);
        }
        if (size == 4) {
            texture = new Texture("buildingdestroy.png");
            textureAnimation = new Animation(new TextureRegion(texture), 4, 0.1f);
        }
        textureAnimation.setMaxCycles(1);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public TextureRegion getTexture() {
        return textureAnimation.getFrame();
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
