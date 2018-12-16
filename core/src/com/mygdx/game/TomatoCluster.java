package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;




public class TomatoCluster extends Group {
    private float tomatoX, tomatoY,screenW, screenH, tomatoSize, offset,tileSize ;
    private int appleTarget;

    private Viewport globalViewport;
    private boolean random;

    public TomatoCluster(float tomatoX, float tomatoY, int appleTarget, Viewport globalViewport, boolean random, float tileSize)
    {

        this.globalViewport = globalViewport;
        this.appleTarget = appleTarget;
        this.tomatoY = tomatoY;
        this.tomatoX = tomatoX;
        this.random = random;
        this.tileSize = tileSize;
        screenW = globalViewport.getScreenWidth();
        screenH = globalViewport.getScreenHeight();
        System.out.println("Heihgt:" + screenH);
        tomatoSize = screenW/12;

        offset = (screenW - (tomatoX * tomatoSize))/(tomatoX+1);


    }
    public void fill()
    {
        int rand;
        //fills apples in systemic manner
        if (!random) {
            for (int k = 0; k < tomatoY; k++) {
                float posX = 0;
                for (int i = 0; i < tomatoX; i++) {
                    rand = MathUtils.random(4);
                    if (i!=0)
                    {
                        posX+= tomatoSize;
                    }
                    posX += offset;
                    Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, posX,   k * globalViewport.getScreenWidth() / 12);

                    this.addActor(curr);
                }


            }
        }
        else
        {
            for (int i = 0; i < tomatoX * tomatoY; i++)
            {
                rand = MathUtils.random(4);
                //System.out.println(tileSize);
                Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, MathUtils.random(screenW - screenW/12), MathUtils.random(screenH - 2 * tileSize - screenW/12));
                addActor(curr);
            }
        }

    }
    public void reset()
    {

    }


}
