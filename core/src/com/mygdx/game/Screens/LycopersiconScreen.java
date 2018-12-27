package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Background;
import com.mygdx.game.GameOverUI;
import com.mygdx.game.Lycopersicon;
import com.mygdx.game.LycopersiconTitleUI;
import com.mygdx.game.TapPrompt;
import com.mygdx.game.TomatoCluster;
import com.mygdx.game.TomatoWorld;
import com.mygdx.game.Twinkle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Fade out mechanisms,
 * Fade in, faster movement, rotating apples
 * Teleportation(?)
 * How am I going to implement nextlevel?
 *
 *  LEVEL IMPLEMENTATION PSEUDOCODE
 *  if (remaining lycos == 0)
 *  {
 *       run timer.
 *       remove all actors (including cluster
 *       remove the fonts stuff
 *       display level number
 *       screen fade out?
 *       add new cluster
 *
 *  }
 *
 *  bonuslevel score multiplier
 *
 */

public class LycopersiconScreen implements Screen {
    public final Lycopersicon game;

    private ScreenViewport tViewport;
    private SpriteBatch tBatch;
    private BitmapFont tFont;
    private FreeTypeFontGenerator tGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter tParams;
    private GlyphLayout tLayout;

    private TomatoWorld tWorld;
    private LycopersiconTitleUI tTitleUI;
    private GameOverUI tGameOverUI;
    private TomatoCluster tCluster;
    private Background tBackground;


    private TapPrompt tTapPrompt;

    private int tStemNumber, levelNumber;
    private float tTileSize;

    private Image tStars;

    private boolean test = false;

    public LycopersiconScreen(Lycopersicon game) {
        this.game = game;
        tViewport = new ScreenViewport();

        tBatch = new SpriteBatch();


        tTapPrompt = new TapPrompt(tViewport);


    }

    @Override
    public void show() {


        tWorld = new TomatoWorld(tViewport, tBatch); // 800 x 480 world

        tTitleUI = new LycopersiconTitleUI(tViewport);
        tGameOverUI = new GameOverUI(tViewport);


        tGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin_Sketch/CabinSketch-Regular.ttf"));

        tParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tLayout = new GlyphLayout();

        tParams.size = (tViewport.getScreenHeight() / 9);

        tParams.color = Color.WHITE;
        tFont = tGenerator.generateFont(tParams);



        Gdx.input.setInputProcessor(tTitleUI);




    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.getInputProcessor().equals(tTitleUI)) {
            tTitleUI.draw();
            tTitleUI.act(delta);
        }
        if (Gdx.input.getInputProcessor().equals(tWorld)) {

            tWorld.draw();
            tWorld.act(delta);
            drawHUD();
            if (test == false) {
                System.out.println("EEE");
                nextLevel();
                test = true;
            }
        }


    }

    private Drawable textureToDrawable(Texture t) // I made this method to convert textures to drawables for ease of modification in the table
    {
        return new TextureRegionDrawable(new TextureRegion(t));
    }

    @Override
    public void resize(int width, int height) {
        tViewport.update(width, height);
        tTileSize = tViewport.getScreenWidth() / 10;

        tTapPrompt.init();


        tTitleUI.addActor(tTapPrompt);

        setUpTitle();
        setUpTitleUIListener();


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {


        tBatch.dispose();
        addAction(Actions.rotateBy(5000000));

        tCluster.dispose();
    }

    private void drawHUD() {
        tBatch.begin();
        tLayout.setText(tFont, "STEMS:" + tStemNumber);
        tFont.draw(tBatch, tLayout, 0, tViewport.getScreenHeight() - tViewport.getScreenHeight() / 12);
        tLayout.setText(tFont, "Lycos Left: " + tCluster.remainingTargets());
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() / 2, tViewport.getScreenHeight() * 11 / 12);
        tBatch.end();
    }

    private void setUpTitleUIListener() {
        tTitleUI.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tTapPrompt.clearActions();
                tTapPrompt.addAction(sequence(parallel(repeat(7, rotateBy(50, .2f)),
                        scaleTo(.5f, .5f, 1.4f)), run(new Runnable() {
                    @Override
                    public void run() {
                        // Gdx.input.setInputProcessor(tWorld);
                        setUpWorld();
                    }
                })));
                return true;
            }
        });
    }

    private void setUpWorldListener() {
        tWorld.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                switch (keycode) {
                    case Input.Keys.M:
                        tCluster.scaleDownVelocity(.7f);
                        break;

                }
                return true;
            }
        });
    }

    private void setUpWorld() {

        setUpWorldListener();
        tStemNumber = MathUtils.random(4);

        tCluster = new TomatoCluster(5, 4, tViewport.getScreenWidth() / 50, tStemNumber, tViewport, true, tTileSize);
        tBackground = new Background(tViewport, tTileSize);

        tCluster.setPosition(0, 0);
        tCluster.fill();

        tCluster.addAction(sequence(delay(1f), fadeIn(4f)));


        tWorld.addActor(tBackground);
        tWorld.addActor(tCluster);
        System.out.println("AAAA");
        Gdx.input.setInputProcessor(tWorld);

    }
    private void setUpTitle()
    {

        for (int i = 0; i < 50; i++)
        {
            Twinkle t = new Twinkle(tViewport);

            tTitleUI.addActor(t);
        }
    }
    private void nextLevel()
    {
        tWorld.getActors().removeValue(tCluster, true);
        System.out.println(tViewport.getScreenWidth());
        System.out.println(tTileSize);
        TomatoCluster a = new TomatoCluster(1, 5, 2, tStemNumber, tViewport, true, tTileSize);
        a.setPosition(0, 0);
        a.fill();
        tWorld.addActor(a);
        System.out.println(tWorld.getActors());

        //tWorld.add
    }


}


