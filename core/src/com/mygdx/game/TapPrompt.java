package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TapPrompt extends Actor {

    private Texture tTexture;
    private Viewport tViewport;

    public TapPrompt(Viewport tViewport) {

        this.tViewport = tViewport;



    }

    public void init() {
        tTexture = new Texture(Gdx.files.internal("tapwhite.png"));
        this.setWidth(7.429f * tViewport.getScreenWidth() * .13f);
        this.setHeight(1 * tViewport.getScreenWidth() * .13f);
        this.clearActions();
        this.setOrigin(getWidth() / 2, getHeight() / 2);
        addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f))));


    }

    public void reset() {
        this.setWidth(7.429f * tViewport.getScreenWidth() * .13f);
        this.setHeight(1 * tViewport.getScreenWidth() * .13f);
        this.clearActions();
        this.setOrigin(getWidth() / 2, getHeight() / 2);
        addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f))));
        this.clearActions();

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
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        batch.draw(tTexture, tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight() / 2 - getHeight() / 2, this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                tTexture.getWidth(), tTexture.getHeight(), false, false);

    }

}
