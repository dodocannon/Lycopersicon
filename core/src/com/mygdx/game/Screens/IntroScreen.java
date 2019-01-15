package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Lycopersicon;

public class IntroScreen implements Screen {
    private Texture tTexture;
    private Lycopersicon game;
    private Viewport tViewport;
    private float tElapsedTime;
    private SpriteBatch tBatch;
    private float tX, tY;

    public IntroScreen(Lycopersicon game) {
        this.game = game;
        tViewport = new ScreenViewport();
        tTexture = new Texture(Gdx.files.internal("kevinkim.png"));


    }

    @Override
    public void dispose() {
        tBatch.dispose();
        tTexture.dispose();

    }

    @Override
    public void show() {
        tElapsedTime = 0;
        tBatch = new SpriteBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);

    }

    @Override
    public void render(float delta) {
        tElapsedTime += delta;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tViewport.apply(true);

        tBatch.begin();
        tBatch.draw(tTexture, tViewport.getScreenWidth() / 2 - tX / 2, tViewport.getScreenHeight() / 2 - tY / 2, tX, tY);
        tBatch.end();
        if (tElapsedTime > 3) {
            game.setScreen(new LycopersiconScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        tViewport.update(width, height, true);
        tX = tViewport.getScreenWidth() / 2;
        tY = tViewport.getScreenWidth() / 2 * tViewport.getScreenHeight() / tViewport.getScreenWidth();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
