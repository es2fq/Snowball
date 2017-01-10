package com.es2fq.firstgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by es2fq on 12/31/2016.
 */

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    private int numCycles;
    private int maxCycles;

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        int frameHeight = region.getRegionHeight();

        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, frameHeight));
        }

        this.frameCount = frameCount;
        this.maxFrameTime = cycleTime / frameCount;
        this.frame = 0;
        this.numCycles = 0;
        this.maxCycles = -1;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            numCycles++;
            if (maxCycles != -1 && numCycles >= maxCycles) {
                frame -= 1;
                return;
            }
            frame = 0;
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public void setMaxCycles(int num) {
        maxCycles = num;
    }
}
