package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.es2fq.firstgame.*;

/**
 * Created by es2fq on 1/14/2017.
 */

public class Bomb {
    private static final int GRAVITY = -15;

    private Vector3 position;
    private Vector3 velocity;

    private Texture texture;
    private Animation textureAnimation;

    public Bomb(float x, float y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
    }

    private void update(float dt) {
        textureAnimation.update(dt);

        velocity.add(0, GRAVITY, 0);

        velocity.scl(dt);

        position.add(velocity.x, velocity.y, 0);

        velocity.scl(1 / dt);
    }
}
