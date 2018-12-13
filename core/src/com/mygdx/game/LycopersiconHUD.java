package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;


public class LycopersiconHUD extends Group {
    private Viewport tViewport;
    private SpriteBatch tBatch;
    private Image tWoodBackground, tFont;
    private GlyphLayout tGlyphLayout;
    private int tLevel;
    private FreeTypeFontGenerator tGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter tParams;

    public LycopersiconHUD(Viewport tViewport, SpriteBatch tBatch, int tLevel)
    {
        this.tViewport = tViewport;
        this.tBatch = tBatch;
        this.tLevel = tLevel;
        init();
    }
    public void init()
    {
        tGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin_Sketch/CabinSketch-Regular.ttf"));
        tParams = new FreeTypeFontGenerator.FreeTypeFontParameter();

        tParams.size = (int) (0.65 * (tViewport.getScreenHeight() /9));
        tParams.color = Color.WHITE;

        for (int i  = 1 ; i <= 2; i++)
        {
            for (int k = 0; k < tViewport.getScreenWidth(); k+=64)
            {
                tWoodBackground = new Image(new Texture(Gdx.files.internal("wood.jpg")));
                tWoodBackground.setSize(64,64);
                tWoodBackground.setPosition(k,tViewport.getScreenHeight()- i*64);
                addActor(tWoodBackground);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
