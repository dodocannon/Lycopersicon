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


public class ScorePane extends Actor {
    private Viewport tViewport;
    private int tScore, tHighScore;
    private FreeTypeFontGenerator tGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter tParam;
    private BitmapFont tFont;
    private GlyphLayout tLayout;
    private Texture tPaneTexture;

    public ScorePane(Viewport tViewport) {
        this.tViewport = tViewport;
        tGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Baloo-Regular.ttf"));
        tPaneTexture = new Texture(Gdx.files.internal("scorePane.png"));
    }

    public void init() {
        setSize(tViewport.getScreenWidth() / 2, tViewport.getScreenHeight() / 3);
        setPosition(tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight());
        tParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tLayout = new GlyphLayout();
        tParam.size = (int) (tViewport.getScreenHeight() / 20f);
        tParam.color = Color.ORANGE;
        tFont = tGenerator.generateFont(tParam);

    }

    public void updateScore(int score) {
        this.tScore = score;
    }

    public void updateHighScore(int highScore) {
        tHighScore = highScore;
    }

    public void dispose() {
        tFont.dispose();
        tGenerator.dispose();

    }

    public void reset() {
        this.clearActions();
        setPosition(tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tPaneTexture, getX(), getY(), getWidth(), getHeight());
        tLayout.setText(tFont, "HIGHEST LEVEL: " + tHighScore + "\nDEAD AT LEVEL: " + tScore);
        tFont.draw(batch, tLayout, tViewport.getScreenWidth() / 2 - getWidth() / 4, tViewport.getScreenHeight() / 2 + getHeight() / 1.5f);
    }
}
