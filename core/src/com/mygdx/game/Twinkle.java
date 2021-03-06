package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Twinkle extends Actor implements Pool.Poolable {
    private Viewport tViewport;
    private Texture tTwinkle;
    private float boundX, boundY;

    @Override
    public void reset() {

        addAction(Actions.forever(Actions.sequence(Actions.delay(MathUtils.random(2f)), Actions.fadeOut(1f), Actions.delay(MathUtils.random(1f)), Actions.fadeIn(1f))));

        //setColor(getColor().r, getColor().g, getColor().b, 1);


    }

    /**
     * Constructor for twinkle
     * boolean starstreak determines if it a rainbow pixel streaking across the screen
     *
     * @param tViewport
     * @param
     */
    public Twinkle(Viewport tViewport, float boundX, float boundY) {

        this.tViewport = tViewport;
        tTwinkle = new Texture(Gdx.files.internal("smallStar.png"));

        addAction(Actions.forever(Actions.sequence(Actions.delay(MathUtils.random(2f)), Actions.fadeOut(1f), Actions.delay(MathUtils.random(1f)), Actions.fadeIn(1f))));
        setX(MathUtils.random(boundX));
        setY(MathUtils.random(boundY));



    }

    public void initTwinkle(Viewport tViewport, float boundX, float boundY) {

        this.tViewport = tViewport;
        tTwinkle = new Texture(Gdx.files.internal("smallStar.png"));

        addAction(Actions.forever(Actions.sequence(Actions.delay(MathUtils.random(2f)), Actions.fadeOut(1f), Actions.delay(MathUtils.random(1f)), Actions.fadeIn(1f))));
        setX(MathUtils.random(boundX));
        setY(MathUtils.random(boundY));


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);



        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);


        batch.draw(tTwinkle,getX(),getY(),tViewport.getScreenWidth()/125,tViewport.getScreenWidth()/125);
    }
}
