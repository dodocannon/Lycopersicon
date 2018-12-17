package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TapPrompt extends Actor {


    private Viewport tViewport;

    public TapPrompt(Viewport tViewport) {

        this.tViewport = tViewport;



    }

    public void init() {

        this.setWidth(7.429f * tViewport.getScreenWidth() * .13f);
        this.setHeight(1 * tViewport.getScreenWidth() * .13f);
        this.clearActions();
        this.setColor(getColor().r, getColor().g, getColor().b, 1);

        addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f))));

    }

    public void reset() {

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(new Texture(Gdx.files.internal("tap.png")), tViewport.getScreenWidth() / 2 - getWidth() / 2, tViewport.getScreenHeight() / 2 - getHeight() / 2, getWidth(), getHeight());

    }

}
