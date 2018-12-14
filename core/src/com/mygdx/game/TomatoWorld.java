package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TomatoWorld extends Stage {
    private float tileSize;
    public TomatoWorld(Viewport tViewport, SpriteBatch tSpriteBatch, float tileSize)
    {
        super(tViewport, tSpriteBatch);
        this.tileSize = tileSize;
    }
    public float getTileSize()
    {
        return tileSize;
    }
}
