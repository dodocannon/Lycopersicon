package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Twinkle extends Actor {
    private Viewport tViewport;
    private Texture tTwinkle;
    public Twinkle(Viewport tViewport)
    {
        this.tViewport = tViewport;
        tTwinkle = new Texture(Gdx.files.internal("smallStar.png"));
        addAction(Actions.forever(Actions.sequence(Actions.delay(MathUtils.random(2f)),Actions.fadeOut(1f),Actions.delay(MathUtils.random(1f)), Actions.fadeIn(1f))));
        setX(MathUtils.random(tViewport.getScreenWidth()));
        setY(MathUtils.random(tViewport.getScreenHeight()));


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(tTwinkle,getX(),getY(),tViewport.getScreenWidth()/125,tViewport.getScreenWidth()/125);
    }
}
