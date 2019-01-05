package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CreditsPane extends Actor {
    private Viewport tViewport;
    private FreeTypeFontGenerator tGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter tParam;
    private BitmapFont tFont;
    private GlyphLayout tLayout;
    private Texture tPaneTexture, tKevinTexture, tJoonTexture, tPhillipTexture, tEddieTexture, tCreditsTexture;
    private float spacingBuffer, picSize;

    public CreditsPane(Viewport tViewport) {
        this.tViewport = tViewport;
        tGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Baloo-Regular.ttf"));
        tPaneTexture = new Texture(Gdx.files.internal("scorePane.png"));
        tPhillipTexture = new Texture(Gdx.files.internal("phillip.png"));
        tKevinTexture = new Texture(Gdx.files.internal("kevin.png"));
        tJoonTexture = new Texture(Gdx.files.internal("joon.png"));
        tCreditsTexture = new Texture(Gdx.files.internal("credits.png"));
        tEddieTexture = new Texture(Gdx.files.internal("eddie.png"));


    }

    public void init() {
        setSize(tViewport.getScreenWidth() / 1.5f, tViewport.getScreenHeight() / 1.2f);
        setPosition(tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight());
        tParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tLayout = new GlyphLayout();
        tParam.size = (int) (getWidth() / 25);
        tParam.color = Color.ORANGE;
        tFont = tGenerator.generateFont(tParam);
        spacingBuffer = getHeight() / 15;
        picSize = getHeight() / 8;
    }

    public void reset() {
        this.clearActions();

        setPosition(tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight());
    }

    public void dispose() {
        tFont.dispose();
        tGenerator.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tPaneTexture, getX(), getY(), getWidth(), getHeight());
        batch.draw(tCreditsTexture, getX() + getWidth() / 2 - getWidth() / 8, getY() + getHeight() / 1.25f, getWidth() / 4, getWidth() / 8);
        batch.draw(tKevinTexture, getX() + getWidth() / 8, getY() + 4 * (spacingBuffer) + 3 * picSize, picSize, picSize);
        batch.draw(tEddieTexture, getX() + getWidth() / 8, getY() + 3 * (spacingBuffer) + 2 * picSize, picSize, picSize);
        batch.draw(tJoonTexture, getX() + getWidth() / 8, getY() + 2 * (spacingBuffer) + picSize, picSize, picSize);
        batch.draw(tPhillipTexture, getX() + getWidth() / 8, getY() + spacingBuffer, picSize, picSize);

        tLayout.setText(tFont, "Kevin - Design, Programming, Artwork\nKim");
        tFont.draw(batch, tLayout, getX() + getWidth() / 7.5f + picSize, getY() + 4 * (spacingBuffer + picSize));
        tLayout.setText(tFont, "Eddie - Ideas, moral support\nPark");
        tFont.draw(batch, tLayout, getX() + getWidth() / 7.5f + picSize, getY() + 3 * (spacingBuffer + picSize));

        tLayout.setText(tFont, "Joonwoo - Ideas, moral support\nShin");
        tFont.draw(batch, tLayout, getX() + getWidth() / 7.5f + picSize, getY() + 2 * (spacingBuffer + picSize));

        tLayout.setText(tFont, "Phillip - Ideas, moral support\nPeng");
        tFont.draw(batch, tLayout, getX() + getWidth() / 7.5f + picSize, getY() + 1 * (spacingBuffer + picSize));


        //batch.draw();

    }
}
