package io.github.some_example_name.screens;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;

public class MainMenuScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private final Stage stage;
    private final Skin skin;

    private final Texture background;
    private final SpriteBatch batch;

    public MainMenuScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        this.stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));

        background = new Texture(Gdx.files.internal("Backgrounds/MainMenuBackground.png"));
        batch = new SpriteBatch();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton startButton = new TextButton("Start", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(startButton).pad(10).row();
        table.add(exitButton).pad(10);

        startButton.addListener(e -> {
            if (startButton.isPressed()) {
                game.setScreen(new MapScreen(game, player, enemy));
            }
            return false;
        });

        exitButton.addListener(e -> {
            if (exitButton.isPressed()) {
                Gdx.app.exit();
            }
            return false;
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }


    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
        batch.dispose();
    }
}
