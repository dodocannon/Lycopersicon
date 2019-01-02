package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;




public class TomatoCluster extends Group {
    private float tomatoX, tomatoY,screenW, screenH, velocity, tomatoSize, offset,tileSize;
    private int appleTarget, tTargets;


    private Viewport globalViewport;

    public TomatoCluster(float tomatoX, float tomatoY, float velocity, Viewport globalViewport, float tileSize)
    {

        this.globalViewport = globalViewport;
        this.tomatoY = tomatoY;
        this.tomatoX = tomatoX;
        this.tileSize = tileSize;
        this.velocity = velocity;

        screenW = globalViewport.getScreenWidth();
        screenH = globalViewport.getScreenHeight();

        tomatoSize = screenW/12;
        offset = (screenW - (tomatoX * tomatoSize))/(tomatoX+1);


        //this.setColor(getColor().r, getColor().g, getColor().b, 1);


    }

    /**
     * Fills the group with Tomato Actors: sets their position
     * Fills the group with explosive/non explosive Tomato Actors
     * Updates tTarget counter
     */
    public void fill() {
        dispose();
        tomatoSize = (tomatoX == 1) ? (screenW- tileSize)/tomatoX : screenW/tomatoX;
        offset = (screenW - (tomatoX * tomatoSize))/(tomatoX+1);
        appleTarget = MathUtils.random(4);
        int rand;
        if (tomatoX <= 20) {
            for (int i = 0; i < tomatoX * tomatoY; i++) {
                rand = MathUtils.random(4);
                Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, MathUtils.random(screenW - screenW / 12), MathUtils.random(screenH - 2 * tileSize - screenW / 12), getRandomVelocity(), getRandomVelocity(), tileSize, screenW / 12);
                if (curr.isRightTomato()) {
                    tTargets++;
                    //curr.startCountdown();

                }

                addActor(curr);

            }
        } else {
            for (int k = 0; k < tomatoY; k++) {
                float posX = 0;
                for (int i = 0; i < tomatoX; i++) {
                    rand = MathUtils.random(4);
                    if (i != 0) {
                        posX += tomatoSize;
                    }
                    posX += offset;
                    /**
                     * This is for the curr when apples start to get clutter the screen (systematic spread out positioning)
                     */
                    Tomato curr = new Tomato(rand, rand == appleTarget, globalViewport, posX, 0, 0, getRandomVelocity(), tileSize, tomatoSize);
                    if (curr.isRightTomato()) {
                        tTargets++;
                    }

                    this.addActor(curr);
                }


            }
        }
        //fills apples in systemic manner



        if (tTargets == 0) {

            fill();
        }

    }


    public void reset()
    {
        dispose();
        tTargets = 0;
        //setPosition(0,0);
        tomatoX = 1;
        tomatoY = 1;
        velocity = 1;
        fill();
    }
    public void init()
    {
        screenW = globalViewport.getScreenWidth();
        screenH = globalViewport.getScreenHeight();

        tomatoSize = screenW/12;
        offset = (screenW - (tomatoX * tomatoSize))/(tomatoX+1);
    }

    public int remainingTargets() {
        return tTargets;
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

    public void scaleDownVelocity(float scale) {
        for (Actor t : getChildren()) {
            ((Tomato) t).setVelX(((Tomato) t).getVelX() * scale);
            ((Tomato) t).setVelY(((Tomato) t).getVelY() * scale);
        }
    }

    public int getTarget() {
        return appleTarget;
    }

    /**
     * Future method for implementation that starts countdown on tomato via method on group
     */
    public void startCountdown() {
        for (Actor t : getChildren()) {
            ((Tomato) t).startCountdown();
        }
    }

    public void dispose() {
        for (Actor t : getChildren()) {
            t.addAction(Actions.removeActor());


        }

    }
    @Override
    public void act(float delta) {
        super.act(delta);
        for (Actor t : getChildren())
        {
            if (((Tomato) t).isAlreadyExploded())
            {
                tTargets--;
                this.removeActor(t);
                t.remove();
                System.out.println("removed");
            }
        }
    }

    public void raiseDifficulty() {

        dispose();
        //tTargets = 0;
        //setPosition(0,0);
        tomatoX += 2;
        tomatoY =1;
        velocity = velocity * 1.25f;
        fill();


    }

    public void print() {
        System.out.println(offset);
    }


}
