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

/**
 * Created by es2fq on 12/31/2016.
 */

public class PlayState extends State {
    private static final int GROUND_OFFSET = -30;
    private static final int NUM_OBSTACLES = 5;
    private static final int OBSTACLE_GAP = 200;

    private Snowball snowball;
    private Array<Obstacle> obstacles;

    private Texture bg;
    private Texture ground;

    private Vector2 groundPos1, groundPos2;

    private float zoom;
    private int score;

    BitmapFont bitmapFont;
    GlyphLayout glyphLayout;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, GameClass.WIDTH / 2, GameClass.HEIGHT / 2);

        zoom = 1.0f;
        score = 0;

        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();

        bg = new Texture("bg.png");
        ground = new Texture("ground.png");

        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_OFFSET);

        int groundHeight = GROUND_OFFSET + ground.getHeight();

        snowball = new Snowball(50, groundHeight);

        obstacles = new Array<Obstacle>();
        for (int i = 1; i <= NUM_OBSTACLES; i++) {
            obstacles.add(new Obstacle(i * OBSTACLE_GAP + 100, groundHeight));
        }
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
        snowball.update(dt);

        for (Obstacle o : obstacles) {
            if (o.collides(snowball.getBounds())) {
                if (score > o.getSize()) {
                    o.destroy();
                    break;
                }
                gsm.set(new PlayState(gsm));
                return;
            }
            if (snowball.getPosition().x > o.getPosition().x + o.getTexture().getWidth() && !o.isPassed()) {
                score++;

                if (score % NUM_OBSTACLES == 0) {
                }

                o.setPassed(true);
            }
            if (cam.position.x - (cam.viewportWidth / 2) > o.getPosition().x + o.getTexture().getWidth()) {
                o.reposition(o.getPosition().x + (NUM_OBSTACLES * OBSTACLE_GAP), score);
                o.setPassed(false);
            }
        }

        cam.setToOrtho(false, GameClass.WIDTH / 2 * zoom, GameClass.HEIGHT / 2 * zoom);
        cam.position.x = snowball.getPosition().x + cam.viewportWidth * 0.3f;

        zoom += 0.001f;

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(snowball.getTexture(), snowball.getPosition().x, snowball.getPosition().y, snowball.getSizeX(), snowball.getSizeY());

        for (Obstacle o : obstacles) {
            sb.draw(o.getTexture(), o.getPosition().x, o.getPosition().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        glyphLayout.setText(bitmapFont, "" + score);
        bitmapFont.getData().scale(zoom / 2000);
        bitmapFont.draw(sb, glyphLayout, cam.position.x - glyphLayout.width / 2, cam.position.y + (cam.viewportHeight / 4));

        glyphLayout.setText(bitmapFont, "" + snowball.getSnowCount());
        bitmapFont.getData().scale(zoom / 2000);
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
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
