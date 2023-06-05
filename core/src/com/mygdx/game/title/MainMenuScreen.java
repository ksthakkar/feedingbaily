package com.mygdx.game.title;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Baily;


public class MainMenuScreen implements Screen{
    private Stage stage;


    final Baily game;
    Texture titleScreenImage;
    Texture controlScreenImage;

    OrthographicCamera camera;

    public MainMenuScreen(final Baily game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new FitViewport(640, 640));
        Gdx.input.setInputProcessor(stage);

        // Load your start screen image as a texture
        Texture startScreenTexture = new Texture(Gdx.files.internal("TitleScreens.png"));

        // Create an Image actor from the texture
        Image startScreenImage = new Image(startScreenTexture);

        // Set the size of the image to fit the stage
        startScreenImage.setSize(stage.getWidth(), stage.getHeight());

        // Add the image to the stage
        stage.addActor(startScreenImage);



    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        titleScreenImage = new Texture(Gdx.files.internal("TitleScreens.png"));
        controlScreenImage = new Texture(Gdx.files.internal("ControlScreen.png"));

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(titleScreenImage, 0, 0);

        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {


            game.setScreen(new TutorialScreen(game));
            dispose();

        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    }


    //...Rest of class omitted for succinctness.

}