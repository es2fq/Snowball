package com.es2fq.firstgame.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.es2fq.firstgame.GameClass;
import com.es2fq.firstgame.sprites.*;

import java.util.Random;

/**
 * Created by es2fq on 12/31/2016.
 */

public class PlayState extends State {
    private static final int GROUND_OFFSET = -30;
    private static final int NUM_GROUND = 4;
    private static final int NUM_OBSTACLES = 4;
    private static final int OBSTACLE_GAP = 200;

    private Snowball snowball;

    private Array<Obstacle> obstacles;
    private Array<Vector2> groundPositions;
    private Array<Plane> planes;

    private Texture bg;
    private Texture ground;

    private float zoom;
    private int score;
    private int count;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, GameClass.WIDTH / 2, GameClass.HEIGHT / 2);

        zoom = 1.0f;
        score = 0;
        count = 0;

        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();

        bg = new Texture("bg.png");
        ground = new Texture("ground.png");

        int groundHeight = GROUND_OFFSET + ground.getHeight();
        snowball = new Snowball(50, groundHeight);

        obstacles = new Array<Obstacle>();
        for (int i = 1; i <= NUM_OBSTACLES; i++) {
            obstacles.add(new Obstacle(i * OBSTACLE_GAP + 100, groundHeight));
        }

        groundPositions = new Array<Vector2>();
        for (int i = 0; i < NUM_GROUND; i++) {
            groundPositions.add(new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth() * i, GROUND_OFFSET));
        }

        planes = new Array<Plane>();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && snowball.isLanded()) {
            snowball.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        updateObstacles(dt);
        updatePlanes(dt);
        snowball.update(dt);

        cam.setToOrtho(false, GameClass.WIDTH / 2 * zoom, GameClass.HEIGHT / 2 * zoom);
        cam.position.x = snowball.getPosition().x + cam.viewportWidth * 0.4f;

        zoom += 0.001f;

        cam.update();

        if (count % 15 == 0) {
            snowball.increaseSnowCount(1);
        }
        if (count % 50 == 0 && snowball.getSnowCount() > 50) {
            addPlane();
        }
        count++;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(snowball.getTexture(), snowball.getPosition().x, snowball.getPosition().y, snowball.getSizeX(), snowball.getSizeY());

        for (Vector2 coord : groundPositions) {
            sb.draw(ground, coord.x, coord.y);
        }

        for (Obstacle o : obstacles) {
            sb.draw(o.getTexture(), o.getPosition().x, o.getPosition().y);

            glyphLayout.setText(bitmapFont, "" + o.getSize());
            bitmapFont.getData().scale(zoom / 10000);
            bitmapFont.draw(sb, glyphLayout,
                    o.getPosition().x + o.getTexture().getRegionWidth() / 2 - glyphLayout.width / 2,
                    o.getPosition().y - 10);
        }

        for (Plane plane : planes) {
            sb.draw(plane.getTexture(), plane.getPosition().x, plane.getPosition().y);
        }

        glyphLayout.setText(bitmapFont, "" + score);
        bitmapFont.getData().scale(zoom / 10000);
        bitmapFont.draw(sb, glyphLayout, cam.position.x - glyphLayout.width / 2, cam.position.y + (cam.viewportHeight / 4));

        glyphLayout.setText(bitmapFont, "" + snowball.getSnowCount());
        bitmapFont.getData().scale(zoom / 10000);
        bitmapFont.draw(sb, glyphLayout,
                snowball.getPosition().x + snowball.getBounds().width / 2 - glyphLayout.width / 2,
                snowball.getPosition().y + snowball.getBounds().height + glyphLayout.height + 10);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        snowball.dispose();
        ground.dispose();
    }

    private void updateGround() {
        for (int i = 0; i < groundPositions.size; i++) {
            Vector2 coord = groundPositions.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > coord.x + ground.getWidth()) {
                coord.add(ground.getWidth() * NUM_GROUND, 0);
            }
            groundPositions.set(i, coord);
        }
    }

    private void updateObstacles(float dt) {
        for (Obstacle o : obstacles) {
            o.update(dt);
            if (o.collides(snowball.getBounds())) {
                if (snowball.getSnowCount() >= o.getSize()) {
                    o.destroy();
                } else {
                    gsm.set(new PlayState(gsm));
                    return;
                }
            }
            if (snowball.getPosition().x > o.getPosition().x + o.getTexture().getRegionWidth() && !o.isPassed()) {
                score++;
                o.setPassed(true);
            }
            if (cam.position.x - (cam.viewportWidth / 2) > o.getPosition().x + o.getTexture().getRegionWidth()) {
                o.reposition(o.getPosition().x + (NUM_OBSTACLES * OBSTACLE_GAP), snowball.getSnowCount());
                o.setPassed(false);
            }
        }
    }

    private void updatePlanes(float dt) {
        for (Plane plane : planes) {
            plane.update(dt);

            if (plane.getPosition().x > cam.position.x + cam.viewportWidth / 2) {
                planes.removeValue(plane, true);
            }
        }
    }

    private void addPlane() {
        float rand = new Random().nextInt((int) cam.viewportHeight / 2);

        float x = cam.position.x - cam.viewportWidth / 2 - 100;
        float y = cam.position.y + rand;
        float velX = snowball.getExtraSpeed() * 3;

        Plane plane = new Plane(x, y, velX);
        planes.add(plane);
    }
}
