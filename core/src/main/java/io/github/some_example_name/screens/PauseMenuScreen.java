package io.github.some_example_name.screens;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

//temporary functionality purely for button
public class PauseMenuScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private Stage stage;
    private final Skin skin;
//    private final Texture background;

    public PauseMenuScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));

//        //change bg? idk
//        background = new Texture(Gdx.files.internal("Backgrounds/MainMenuBackground.png"));

    }

    public Table getPauseButton() {
        Button pauseButton = new Button(new Skin(Gdx.files.internal("button/Buttons.json")), "pause");
        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).size(55, 55).pad(10);

        pauseButton.addListener(e -> {
            if (pauseButton.isPressed()) {
                game.setScreen(new MainMenuScreen(game, player, enemy));
            }
            return false;
        });
        return table;
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(Color.BLACK);
//
//        batch.begin();
//        batch.draw();
//        batch.end();
//
//        stage.act(delta);
//        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }


    public void show() {
    }

    @Override
    public void hide(){
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
//        background.dispose();
    }
}
