package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameObject {

    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private Texture playerImage;
    private Vector2 playerPos;
    private Vector2 groundCheckPos;
    private Vector2 playerVelocity;
    private float gravity;

    private Texture projectileTexture;
    private Sprite projectileSprite;
    private Vector2 projectilePosition;
    private Vector2 projectileVelocity;


    private Vector3 mousePos3d;
    private boolean isProjectileActive;
    private boolean isLeft;


    //health Bar
    float health = 100f; // Current health value
    float maxHealth = 100f; // Maximum health value
    float barWidth = 200f; // Width of the health bar
    float barHeight = 20f; // Height of the health bar
    float barX = (Gdx.graphics.getWidth() - barWidth) - 20; // X-coordinate of the health bar
    float barY = Gdx.graphics.getHeight() - 50f;

    ShapeRenderer shapeRenderer;


    Rectangle hitbox;

    public static enum Direction {
        LEFT, RIGHT
    }

    public Player() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        playerImage = new Texture(Gdx.files.internal("dunlea2.png"));
        //playerImage.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        playerPos = new Vector2(-200, 60);
        playerVelocity = new Vector2();
        sprite = new Sprite(playerImage);


        projectileTexture = new Texture(Gdx.files.internal("wrench2.png"));
        projectileSprite = new Sprite(projectileTexture);

        projectilePosition = new Vector2();
        projectileVelocity = new Vector2();
        mousePos3d = new Vector3();
        isProjectileActive = false;

        groundCheckPos = new Vector2(100, 80);

        hitbox = new Rectangle();



        gravity = 1800f;
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

    public void setPlayerPos(Vector2 playerPos) {
        this.playerPos = playerPos;
    }

    public Vector2 getPlayerVelocity(){
        return playerVelocity;
    }

    public void setPlayerHit(Rectangle playerPos) {
        this.hitbox = playerPos;
    }

    public float getHeight(){
        return sprite.getHeight();
    }

    public float getWidth(){
        return sprite.getWidth();
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

    public Sprite getSprite(){
        return sprite;
    }

    public boolean isLeft(){
        return isLeft;
    }

    public Sprite getProjectileSprite(){
        return  projectileSprite;
    }
    public boolean isProjectileActive(){
        return isProjectileActive;
    }

    public void shoot(OrthographicCamera camera){
            // Set projectile position to player position
            projectilePosition.set(playerPos);

            // Calculate direction from player to mouse position
            mousePos3d.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos3d);

            Vector2 mousePosition = new Vector2(mousePos3d.x, mousePos3d.y);

            Vector2 direction = mousePosition.sub(playerPos).nor();

            // Set projectile velocity based on direction
            float projectileSpeed = 5f;
            projectileVelocity.set(direction.x * projectileSpeed, direction.y * projectileSpeed);

            isProjectileActive = true;
    }


    public void move(Direction d){
        if (d == Direction.LEFT){
            sprite.setFlip(true, false);
            playerPos.x -= 200 * Gdx.graphics.getDeltaTime();

            isLeft = true;
        } else if (d == Direction.RIGHT){
            sprite.setFlip(false, false);
            playerPos.x += 200 * Gdx.graphics.getDeltaTime();

            isLeft =false;
        }
    }

    public void setPlayerYVelocity(float yVelocity) {
        this.playerVelocity.y = yVelocity;
    }

    public void jump(){
        playerVelocity.y = 450;
    }

    public void throwWrench(){

    }

    public Vector2 getGroundCheckPos() {
        return groundCheckPos;
    }

    public void run(){

        if (health <= 0){
            //go to losing screen
        }


        playerVelocity.y -= gravity * Gdx.graphics.getDeltaTime();
        playerPos.add(playerVelocity.cpy().scl(Gdx.graphics.getDeltaTime()));



        groundCheckPos.x = playerPos.x;;
        groundCheckPos.y = playerPos.y - 20;

        sprite.setPosition(playerPos.x, playerPos.y);

        if (isProjectileActive) {
            projectilePosition.add(projectileVelocity);
            projectileSprite.setPosition(projectilePosition.x, projectilePosition.y);
        }

        float currentWidth = (health / maxHealth) * barWidth;

        // Render the background of the health bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.end();

        // Render the current health value as a colored portion of the bar

        if ((currentWidth) > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.GREEN);

            shapeRenderer.rect(barX, barY, currentWidth, barHeight);

            shapeRenderer.end();
        }


        hitbox.set(playerPos.x, playerPos.y, playerImage.getWidth(), playerImage.getHeight());
    }

    public Rectangle phitbox(){
        return hitbox;
    }

    public float myhealth(){
        return  health;
    }
    public void takeDamage(){
        health -= 3;

        if (health < 0){
            health = 0;
        }
    }
    public void getHealth(){
        health += 20;
        if (health > 100){
            health = 100;
        }
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
