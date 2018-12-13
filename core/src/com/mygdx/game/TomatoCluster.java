package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;




public class TomatoCluster extends Group {
    private InputProcessor processor;
    private int height;
    private int width;
    private int appleTarget;
    private Viewport globalViewport;
    private boolean random;

    public TomatoCluster(int width, int height, int appleTarget, Viewport globalViewport, boolean random)
    {

        this.globalViewport = globalViewport;
        this.appleTarget = appleTarget;
        this.height = height;
        this.width = width;
    }
    public void fill()
    {
        if (!random) {
            for (int k = 0; k < height; k++) {
                for (int i = 0; i < width; i++) {
                    int rand = MathUtils.random(4);
                    Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, getX() + i * globalViewport.getScreenWidth() / 12, getY() + k * globalViewport.getScreenWidth() / 12);
                    this.addActor(curr);
                }


            }
        }
        else
        {

        }

    }


}
