package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameObject {

    private Texture enemyImage;
    private Vector2 enemyPos;
    private Vector2 direction;

    private Player player;

    public Enemy(String name, Player player){
        this.player = player;
        enemyImage = new Texture(Gdx.files.internal(name));
        enemyPos = new Vector2(300, 100);
        direction = new Vector2();
    }

    public Texture getEnemyImage(){
        return enemyImage;
    }

    public float getX(){
        return enemyPos.x;
    }

    public float getY(){
        return enemyPos.y;
    }

    public void advance(float speed){
        direction.set(player.getPlayerPos()).sub(enemyPos).nor();

        // Update the position based on the direction and speed
        enemyPos.add(direction.x * speed * Gdx.graphics.getDeltaTime(), direction.y * speed * Gdx.graphics.getDeltaTime());

        // Update the sprite's position
        //sprite.setPosition(position.x, position.y);
    }

    private double getDistanceTo(){
        return Math.sqrt(Math.pow(Math.abs(enemyPos.x - player.getX()), 2) + Math.pow(Math.abs(enemyPos.y - player.getY()), 2));
    }

    void attack(){

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
