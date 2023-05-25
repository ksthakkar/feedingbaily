package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.nio.channels.spi.SelectorProvider;

public class Baily extends Game {

    public SpriteBatch batch;
    public BitmapFont font;




    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new GameScreen(this));

    }

    @Override
    public void render() {
        // Clear the screen



        super.render(); // important!

        // Render the stage



    }
}
