package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * COMMENTED OUT BATCH SETCOLOR!!!
 */
public class Tomato extends Actor implements Poolable {
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

    private Timer timer;


    //SequenceAction sequenceAction;


    Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){ //Action pooling enables action recycling: more memory efficient
        protected MoveToAction newObject(){
            return new MoveToAction();
        }
    };

    @Override
    public void reset() {
        clicked = false;
        animationTime = 0;
        actionCompleted = false;
        setTouchable(Touchable.enabled);
        explosionFrame = null;


        //  tomatoSprite = new TextureRegion(new Texture(Gdx.files.internal("tomato"+tomatoNumber+".png")));

    }


    public Tomato() {
    }

    public void initTomato(int tomatoNumber, boolean rightTomato, Viewport globalViewport, float x, float y, float velX, float velY, float tileSize, float tomatoWidth)
    {

        this.tomatoWidth = tomatoWidth;
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
        actionCompleted = false;

        tomatoSprite = new TextureRegion(new Texture(Gdx.files.internal("tomato"+tomatoNumber+".png")));
        tShotSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/splat1.mp3"));

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
        setUpListener();
    }

    private void setUpListener() {

        listener = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                setTouchable(Touchable.disabled);
                if (rightTomato) {
                    tShotSound.play();
                    clicked = true;
                }


            }

        };

        this.addListener(listener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        if (clicked && rightTomato) {
            //if this tomato is the "right" (exploding) tomato...
            explosionFrame = animation.getKeyFrame(animationTime);
            batch.draw(explosionFrame, getX(), getY(), tomatoWidth, tomatoWidth);

        } else {
            batch.draw(tomatoSprite, getX(), getY(), tomatoWidth, tomatoWidth);
        }

    }
    private void relocate()
    {
        if (getX() > globalViewport.getScreenWidth() - tomatoWidth || getX() < 0)
        {
            velX = -velX;
        }
        if (getY() > globalViewport.getScreenHeight() - 1 * tileSize - tomatoWidth || getY() < 0)
        {
            velY = -velY;
        }

        setPosition(getX() + velX, getY() + velY);
    }


    /**
     * Initializes and returns action sequences for tomato left to right movement.
     * This movement only occurs when the tomato is not the "exploding tomato".
     * This left to right movement is named "jitter", hence "getJitter()"
     * @return A SequencedAction containing actions for the left to right movement or "jitter" for a tomato object.
     *
     */
    public void dispose() {
        tShotSound.dispose();

    }
    @Override
    public void act(float delta) {
        super.act(delta); //need to call super so I don't lose the parent class's abilities (act sequencing, etc)
        if (clicked) animationTime += delta;
        relocate();

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

    public boolean isRightTomato() {
        return rightTomato;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float x) {
        velX = x;
    }

    public void setVelY(float y) {
        velY = y;
    }

    /*@Override
    public String toString() {
        return "Tomato{" +

                ", animationTime=" + animationTime +
                ", explosionAnimationSpeed=" + explosionAnimationSpeed +
                ", jitterSpeed=" + jitterSpeed +
                ", tomatoWidth=" + tomatoWidth +
                ", velX=" + velX +
                ", velY=" + velY +
                ", tileSize=" + tileSize +
                ", tomatoNumber=" + tomatoNumber +
                ", rightTomato=" + rightTomato +
                ", alreadyExploded=" + alreadyExploded +
                ", clicked=" + clicked +
                ", actionCompleted=" + actionCompleted +
                ", tShotSound=" + tShotSound +
                ", listener=" + listener +
                ", explosionFrame=" + explosionFrame +
                ", tomatoSprite=" + tomatoSprite +
                ", globalViewport=" + globalViewport +
                ", timer=" + timer +
                ", actionPool=" + actionPool +
                '}';
    }*/
}
