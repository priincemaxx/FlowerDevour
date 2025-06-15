package io.github.some_example_name.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;

//temporary functionality purely for button
public class PauseMenuScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private final Stage stage;
    private final Skin skin;
    private final Texture background;
    private int roomId;

    public PauseMenuScreen(Main game, Player player, Enemy enemy, int roomId) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        this.roomId = roomId;

        this.stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));

        background = new Texture(Gdx.files.internal("Backgrounds/backgroundTint.png"));

    }


    public Table getPauseButton() {
        Button pauseButton = new Button(new Skin(Gdx.files.internal("button/Buttons.json")), "pause");
        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).size(55, 55).pad(10);

        pauseButton.addListener(e -> {
            if (pauseButton.isPressed()) {
                game.setScreen(new PauseMenuScreen(game, player, enemy, roomId));
            }
            return false;
        });
        return table;
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(Color.BLACK);
//
//        game.batch.begin();
//        game.batch.draw();
//        game.batch.end();

//        stage.act(delta);
//        stage.draw();
        drawPause();
    }

    public void drawPause() {
        Gdx.input.setInputProcessor(stage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        game.batch.end();

        stage.act();
        stage.draw();

        Table table = new Table();
        TextButton backToMenu = new TextButton("Exit to main menu" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        TextButton continueGame = new TextButton("Continue" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        table.setFillParent(true);
        table.bottom().row();
        table.add(backToMenu).pad(100).growX().height(60).uniform();
        table.row();
        table.add(continueGame).pad(100).growX().height(60).uniform();
        stage.addActor(table);

        backToMenu.addListener(e -> {
            if (backToMenu.isPressed()) {
                game.setScreen(new MainMenuScreen(game, player, enemy));
            }
            return false;
        });
        continueGame.addListener(e -> {
            if (continueGame.isPressed()) {
                switch(roomId){
                    //map
                    case 1: game.setScreen(new MapScreen(game, player, enemy)); break;
                    //combat
                    case 2: game.setScreen(new CombatScreen(game, player, enemy)); break;
                    //loot
                    case 3: game.setScreen(new LootScreen(game, player, enemy)); break;
                }

            }
            return false;
        });
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
    }
}
