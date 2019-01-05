package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TapPrompt extends Actor {

    private Texture tTapTexture, tLycopersionTexture;
    private Viewport tViewport;

    public TapPrompt(Viewport tViewport) {

        this.tViewport = tViewport;
        tTapTexture = new Texture(Gdx.files.internal("start.png"));
        tLycopersionTexture = new Texture(Gdx.files.internal("lycopersicon.png"));




    }

    public void init() {
        this.clearActions();
        this.setWidth(2 * tViewport.getScreenWidth() * .13f);
        this.setHeight(tViewport.getScreenWidth() * .13f);
        this.setOrigin(getWidth() / 2, getHeight() / 2);
        addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f))));


    }

    public void reset() {
        this.clearActions();

        this.setWidth(2 * tViewport.getScreenWidth() * .13f);
        this.setHeight(tViewport.getScreenWidth() * .13f);
        this.setOrigin(getWidth() / 2, getHeight() / 2);
        addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f))));

    }

    /**
     * Overrides the draw method in order to allow color fading, scaling, positioning, and rotating.
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tLycopersionTexture, tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight() / 2 - getHeight() / 2, getWidth() * 1.5f, getHeight() * 1.5f);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        batch.draw(tTapTexture, tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight() / 4 - getHeight() / 2, this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                tTapTexture.getWidth(), tTapTexture.getHeight(), false, false);


    }

}
