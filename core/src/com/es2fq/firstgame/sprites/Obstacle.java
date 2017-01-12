package com.es2fq.firstgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.es2fq.firstgame.*;

import java.util.Random;

/**
 * Created by es2fq on 1/4/2017.
 */

public class Obstacle {
    private Vector3 position;

    private Array<Texture> textures;
    private Texture currentTexture;

    private Array<Animation> destroyAnimations;
    private Animation textureAnimation;

    private int[] obstacleSizes;

    private Rectangle bounds;

    private int obstacleNumber;
    private boolean passed;
    private boolean destroyed;

    public Obstacle(float x, float y) {
        textures = new Array<Texture>();
        textures.add(new Texture("snow.png"));
        textures.add(new Texture("fence.png"));
        textures.add(new Texture("building.png"));

        destroyAnimations = new Array<Animation>();
        destroyAnimations.add(new Animation(new TextureRegion(new Texture("snowdestroy.png")), 4, 0.2f));
        destroyAnimations.add(new Animation(new TextureRegion(new Texture("fencedestroy.png")), 4, 0.2f));
        destroyAnimations.add(new Animation(new TextureRegion(new Texture("buildingdestroy.png")), 4, 0.2f));

        obstacleSizes = new int[textures.size];
        obstacleSizes[0] = 3;
        obstacleSizes[1] = 20;
        obstacleSizes[2] = 50;

        obstacleNumber = 1;

        currentTexture = textures.get(obstacleNumber);
        textureAnimation = new Animation(new TextureRegion(currentTexture), 1, 1f);

        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, currentTexture.getWidth(), currentTexture.getHeight());

        passed = false;
        destroyed = false;
    }

    public void update(float dt) {
        textureAnimation.update(dt);
    }

    public void reposition(float x, int snowCount) {
        changeObstacle(snowCount);
        textureAnimation.setFrame(0);

        destroyed = false;

        currentTexture = textures.get(obstacleNumber);
        textureAnimation = new Animation(new TextureRegion(currentTexture), 1, 1f);

        bounds.setWidth(currentTexture.getWidth());
        bounds.setHeight(currentTexture.getHeight());

        position.set(x, position.y, 0);
        bounds.setPosition(position.x, position.y);
    }

    private void changeObstacle(int snowCount) {
        int numObstaclesAvailable = obstacleSizes.length;

        if (snowCount < 20)
            numObstaclesAvailable = 2;
        if (snowCount < 50)
            numObstaclesAvailable = 3;

        obstacleNumber = new Random().nextInt(numObstaclesAvailable);
    }

    public void destroy() {
        if (destroyed)
            return;

        destroyed = true;

        textureAnimation = destroyAnimations.get(obstacleNumber);

        textureAnimation.setMaxCycles(1);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public TextureRegion getTexture() {
        return textureAnimation.getFrame();
    }

    public int getObstacleNumber() {
        return obstacleNumber;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean bool) {
        passed = bool;
    }

    public int getSize() {
        return obstacleSizes[obstacleNumber];
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position = new Vector3(x, y, z);
    }
}
