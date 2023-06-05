package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Shooter extends GameObject {
    final Baily game;

    public int SPEED = 500;
    public static final int DEFAULT_X = 20;
    private static Texture texture;
    float x, y;
    public boolean remove = false;
    Texture horWrench;
    Texture verWrench;
    TextureRegion textureRegion;
    TextureRegion[] frames; // Array to store the frames of the animation
    Animation<TextureRegion> animation;
    float stateTime;

    Rectangle hitbox;

    public Shooter(float x, float y, final Baily game, int speed) {
        SPEED = speed;
        this.game = game;
        this.x = x; // need to change for actual game
        this.y = y; // need to change for actual game
        texture = new Texture(Gdx.files.internal("exThrow.png"));
        horWrench = new Texture(Gdx.files.internal("BetterWrenchHorizontal.png"));
        verWrench = new Texture(Gdx.files.internal("BetterWrenchVertical.png"));
        hitbox = new Rectangle();
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("ThrownWrench.png")));

        int frameCols = 1; // Number of columns in the GIF
        int frameRows = 2; // Number of rows in the GIF
        int frameWidth = textureRegion.getRegionWidth() / frameCols;
        int frameHeight = textureRegion.getRegionHeight() / frameRows;

        TextureRegion[][] splitRegions = textureRegion.split(frameWidth, frameHeight);
        frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = splitRegions[i][j];
            }
        }

        float frameDuration = 0.3f; // Duration of each frame in seconds
        animation = new Animation<>(frameDuration, frames);
        stateTime = 0f; // Accumulated time for the animation

        hitbox.set(x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    public float getY() {
        return y;
    }
    public float getX(){
        return x;
    }

    public Rectangle getWHitbox(){
        return hitbox;
    }
    public void update(float deltaTime) {
        hitbox.set(x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());

        x += SPEED * deltaTime;

    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate the elapsed time
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true); // Get the current frame

        batch.draw(currentFrame, x, y); // Draw the current frame at the specified position
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