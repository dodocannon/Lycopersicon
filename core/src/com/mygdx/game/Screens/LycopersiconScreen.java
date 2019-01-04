package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Background;
import com.mygdx.game.GameOverUI;
import com.mygdx.game.LycoButton;
import com.mygdx.game.Lycopersicon;
import com.mygdx.game.LycopersiconTitleUI;
import com.mygdx.game.NextLevelUI;
import com.mygdx.game.ScorePane;
import com.mygdx.game.TapPrompt;
import com.mygdx.game.TomatoCluster;
import com.mygdx.game.TomatoWorld;
import com.mygdx.game.Twinkle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;



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
    private NextLevelUI tNextLevelUI;
    private GameOverUI test;
    private TomatoCluster tCluster;
    private Background tBackground;


    private TapPrompt tTapPrompt;
    private Image tNextLevel;
    private LycoButton tReplayButton, tHomeButton;
    private ScorePane tScorePane;

    private int tStemNumber, tLevel;
    private float tTileSize, tTimeLeft, tTimeRemain;

    private Preferences tData;

    private Image tStars;
    Pool<MoveToAction> actionPool = new Pool<MoveToAction>() { //Action pooling enables action recycling: more memory efficient
        protected MoveToAction newObject() {
            return new MoveToAction();
        }
    };

    //private boolean test = false;

    public LycopersiconScreen(Lycopersicon game) {
        this.game = game;
        tLevel = 1;
        tViewport = new ScreenViewport();

        tBatch = new SpriteBatch();


        tTapPrompt = new TapPrompt(tViewport);
        tNextLevel = new Image(new Texture(Gdx.files.internal("nextLevel.png")));

        tReplayButton = new LycoButton(tViewport, new Texture(Gdx.files.internal("replayButton.png")));
        tHomeButton = new LycoButton(tViewport, new Texture(Gdx.files.internal("homeButton.png")));
        tScorePane = new ScorePane(tViewport);

        tTimeLeft = 10f;
        tTimeRemain = 10f;

        tData = Gdx.app.getPreferences("LycopersiconData");


    }

    @Override
    public void show() {


        tWorld = new TomatoWorld(tViewport, tBatch); // 800 x 480 world

        tTitleUI = new LycopersiconTitleUI(tViewport);
        tGameOverUI = new GameOverUI(tViewport);
        tNextLevelUI = new NextLevelUI(tViewport);
        test = new GameOverUI(tViewport);


        tGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin_Sketch/CabinSketch-Regular.ttf"));

        tParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tLayout = new GlyphLayout();

        tParams.size = (tViewport.getScreenHeight() / 9);

        tParams.color = Color.WHITE;
        tFont = tGenerator.generateFont(tParams);


        tNextLevel.setSize(16 * tViewport.getScreenHeight() / 20, 9 * tViewport.getScreenHeight() / 20);
        tNextLevel.setPosition(-tNextLevel.getWidth(), tViewport.getScreenHeight() / 2 - tNextLevel.getHeight());
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
            if (tCluster.remainingTargets() == 0) {
                nextLevel();
            }
            if (tTimeLeft <= 0) {
                gameOver();
            }

            tTimeLeft -= delta;
            tWorld.draw();
            tWorld.act(delta);
            drawHUD();


        }
        if (Gdx.input.getInputProcessor().equals(tGameOverUI)) {
            Gdx.app.log("Run", "test");
            tWorld.draw();
            drawHUD();
            test.draw();
            tGameOverUI.draw();
            tGameOverUI.act();
        }
        if (Gdx.input.getInputProcessor().equals(tNextLevelUI)) {
            tWorld.draw();
            tWorld.act();
            drawLevel();
        }


    }

    @Override
    public void resize(int width, int height) {
        tViewport.update(width, height);
        tTileSize = tViewport.getScreenWidth() / 10;
        tTapPrompt.init();


        tTitleUI.addActor(tTapPrompt);
        tReplayButton.init();
        tHomeButton.init();
        tScorePane.init();

        tReplayButton.setPosition(tViewport.getScreenWidth() / 2 - tScorePane.getWidth() / 2, tViewport.getScreenHeight());
        tHomeButton.setPosition(tViewport.getScreenWidth() / 2 + tScorePane.getWidth() / 2 - tHomeButton.getWidth(), tViewport.getScreenHeight());

        tGameOverUI.addActor(tReplayButton);
        tGameOverUI.addActor(tHomeButton);
        tGameOverUI.addActor(tScorePane);

        setUpTitle();
        setUpTitleUIListener();
        setUpReplayButtonListener();
        setUpHomeButtonListener();

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
        tScorePane.dispose();
        tCluster.dispose();
    }

    private void drawHUD() {
        tBatch.begin();
        tLayout.setText(tFont, "STEMS:" + tCluster.getTarget());
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() / 20, tViewport.getScreenHeight() - tViewport.getScreenHeight() / 12 + tViewport.getScreenHeight() / 20);
        tLayout.setText(tFont, "LEFT:" + tCluster.remainingTargets());
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() - tViewport.getScreenHeight() / 2, tViewport.getScreenHeight() * 11 / 12 + tViewport.getScreenHeight() / 20);
        tLayout.setText(tFont, "LEVEL:" + tLevel);
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() - tViewport.getScreenHeight() / 2, tViewport.getScreenHeight() / 10);
        tLayout.setText(tFont, (int) tTimeLeft + "s");
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() / 20, tViewport.getScreenHeight() / 10);
        tBatch.end();
    }


    private void drawLevel() {
        tBatch.begin();
        tLayout.setText(tFont, "LV " + tLevel);
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() * 4 / 10, tViewport.getScreenHeight() / 2);
        tLayout.setText(tFont, +(double) ((int) (tTimeLeft * 100)) / 100 + "sec");
        tFont.draw(tBatch, tLayout, tViewport.getScreenWidth() / 20, tViewport.getScreenHeight() / 10);

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
        tLevel = 1;
        setUpWorldListener();
        tStemNumber = MathUtils.random(4);

        tCluster = new TomatoCluster(1, tLevel, tViewport, tTileSize);
        tBackground = new Background(tViewport, tTileSize);
        tBackground.initFarm();

        tCluster.fill();

        tWorld.addActor(tBackground);
        tWorld.addActor(tCluster);
        tWorld.addActor(tNextLevel);

        Gdx.input.setInputProcessor(tWorld);

    }

    private void setUpTitle() {

        for (int i = 0; i < 50; i++) {
            Twinkle t = new Twinkle(tViewport, tViewport.getScreenWidth(), tViewport.getScreenHeight());

            tTitleUI.addActor(t);
        }
    }


    /**
     * Next level removes the current cluster
     * sets tCluster to a more "difficult" cluster
     */
    private void nextLevel() {
        Gdx.input.setInputProcessor(tNextLevelUI);
        if (tLevel >= 5) {
            tBackground.clear();
            tBackground.initSpace2();
        }

        tCluster.addAction(Actions.removeActor());
        tCluster.raiseDifficulty();
        tLevel++;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.input.setInputProcessor(tWorld);
                tWorld.addActor(tCluster);
                tTimeLeft = 10;

            }
        }, 2, 0, 0);
    }


    private void resetGame() {
        tHomeButton.reset();
        tReplayButton.reset();
        tCluster.reset();
        tScorePane.reset();
        tBackground.reset();

        tTimeLeft = 10;
        tLevel = 1;
        Gdx.input.setInputProcessor(tWorld);


    }

    private void gameOver() {
        updateHighScore();
        tScorePane.addAction(Actions.moveTo(tViewport.getScreenWidth() / 2 - tScorePane.getWidth() / 2, tViewport.getScreenHeight() / 2, .1f));
        tReplayButton.addAction(Actions.moveTo(tViewport.getScreenWidth() / 2 - tScorePane.getWidth() / 2, tViewport.getScreenHeight() / 2 - tScorePane.getHeight() / 2, .1f));
        tHomeButton.addAction(Actions.moveTo(tViewport.getScreenWidth() / 2 + tScorePane.getWidth() / 2 - tHomeButton.getWidth(), tViewport.getScreenHeight() / 2 - tScorePane.getHeight() / 2, .1f));
        tReplayButton.setTouchable(Touchable.enabled);
        tHomeButton.setTouchable(Touchable.enabled);

        Gdx.input.setInputProcessor(tGameOverUI);

    }

    private void updateHighScore() {
        if (tLevel > tData.getInteger("maxLevel")) {
            tData.putInteger("maxLevel", tLevel);
            tData.flush();

        }
        tScorePane.updateHighScore(tData.getInteger("maxLevel"));
        tScorePane.updateScore(tLevel);

    }


    private void setUpReplayButtonListener() {
        tReplayButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tHomeButton.setTouchable(Touchable.disabled);
                tReplayButton.setTouchable(Touchable.disabled);

                tReplayButton.addAction(sequence(moveBy(0, -tReplayButton.getHeight() / 2, .25f), moveBy(0, tReplayButton.getHeight() / 2, .25f), run(new Runnable() {
                    @Override
                    public void run() {
                        resetGame();
                    }
                })));
                return true;
            }
        });
    }

    private void setUpHomeButtonListener() {
        tHomeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tHomeButton.setTouchable(Touchable.disabled);
                tReplayButton.setTouchable(Touchable.disabled);

                tHomeButton.addAction(sequence(moveBy(0, -tHomeButton.getHeight() / 2, .25f), moveBy(0, tHomeButton.getHeight() / 2, .25f), run(new Runnable() {
                    @Override
                    public void run() {
                        goHome();
                    }
                })));
                return true;
            }
        });
    }

    private void goHome() {
        tHomeButton.reset();
        tReplayButton.reset();
        tCluster.reset();
        tTapPrompt.reset();
        tScorePane.reset();
        tBackground.reset();

        tTimeLeft = 10;
        tLevel = 1;

        Gdx.input.setInputProcessor(tTitleUI);
    }


}


