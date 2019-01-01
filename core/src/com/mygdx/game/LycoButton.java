package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LycoButton extends Actor {
    private Texture tTexture;
    private Viewport tViewport;

    public LycoButton(Viewport tViewport, Texture tTexture) {
        this.tViewport = tViewport;
        this.tTexture = tTexture;

    }

    public void init() {
        setSize(tViewport.getScreenWidth() / 12, tViewport.getScreenWidth() / 12);

    }

    public void reset() {
        this.clearActions();
        setColor(getColor().r, getColor().g, getColor().b, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(tTexture, getX(), getY(), getWidth(), getHeight());
    }
}
