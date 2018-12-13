package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Background;
import com.mygdx.game.Lycopersicon;
import com.mygdx.game.TomatoCluster;
import com.mygdx.game.TomatoWorld;


public class LycopersiconScreen implements Screen{
    final Lycopersicon game;
    int appleTarget;
    ScreenViewport tViewport = new ScreenViewport();
    private SpriteBatch tBatch;
    private TomatoWorld tTomatoWorld;
    public LycopersiconScreen(Lycopersicon game) {
        this.game = game;
        tBatch = new SpriteBatch();
        appleTarget = MathUtils.random(4);


        tTomatoWorld = new TomatoWorld(tViewport, tBatch); // 800 x 480 world

        TomatoCluster tomatoFarm = new TomatoCluster(5,4,appleTarget, tViewport,false);
        tomatoFarm.setSize(tViewport.getScreenWidth(),tViewport.getScreenHeight());
        tomatoFarm.setPosition(100,0);

        tomatoFarm.setDebug(true);
        tomatoFarm.fill();


        Background b = new Background(tViewport,64f);
        tTomatoWorld.addActor(b);
        tTomatoWorld.addActor(tomatoFarm);
        Gdx.input.setInputProcessor(tTomatoWorld);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tTomatoWorld.act(delta);

        tTomatoWorld.draw();
    }

    private Drawable textureToDrawable(Texture t) // I made this method to convert textures to drawables for ease of modification in the table
    {
        return new TextureRegionDrawable(new TextureRegion(t));
    }

    @Override
    public void resize(int width, int height) {
        tTomatoWorld.getViewport().update(width, height);
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

    @Override
    public void dispose() {

    }
    private void drawScore()
    {

    }



}


