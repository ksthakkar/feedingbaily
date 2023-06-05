package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameObject {

    private Texture enemyImage;
    private Vector2 enemyPos;
    private Vector2 direction;

    private Player player;

    private boolean patrolForward;

    private boolean dead = false;

    private  Vector2 pointA, pointB, dir, targetPoint;
    float speedy;

    Rectangle hitbox;

    public static enum EnemyType {
        PATROL, MISSILE
    }

    public Enemy(String name, Player player){
        this.player = player;
        enemyImage = new Texture(Gdx.files.internal(name));
        enemyPos = new Vector2(-700, 200);
        direction = new Vector2();

         pointA = new Vector2(); // Starting point
         pointB = new Vector2(); // Destination point

         dir = new Vector2();

         hitbox = new Rectangle();
    }

    public Enemy(Player player){
        this.player = player;
        enemyPos = new Vector2(100, 100);
        direction = new Vector2();

        pointA = new Vector2(); // Starting point
        pointB = new Vector2(); // Destination point

        dir = new Vector2();
        dead = false;

        hitbox = new Rectangle();
        hitbox.set(enemyPos.x, enemyPos.y, enemyImage.getWidth(), enemyImage.getHeight());
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

    public Vector2 getEnemyPos(){
        return enemyPos;
    }

    public void patrol(Vector2 a, Vector2 b, float sp){
        pointA = new Vector2(a.x, a.y); // Starting point
        pointB = new Vector2(b.x, b.y); // Destination point
        dir = pointB.cpy().sub(pointA).nor(); // Direction vector
        speedy = sp;

        enemyPos = new Vector2(pointA.x, pointA.y);
        targetPoint = pointB;
        patrolForward = true;
    }

    public void updatePatrol(float deltaTime){



        enemyPos.add(dir.x * speedy * deltaTime, dir.y * speedy * deltaTime);



        // Check for collision or arrival at point B
        if (enemyPos.dst2(targetPoint) < 10f) {
            // Reverse the patrol direction
            if (targetPoint.equals(pointA)) {
                targetPoint = pointB;
            } else {
                targetPoint = pointA;
            }

            dir = targetPoint.cpy().sub(enemyPos).nor();
            // Reverse direction
        }
    }

    public Rectangle ehitbox(){
        return hitbox;
    }

    private boolean checkCollision(Rectangle rectangle){
        Rectangle enemyRect = new Rectangle(enemyPos.x, enemyPos.y, enemyImage.getWidth(), enemyImage.getHeight());

        return enemyRect.overlaps(rectangle);
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

    void run(){
        if (!dead)
            hitbox.set(enemyPos.x, enemyPos.y, enemyImage.getWidth(), enemyImage.getHeight());
        else
            hitbox = null;
    }

    void die(){
        dead = true;
    }

    public  boolean isDead(){
        return dead;
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
