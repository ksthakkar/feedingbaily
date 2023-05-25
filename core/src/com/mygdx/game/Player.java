package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

    private Texture playerImage;
    private Vector2 playerPos;
    private Vector2 playerVelocity;
    private float gravity;

    public static enum Direction {
        LEFT, RIGHT
    }

    public Player() {
        playerImage = new Texture(Gdx.files.internal("dunlea2.png"));
        playerPos = new Vector2(100, 100);
        playerVelocity = new Vector2();
        gravity = 800f;
    }

    public void setX(float x){
        playerPos.x = x;
    }
    public float getX(){
        return playerPos.x;
    }

    public void setY(float y){
        playerPos.y = y;
    }

    public float getY(){
        return playerPos.y;
    }

    public Vector2 getPlayerPos(){
        return playerPos;
    }

    public Texture getTexture(){
        return playerImage;
    }


    public void move(Direction d){
        if (d == Direction.LEFT){
            playerPos.x -= 200 * Gdx.graphics.getDeltaTime();
        } else if (d == Direction.RIGHT){
            playerPos.x += 200 * Gdx.graphics.getDeltaTime();
        }

    }

    public void setPlayerYVelocity(float yVelocity) {
        this.playerVelocity.y = yVelocity;
    }

    public void jump(){
        playerVelocity.y = 350;
    }

    public void throwWrench(){

    }

    public void run(){
        playerVelocity.y -= gravity * Gdx.graphics.getDeltaTime();
        playerPos.add(playerVelocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public String getTag() {
        return null;
    }
}
