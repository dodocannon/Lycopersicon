package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Background extends Group {
    /*
    for ease of setting the background image
     */
    private Image grassBackground, woodBackground;
    private Viewport tViewport;
    private float tileSize;

    public Background(Viewport tViewport, float tileSize)
    {
        this.tileSize = tileSize;
        this.tViewport = tViewport;
        init();

    }

    private void init()
    {
        for (int i = 0; i < tViewport.getScreenHeight(); i+=tileSize)
        {
            for (int j = 0; j < tViewport.getScreenWidth(); j+= tileSize)
            {
                grassBackground = new Image(new Texture(Gdx.files.internal("grass.png")));
                grassBackground.setSize(tileSize,tileSize);
                grassBackground.setPosition(j,i);
                addActor(grassBackground);
            }
        }
        for (int i = 1; i <= 1; i++)
        {
            for (int k = 0; k < tViewport.getScreenWidth(); k+=tileSize)
            {
                woodBackground = new Image(new Texture(Gdx.files.internal("wood.jpg")));
                woodBackground.setSize(tileSize,tileSize);
                woodBackground.setPosition(k,tViewport.getScreenHeight()- i*tileSize);
                addActor(woodBackground);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


    }
}
