package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.es2fq.firstgame.*;

/**
 * Created by es2fq on 1/4/2017.
 */

public class Snowball {
    private static final float MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private static final float JUMP = 350;

    private Vector3 position;
    private Vector3 velocity;

    private Texture texture;
    private Animation textureAnimation;

    private Rectangle bounds;

    private int groundPosition;
    private boolean landed;
    private int snowCount;
    private float sizeX;
    private float sizeY;
    private float extraSpeed;
    private float scale;

    public Snowball(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        groundPosition = y;
        landed = true;
        snowCount = 10;
        extraSpeed = 0;

        int numFrames = 4;
        texture = new Texture("rolling.png");
        textureAnimation = new Animation(new TextureRegion(texture), numFrames, 0.5f);

//        sizeX = texture.getWidth() / numFrames / 2;
//        sizeY = texture.getHeight() / 2;

        sizeX = 10;
        sizeY = 10;

        scale = 0.1f;

        bounds = new Rectangle(x, y, sizeX, sizeY);
    }

    public void update(float dt) {
        textureAnimation.update(dt);

        if (position.y > groundPosition) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);

        position.add((MOVEMENT + extraSpeed) * dt, velocity.y, 0);

        if (position.y < groundPosition) {
            position.y = groundPosition;
            landed = true;
        }

        velocity.scl(1 / dt);

        bounds.set(position.x, position.y, sizeX, sizeY);

        if (sizeX < snowCount) {
            sizeX += scale;
        }
        if (sizeY < snowCount) {
            sizeY += scale;
        }

        extraSpeed += 0.2;
    }

    public void jump() {
        velocity.y = JUMP;
        landed = false;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position = new Vector3(x, y, z);
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y, float z) {
        velocity = new Vector3(x, y, z);
    }

    public float getExtraSpeed() {
        return extraSpeed;
    }

    public boolean isLanded() {
        return landed;
    }

    public void increaseSnowCount(int x) {
        snowCount += x;
    }

    public int getSnowCount() {
        return snowCount;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public TextureRegion getTexture() {
        return textureAnimation.getFrame();
    }

    public void dispose() {
        texture.dispose();
    }
}
