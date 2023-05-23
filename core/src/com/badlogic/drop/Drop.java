package com.badlogic.drop;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.title.MainMenuScreen;


public class Drop extends Game {

    public SpriteBatch batch;
    public BitmapFont font;




    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new MainMenuScreen(this));

    }

    @Override
    public void render() {
        // Clear the screen



        super.render(); // important!

        // Render the stage



    }

}
