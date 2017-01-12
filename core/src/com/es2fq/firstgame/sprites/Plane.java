package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.es2fq.firstgame.*;

/**
 * Created by es2fq on 1/12/2017.
 */

public class Plane {
    private Vector3 position;
    private Vector3 velocity;

    private Texture texture;
    private Animation textureAnimation;

    public Plane(float x, float y, float velX) {
        texture = new Texture("plane.png");
        textureAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);

        position = new Vector3(x, y, 0);
        velocity = new Vector3(velX, 0, 0);
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

    public Vector3 getVelocity() {
        return velocity;
    }
}
