package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {

    Enemy testE;
    Player player;
    final Baily game;
    OrthographicCamera camera;


    Rectangle tempGround;

    ArrayList<Shooter> wrenches = new ArrayList<Shooter>();
    public GameScreen(final Baily game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 320);
        player = new Player();

        tempGround = new Rectangle(0, 50, Gdx.graphics.getWidth(), 20);
        testE = new Enemy("benis2.png", player);

    }
    @Override
    public void show() {

    }
    private void updateWrenches(float delta) {
        Iterator<Shooter> iterator = wrenches.iterator();
        while (iterator.hasNext()) {
            Shooter wrench = iterator.next();
            wrench.update(delta);
            if (wrench.getX() > Gdx.graphics.getHeight() || wrench.getX() < 0) {
                iterator.remove();
            }
        }
    }


    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.2f, 1);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.run();
        testE.advance(100);

        if (player.getY() <= tempGround.y) {
            player.setY(tempGround.y);
            player.setPlayerYVelocity(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(Player.Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(Player.Direction.RIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.getGroundCheckPos().y <= tempGround.y) {
            player.jump();
        }





        camera.update();
        game.batch.begin();

        for(Shooter wrench: wrenches){
            wrench.render(game.batch);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && wrenches.size()<=1) {
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

        //game.font.draw(game.batch, "", 100, 150);
        //game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        //game.batch.draw(player.getTexture(), player.getX(), player.getY());
        player.getSprite().draw(game.batch);


        game.batch.draw(testE.getEnemyImage(), testE.getX(), testE.getY());

        game.batch.end();

       // if (Gdx.input.isTouched()) {
       //     game.setScreen(new GameScreen(game));
       //     dispose();
       // }

    }

    @Override
    public void resize(int width, int height) {

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