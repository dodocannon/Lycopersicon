package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;


public class Tomato extends Actor {
    TextureRegion[] animationFrames;
    TextureRegion tmpFrames[][] = TextureRegion.split(new Texture(Gdx.files.internal("tomatosheet.png")),64,64);
    Animation<TextureRegion> animation;
    private float animationTime, explosionAnimationSpeed,jitterSpeed, tomatoWidth,velX,velY, tileSize;

    private int tomatoNumber;
    private boolean rightTomato, alreadyExploded, clicked, actionCompleted;

    private Sound tShotSound;

    private ClickListener listener;
    private TextureRegion explosionFrame, tomatoSprite;
    private Viewport globalViewport;

    //SequenceAction sequenceAction;


    Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){ //Action pooling enables action recycling: more memory efficient
        protected MoveToAction newObject(){
            return new MoveToAction();
        }
    };



    public Tomato(int tomatoNumber, boolean rightTomato, Viewport globalViewport, float x, float y,float velX,float velY, float tileSize)
    {

        tomatoWidth = globalViewport.getScreenWidth()/12;
        this.rightTomato = rightTomato;
        this.tomatoNumber = tomatoNumber;
        this.globalViewport = globalViewport;


        this.setBounds(x, y,tomatoWidth,tomatoWidth);
        this.velX = velX;
        this.velY = velY;
        this.tileSize = tileSize;
        animationTime = 0;
        explosionAnimationSpeed = 1/15f;
        jitterSpeed = .1f;

        alreadyExploded = false;
        actionCompleted = true;

        tomatoSprite = new TextureRegion(new Texture(Gdx.files.internal("tomato"+tomatoNumber+".png")));
        tShotSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/autism.mp3"));
        init();
    }
    private void init()
    {
        //initializing the libgdx animation for containing the frames that will make up the animation when the user clicks on a "target" tomato
        animationFrames = new TextureRegion[9];
        int a = 0;
        for (int k = 0; k < 3; k++)
        {
            for (int j = 0; j < 3; j++)
            {
                animationFrames[a] = tmpFrames[k][j];
                a++;
            }
        }
        animation = new Animation<TextureRegion>(explosionAnimationSpeed, animationFrames);
        // intilializing the click listnener to be attached to every tomato object
        listener = new ClickListener()
        {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (!rightTomato && actionCompleted)
                {
                    actionCompleted = false;
                    addAction(getJitter());
                }
                if (!alreadyExploded) {
                    clicked = true;
                    alreadyExploded = !alreadyExploded;

                }
                if (rightTomato) {
                    tShotSound.play();
                }


            }

        };

        this.addListener(listener);
    }
    private void relocate()
    {
        if (getX() > globalViewport.getScreenWidth() - tomatoWidth || getX() < 0)
        {
            velX = -velX;
        }
        if (getY() > globalViewport.getScreenHeight() - 2 * tileSize - tomatoWidth || getY() < 0)
        {
            velY = -velY;
        }

        setPosition(getX() + velX, getY() + velY);
    }
    public void reset()
    {
        rightTomato = false;
        alreadyExploded = false;

    }

    /**
     * Initializes and returns action sequences for tomato left to right movement.
     * This movement only occurs when the tomato is not the "exploding tomato".
     * This left to right movement is named "jitter", hence "getJitter()"
     * @return A SequencedAction containing actions for the left to right movement or "jitter" for a tomato object.
     *
     */
    private SequenceAction getJitter()
    {

        float x = getX();
        SequenceAction sequenceAction = new SequenceAction();
        MoveToAction moveAction1 = actionPool.obtain();
        MoveToAction moveAction2 = actionPool.obtain();
        MoveToAction moveAction3 = actionPool.obtain();
        MoveToAction moveAction4 = actionPool.obtain();

        moveAction1.setDuration(jitterSpeed);
        moveAction2.setDuration(jitterSpeed);
        moveAction3.setDuration(jitterSpeed);
        moveAction4.setDuration(jitterSpeed);

        moveAction1.setPosition(x+10,getY());
        moveAction2.setPosition(x,getY());
        moveAction3.setPosition(x-10,getY());
        moveAction4.setPosition(x,getY());

        sequenceAction.addAction(moveAction1);
        sequenceAction.addAction(moveAction2);
        sequenceAction.addAction(moveAction3);
        sequenceAction.addAction(moveAction4);

        sequenceAction.addAction(run(new Runnable()
        {
            @Override
            public void run() {
                actionCompleted = true;
            }
        }));


        return sequenceAction;
    }


    public void dispose() {
        tShotSound.dispose();
    }
    @Override
    public void act(float delta) {
        super.act(delta); //need to call super so I don't lose the parent class's abilities (act sequencing, etc)
        if (clicked) animationTime += delta;
        relocate();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (clicked && rightTomato) {
            //if this tomato is the "right" (exploding) tomato...
            explosionFrame = animation.getKeyFrame(animationTime);
            batch.draw(explosionFrame,getX(),getY(), tomatoWidth,tomatoWidth);

        }
        else
        {
            batch.draw(tomatoSprite,getX(),getY(),tomatoWidth,tomatoWidth);
        }

    }

    /**
     * Returns a boolean determining whether the exploding animation has completed.
     * This only returns true if the tomato is an "exploding tomato", so all other "non exploding" tomatoes will be ignored.
     * @return  whether exploding animation has completed
     */
    public boolean isAlreadyExploded()
    {
        return animationTime > explosionAnimationSpeed * animationFrames.length && rightTomato;
    }
}
