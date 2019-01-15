package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Lycopersicon;

public class IntroScreen implements Screen {
    private Texture tTexture;
    private Lycopersicon game;
    private Viewport tViewport;
    public float tElapsedTime;
    private Batch tBatch;
    private float tX, tY;

    public IntroScreen(Lycopersicon game) {
        // tViewport = new ScreenViewport();
        // tTexture = new Texture(Gdx.files.internal("kevinkim.png"));
        this.game = game;



    }

    @Override
    public void dispose() {
       /* tBatch.dispose();
        tTexture.dispose();*/
        //Gdx.input.setInputProcessor(null);



    }

    @Override
    public void show() {
        tElapsedTime = 0;
        tBatch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
       /* Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/
        // tViewport.apply(true);

       /* tBatch.begin();
        //tBatch.draw(tTexture, tViewport.getScreenWidth() / 2 - tX / 2, tViewport.getScreenHeight() / 2 - tY / 2, tX, tY);
        tBatch.end();*/

        /*tElapsedTime += delta;
        if (tElapsedTime > 1)
        {

            game.setScreen(new LycopersiconScreen(game));
            dispose();

        }*/
        game.setScreen(new LycopersiconScreen(game));





    }

    @Override
    public void resize(int width, int height) {
       /* tViewport.update(width, height, true);
        tX = tViewport.getScreenWidth() / 2;
        tY = tViewport.getScreenWidth() / 2 * tViewport.getScreenHeight() / tViewport.getScreenWidth();
*/
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
