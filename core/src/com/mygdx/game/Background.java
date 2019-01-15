package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Background extends Actor {
    /*
    for ease of setting the background image
     */
    private Tile mainBackground, woodBackground;
    private Viewport tViewport;
    private float tileSize;
    private Texture grass, wood;
    private boolean space;

    public Background(Viewport tViewport, float tileSize)
    {
        this.tileSize = tileSize;
        this.tViewport = tViewport;
        grass = new Texture(Gdx.files.internal("grass1.jpg"));
        wood = new Texture(Gdx.files.internal("wood.jpg"));
        space = false;


    }

    public void spacetime() {
        space = true;
    }

    public void farmtime() {
        space = false;
    }


   /* public void initFarm()
    {
        for (int i = 0; i < tViewport.getScreenHeight(); i+=tileSize)
        {
            for (int j = 0; j < tViewport.getScreenWidth(); j+= tileSize)
            {
                mainBackground = new Tile(new Texture(Gdx.files.internal("grass1.jpg")));
                mainBackground.setSize(tileSize, tileSize);
                mainBackground.setPosition(j, i);
                addActor(mainBackground);
            }
        }
        for (int i = 1; i <= 1; i++)
        {
            for (int k = 0; k < tViewport.getScreenWidth(); k+=tileSize)
            {
                woodBackground = new Tile(new Texture(Gdx.files.internal("wood.jpg")));
                woodBackground.setSize(tileSize,tileSize);
                woodBackground.setPosition(k,tViewport.getScreenHeight()- i*tileSize);
                addActor(woodBackground);
            }
        }
    }

    public void initSpace() {
        for (int i = 0; i < tViewport.getScreenHeight(); i += tileSize) {
            for (int j = 0; j < tViewport.getScreenWidth(); j += tileSize) {
                mainBackground = new Tile(new Texture(Gdx.files.internal("star.png")));
                mainBackground.setSize(tileSize, tileSize);
                mainBackground.setPosition(j, i);
                addActor(mainBackground);
            }
        }
        for (int i = 1; i <= 1; i++) {
            for (int k = 0; k < tViewport.getScreenWidth(); k += tileSize) {
                woodBackground = new Tile(new Texture(Gdx.files.internal("wood.jpg")));
                woodBackground.setSize(tileSize, tileSize);
                woodBackground.setPosition(k, tViewport.getScreenHeight() - i * tileSize);
                addActor(woodBackground);
            }
        }
    }

    public void initSpace2() {
        for (int i = 1; i <= 1; i++) {
            for (int k = 0; k < tViewport.getScreenWidth(); k += tileSize) {
                woodBackground = new Tile(new Texture(Gdx.files.internal("wood.jpg")));
                woodBackground.setSize(tileSize, tileSize);
                woodBackground.setPosition(k, tViewport.getScreenHeight() - i * tileSize);
                addActor(woodBackground);
            }
        }
        for (int i = 0; i < 20; i++) {
            addActor(new Twinkle(tViewport, tViewport.getScreenWidth(), tViewport.getScreenHeight() - tileSize));
        }
    }*/

    public void clear() {
        this.remove();
    }

    public void reset() {

        farmtime();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);'
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        if (space) {
            for (int i = 1; i <= 1; i++) {
                for (int k = 0; k < tViewport.getScreenWidth(); k += tileSize) {

                    batch.draw(wood, k, tViewport.getScreenHeight() - i * tileSize, tileSize, tileSize);
                }
            }
        } else {


            for (int i = 0; i < tViewport.getScreenHeight(); i += tileSize) {
                for (int j = 0; j < tViewport.getScreenWidth(); j += tileSize) {
               /* mainBackground = new Tile(new Texture(Gdx.files.internal("grass1.jpg")));
                mainBackground.setSize(tileSize, tileSize);
                mainBackground.setPosition(j, i);
                addActor(mainBackground);*/
                    batch.draw(grass, j, i, tileSize, tileSize);
                }
            }
            for (int i = 1; i <= 1; i++) {
                for (int k = 0; k < tViewport.getScreenWidth(); k += tileSize) {

                    batch.draw(wood, k, tViewport.getScreenHeight() - i * tileSize, tileSize, tileSize);
                }
            }
        }


    }
}
