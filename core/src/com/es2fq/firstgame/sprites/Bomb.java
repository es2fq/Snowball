package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.es2fq.firstgame.*;

/**
 * Created by es2fq on 1/14/2017.
 */

public class Bomb {
    private static final int GRAVITY = -50;

    private Vector3 position;
    private Vector3 velocity;

    private Texture texture;
    private Animation textureAnimation;

    public Bomb(float x, float y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, GRAVITY, 0);

        texture = new Texture("bomb.png");
        textureAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);
    }

    public void update(float dt) {
        textureAnimation.update(dt);

        velocity.scl(dt);

        position.add(velocity.x, velocity.y, 0);

        velocity.scl(1 / dt);
    }

    public TextureRegion getTexture() {
        return textureAnimation.getFrame();
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
}
