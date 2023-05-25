package com.badlogic.drop;


import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.title.LoserScreen;
import com.mygdx.game.title.TutorialScreen;

public class GameScreen implements Screen {
    final Drop game;

    Texture dropImage;
    Texture bucketImage;
   // Sound dropSound;
    Texture controlScreenImage;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;

    Array<Rectangle> healthKits;
    long lastDropTime;
    long lastDropTimes;

    int dropsGathered;
    private Stage stage;
    float health = 15;
    Texture healthBar;
    Rectangle transparentBar;

    int totalHealth;
    Texture healthBarSegment;
    TextureRegion helthBar;
    Texture firstAidKit;

    int counter = 1;


    public GameScreen(final Drop game) {



        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("DogTreat.png"));
        bucketImage = new Texture(Gdx.files.internal("BaileyY.png"));
        healthBar = new Texture(Gdx.files.internal("EmptyHealthBar.png"));
        healthBarSegment = new Texture(Gdx.files.internal("LongerHealthBar.png"));
        firstAidKit = new Texture(Gdx.files.internal("FirstAidKit.png"));

        // load the drop sound effect and the rain background "music"
       // dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        healthKits = new Array<Rectangle>();
        spawnHealthKit();

        transparentBar = new Rectangle();
        transparentBar.width = Gdx.graphics.getWidth();
        transparentBar.height = 1;




    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    private void spawnHealthKit(){
        Rectangle healthKit = new Rectangle();
        healthKit.x = MathUtils.random(0, 800 - 64);
        healthKit.y = 480;
        healthKit.width = 64;
        healthKit.height = 64;
        healthKits.add(healthKit);
        lastDropTimes = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Treats Collected: " + dropsGathered, 0, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        for(Rectangle healthKit : healthKits){
            game.batch.draw(firstAidKit, healthKit.x, healthKit.y);

        }
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();


        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();
        if (TimeUtils.nanoTime() - lastDropTimes > 1000000000)
            spawnHealthKit();


        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();

        game.batch.begin();


        game.batch.draw(healthBar, 625,350 ,200 , 200 );
        totalHealth = 15;
        game.batch.end();
        game.batch.begin();

        game.batch.draw(healthBarSegment, 675, 325, 10*health, 200);
        game.batch.end();








        while (iter.hasNext()) {
            counter++;
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();


            if (raindrop.y + 64 < 0/*||healthKit.y+64<0*/)
                iter.remove();
                //iterAToR.remove();

            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                // dropSound.play();
                iter.remove();

            }

            if (raindrop.overlaps(transparentBar)) {
                health -= 0.3333333;
                if (health <= 0.0000001) {
                    game.setScreen(new LoserScreen(game)); //change code

                }

            }
            if(counter%3==0){
                Iterator<Rectangle> healthBoxIterator = healthKits.iterator();
                while (healthBoxIterator.hasNext()) {
                    Rectangle healthBox = healthBoxIterator.next();
                    healthBox.y -= 100 * Gdx.graphics.getDeltaTime()*2;

                    if (healthBox.overlaps(bucket)) {
                        health += 5; // Adjust the amount of health restored as desired
                        if (health > 15) {
                            health = 15; // Limit the health to a maximum value
                        }
                        healthBoxIterator.remove();
                    }
                }
            }

        }}




    //}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
       // dropSound.dispose();
        rainMusic.dispose();
    }

}
