package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TutorialPage extends Actor {
    private Viewport tViewport;

    private Texture[] tTutorialContainer;
    private int index = 0;

    public TutorialPage(Viewport tViewport) {
        this.tViewport = tViewport;
        tTutorialContainer = new Texture[4];
        tTutorialContainer[0] = new Texture(Gdx.files.internal("tutorial1.png"));
        tTutorialContainer[1] = new Texture(Gdx.files.internal("tutorial2.png"));
        tTutorialContainer[2] = new Texture(Gdx.files.internal("tutorial3.png"));
        tTutorialContainer[3] = new Texture(Gdx.files.internal("tutorial4.png"));
    }

    public void display(int index) {
        final int b = index;
        this.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.run(new Runnable() {
            @Override
            public void run() {
                displayHelper(b);
                addAction(Actions.fadeIn(.25f));
            }
        })));
    }

    public void displayHelper(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }

    public void reset() {
        clearActions();
        index = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(tTutorialContainer[index], tViewport.getScreenWidth() / 2 - tViewport.getScreenHeight() / 2, -tViewport.getScreenHeight() / 15, tViewport.getScreenHeight(), tViewport.getScreenHeight());

    }
}
