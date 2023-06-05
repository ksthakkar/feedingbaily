package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.title.LoserScreen;
import com.mygdx.game.title.WinnerScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.SocketHandler;

public class GameScreen implements Screen {

    Enemy testE;
    Player player;
    final Baily game;
    OrthographicCamera camera;

    ExtendViewport viewport;
    Rectangle tempGround;
    float VIRTUAL_WIDTH, VIRTUAL_HEIGHT;

    ShapeRenderer shapeRenderer;
    int counter = 0;
    int treatCnt = 0;

    ArrayList<Shooter> wrenches = new ArrayList<Shooter>();
    ArrayList<Enemy> beniss = new ArrayList<Enemy>();

    ShapeRenderer vertex1;

    float t_x = -256;
    float t_y = 40;

    ArrayList<Rectangle> maps = new ArrayList<>();

    boolean onGround = true;

    Texture treat, health;

    long lastDropTime, lastDropTimes;

    Sprite dog;

    Array<Rectangle> raindrops;
    Array<Rectangle> healthKits;
    public GameScreen(final Baily game){
        this.game = game;
        camera = new OrthographicCamera();
        VIRTUAL_WIDTH = 480;
        VIRTUAL_HEIGHT = 320;
        camera.setToOrtho(false, 480, 320);
        shapeRenderer = new ShapeRenderer();

        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        player = new Player();

        tempGround = new Rectangle(0, 50, Gdx.graphics.getWidth(), 20);
        //testE = new Enemy("benis2.png", player);

        //testE.patrol(new Vector2(200,200), new Vector2(100,100), 100f);

        treat = new Texture(Gdx.files.internal("treat.png"));
        health = new Texture(Gdx.files.internal("healthk.png"));



        vertex1 = new ShapeRenderer();

        dog = new Sprite(new Texture(Gdx.files.internal("BaileyY.png")));
        dog.setY(387);
        dog.setX(-150);



        maps.add(new Rectangle(-241, 38, 175, 15));

        maps.add(new Rectangle(-241,-325,800, 15));

        maps.add(new Rectangle(-80,-51, 80, 15));
        maps.add(new Rectangle(-15,36, 110, 15));
        maps.add(new Rectangle(83, -152, 55, 15));
        maps.add(new Rectangle(160, -76, 40, 15));
        maps.add(new Rectangle(185, 35, 60, 15));
        maps.add(new Rectangle(434, 35, 125, 15));
        maps.add(new Rectangle(434, -76, 120 ,15));
        maps.add(new Rectangle(360, -126, 55, 15));
        maps.add(new Rectangle(422, -176, 55, 15));
        maps.add(new Rectangle(471, -227, 55, 15));
        maps.add(new Rectangle(408, -264, 55, 15));
        maps.add(new Rectangle(347, -289, 55, 15));
        maps.add(new Rectangle(160, -214, 64, 15));
        maps.add(new Rectangle(410, 261, 150, 15 ));
        maps.add(new Rectangle(-241, 274, 490, 15));
        maps.add(new Rectangle(-103, 337, 65, 15));
        maps.add(new Rectangle(-241, 387, 155, 15));

        maps.add(new Rectangle(250, -31, 300, 15));
        maps.add(new Rectangle(253, 93, 300, 15));
        maps.add(new Rectangle(253, 140, 300 ,15));
        maps.add(new Rectangle(253, 180, 300 ,15));
        maps.add(new Rectangle(253, 227, 300 ,15));

        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        healthKits = new Array<Rectangle>();
        spawnHealthKit();

    }
    @Override
    public void show() {

    }
    private void updateWrenches(float delta) {
        Iterator<Shooter> iterator = wrenches.iterator();
        while (iterator.hasNext()) {
            Shooter wrench = iterator.next();
            wrench.update(delta);

        }
    }

    private void spawnEnemy() {
        Enemy raindrop = new Enemy("benis2.png", player);
        beniss.add(raindrop);
        //lastETime = TimeUtils.nanoTime();
    }

    boolean canSpawnSprite = true;
    void startSpawnTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canSpawnSprite = true;
            }
        }, 4f); // Reset the flag after 2 seconds
    }


    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(-1000, 1000);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    private void spawnHealthKit(){
        Rectangle healthKit = new Rectangle();
        healthKit.x = MathUtils.random(-1000, 1000);
        healthKit.y = 480;
        healthKit.width = 64;
        healthKit.height = 64;
        healthKits.add(healthKit);
        lastDropTimes = TimeUtils.nanoTime();
    }
    void update(float deltaTime) {
        if (canSpawnSprite) {
            spawnEnemy();
            canSpawnSprite = false; // Prevent immediate respawn
            startSpawnTimer(); // Start the timer for the next spawn
        }

        // Other game logic and rendering
    }


    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClearColor(0.1f, 0.6f, 01f, 0.9f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (treatCnt >= 10 && player.phitbox().overlaps(dog.getBoundingRectangle())){
            game.setScreen(new WinnerScreen(game));
        }

        if (player.myhealth() <= 0 || player.getY() < -400){
            game.setScreen(new LoserScreen(game));
        }


        /*Matrix4 originalProjection = new Matrix4(camera.combined);

// Temporarily set the identity matrix to remove the camera transformations
        camera.setToOrtho(false, screenWidth, screenHeight);

// Render the shape at its world position

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(shapeX, shapeY, shapeWidth, shapeHeight);
        shapeRenderer.end();*

// Restore the original projection matrix
        camera.combined.set(originalProjection);
        */

        viewport.apply();


        player.run();
        //testE.run();
        //beniss.add(testE);
        //testE.advance();
        //testE.updatePatrol(delta);



        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();
        if (TimeUtils.nanoTime() - lastDropTimes > 1000000000)
            spawnHealthKit();


        Iterator<Rectangle> iter3 = raindrops.iterator();









        while (iter3.hasNext()) {
            counter++;
            Rectangle raindrop = iter3.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

            if (raindrop.y + 64 < 0/*||healthKit.y+64<0*/)
                iter3.remove();
            //iterAToR.remove();

            if (raindrop.overlaps(player.phitbox())) {
                treatCnt++;
                // dropSound.play();
                iter3.remove();

            }

            if(counter%3==0){
                Iterator<Rectangle> healthBoxIterator = healthKits.iterator();
                while (healthBoxIterator.hasNext()) {
                    Rectangle healthBox = healthBoxIterator.next();
                    healthBox.y -= 100 * Gdx.graphics.getDeltaTime()*2;

                    if (healthBox.overlaps(player.phitbox())) {
                        player.getHealth();
                        healthBoxIterator.remove();
                    }
                }
            }

        }



        for (Rectangle platform : maps){
                if (player.getPlayerVelocity().y < 0 && player.phitbox().overlaps(platform)) {
                    player.setY(platform.y + platform.height);
                    player.setPlayerYVelocity(0);
                }



            //    if (!player.isLeft() && player.phitbox().overlaps(platform)) {
             //       player.setX(platform.x - player.phitbox().width);

             //   }  if (player.isLeft() && player.phitbox().overlaps(platform)) {
              //      player.setX(platform.x + platform.width);
              //      player.getPlayerVelocity().x = 0;

                //}

        }


        float spawnTimer = 0f;

        float spawnInterval = 2f; // Time interval in seconds between sprite spawns

        update(Gdx.graphics.getDeltaTime());



        Iterator<Enemy> iter = beniss.iterator();
        //Iterator<Shooter> iter2 = wrenches.iterator();

        while (iter.hasNext()) {
            Enemy e = iter.next();
            e.advance((float) Math.random() * 300f + 100f);
            e.run();

            Iterator<Shooter> iter2 = wrenches.iterator();

            if (e.hitbox != null) {
                if (player.phitbox().overlaps(e.ehitbox())) {
                    player.takeDamage();
                }
            }
                while (iter2.hasNext()) {
                    Shooter wrench = iter2.next();

                        if (wrench.getWHitbox().overlaps(e.ehitbox())) {
                            //System.out.println("hit");
                            e.die();
                            iter.remove();
                        }
                }




        }


       // if (player.getY() <= tempGround.y) {
       //     player.setY(tempGround.y);
       //     player.setPlayerYVelocity(0);
       // }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            player.move(Player.Direction.LEFT);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            player.move(Player.Direction.RIGHT);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();

        }








        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
        game.batch.begin();

        for(Shooter wrench: wrenches){

            wrench.render(game.batch);


        }

        for (Rectangle raindrop : raindrops) {
            game.batch.draw(treat, raindrop.x, raindrop.y);
        }
        for(Rectangle healthKit : healthKits){
            game.batch.draw(health, healthKit.x, healthKit.y);

        }



        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.isLeft()){
                wrenches.add(new Shooter(player.getX(), player.getY(), game, -500));
            } else {
                wrenches.add(new Shooter(player.getX(), player.getY(), game, 500));
            }


        }
        updateWrenches(delta);
        ArrayList<Shooter> wrenchesToRemove = new ArrayList();

        wrenches.removeAll(wrenchesToRemove);

        game.batch.setProjectionMatrix(camera.combined);
        game.font.draw(game.batch, "Treats: " + treatCnt, player.getX()+160, player.getY() +120);

        //game.font.draw(game.batch, "X Pos:" + t_x, 40, 150);
       // game.font.draw(game.batch, "Y Pos:" + t_y, 40, 100);
        //game.font.draw(game.batch, "", 100, 150);
        //game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        //game.batch.draw(player.getTexture(), player.getX(), player.getY());
        player.getSprite().draw(game.batch);

        dog.draw(game.batch);

        for (Enemy e : beniss){
            //e.advance(2f);

            //if (!e.isDead()){
            game.batch.draw(e.getEnemyImage(), e.getX(), e.getY());
           // }
        }



        game.batch.end();





        vertex1.setProjectionMatrix(camera.combined);
        vertex1.begin(ShapeRenderer.ShapeType.Filled);

        Matrix4 originalTransform = vertex1.getTransformMatrix().cpy();
        vertex1.setTransformMatrix(new Matrix4());




        vertex1.setColor(Color.BLACK);
        vertex1.rect(-241, 38, 175, 15);
        vertex1.rect(-241,-325,800, 15);
        vertex1.rect(-80,-51, 80, 15);
        vertex1.rect(-15,36, 110, 15);
        vertex1.rect(83, -152, 55, 15);
        vertex1.rect(160, -76, 40, 15);
        vertex1.rect(185, 35, 60, 15);
        vertex1.rect(434, 35, 125, 15);
        vertex1.rect(434, -76, 120 ,15);
        vertex1.rect(360, -126, 55, 15);
        vertex1.rect(422, -176, 55, 15);
        vertex1.rect(471, -227, 55, 15);
        vertex1.rect(408, -264, 55, 15);
        vertex1.rect(347, -289, 55, 15);
        vertex1.rect(160, -214, 64, 15);
        vertex1.rect(410, 261, 150, 15 );
        vertex1.rect(-241, 274, 490, 15);
        vertex1.rect(-103, 337, 65, 15);
        vertex1.rect(-241, 387, 155, 15);

        vertex1.rect(
                250, -31, 300, 15);
        vertex1.rect(253, 93, 300, 15);
        vertex1.rect(253, 140, 300 ,15);
        vertex1.rect(253, 180, 300 ,15);
        vertex1.rect(253, 227, 300 ,15);

        vertex1.end();
        vertex1.setProjectionMatrix(originalTransform);



       // if (Gdx.input.isTouched()) {
       //     game.setScreen(new GameScreen(game));
       //     dispose();
       // }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        game.batch.dispose();
        player.getTexture().dispose();
    }
}
