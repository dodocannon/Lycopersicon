package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;




public class TomatoCluster extends Group {
    private float tomatoX, tomatoY,screenW, screenH, velocity, tomatoSize, offset,tileSize;
    private int appleTarget;


    private Viewport globalViewport;
    private boolean random;

    public TomatoCluster(float tomatoX, float tomatoY,float velocity, int appleTarget, Viewport globalViewport, boolean random, float tileSize)
    {

        this.globalViewport = globalViewport;
        this.appleTarget = appleTarget;
        this.tomatoY = tomatoY;
        this.tomatoX = tomatoX;
        this.random = random;
        this.tileSize = tileSize;
        this.velocity = velocity;
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
                    Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, posX,   k * globalViewport.getScreenWidth() / 12, 0,0,tileSize);

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
                Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, MathUtils.random(screenW - screenW/12), MathUtils.random(screenH - 2 * tileSize - screenW/12), getRandomVelocity(),getRandomVelocity(),tileSize);
                addActor(curr);
            }
        }

    }

    public void init()
    {

    }

    /**
     * returns a float velocity for the moving tomato between -range and range.
     *
     *
     * @return  a float velocity between -range and range.
     */
    private float getRandomVelocity()
    {

        float rVelocity = MathUtils.random(-velocity, velocity);
        if (rVelocity == 0) return rVelocity + velocity/2;
        return rVelocity;

    }

    public void dispose() {
        for (Actor t : getChildren()) {
            t = (Tomato) t;
            ((Tomato) t).dispose();
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        for (Actor t : getChildren())
        {
            t = (Tomato) t;
            if (((Tomato) t).isAlreadyExploded())
            {

                removeActor(t);
                System.out.println("removed");
            }
        }
    }
}
