package com.mygdx.game;


import com.badlogic.gdx.Gdx;
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
    private float animationTime, explosionAnimationSpeed,jitterSpeed, tomatoWidth;

    private int tomatoNumber;
    private boolean rightTomato, alreadyExploded, clicked, actionCompleted;

    private ClickListener listener;
    private TextureRegion explosionFrame, tomatoSprite;
    private Viewport globalViewport;
    //SequenceAction sequenceAction;


    Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){ //Action pooling enables action recycling: more memory efficient
        protected MoveToAction newObject(){
            return new MoveToAction();
        }
    };



    public Tomato(int tomatoNumber, boolean rightTomato, Viewport globalViewport, float x, float y)
    {

        tomatoWidth = globalViewport.getScreenWidth()/12;
        this.rightTomato = rightTomato;
        this.tomatoNumber = tomatoNumber;
        this.globalViewport = globalViewport;


        this.setBounds(x, y,tomatoWidth,tomatoWidth);
        animationTime = 0;
        explosionAnimationSpeed = 1/15f;
        jitterSpeed = .1f;

        alreadyExploded = false;
        actionCompleted = true;

        tomatoSprite = new TextureRegion(new Texture(Gdx.files.internal("tomato"+tomatoNumber+".png")));
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
        animation = new Animation<TextureRegion>(1f/10f, animationFrames);
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

            }

        };

        this.addListener(listener);
    }
    public void reset()
    {
        rightTomato = false;
        alreadyExploded = false;

    }
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



    @Override
    public void act(float delta) {
        super.act(delta); //need to call super so I don't lose the parent class's abilities (act sequencing, etc)
        if (clicked) animationTime += delta;
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
}
