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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private Stage stage;
    private final Skin skin;

    private final Texture background;
    private SpriteBatch batch;

    public MainMenuScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

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
        /*
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "press space to switch!",  4, 3);
        game.font.draw(game.batch, "--- MAIN MENU ---",  4, 4);
        game.batch.end();
         */
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


    //called when this screen becomes current screen
    public void show() {
        /*
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    //should change to map? tutorial?
                    game.setScreen(new CombatScreen(game, player, enemy));
                }
                return true;
            }
        });

         */
    }

    //hides screen+input when another screen is called
    @Override
    public void hide(){
        /*
        Gdx.input.setInputProcessor(null);

         */
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
