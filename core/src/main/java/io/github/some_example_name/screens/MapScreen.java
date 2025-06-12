package io.github.some_example_name.screens;

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

/**
 * quick little something to have access to combat and loot rooms
 * should be easier to implement the actual map when class isnt blank
 */

public class MapScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private Stage stage;
    private final Skin skin;
    private final PauseMenuScreen pauseButton;


    public MapScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        pauseButton = new PauseMenuScreen(game, player, enemy);

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(pauseButton.getPauseButton());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        TextButton lootButton = new TextButton("Loot!", skin);
        TextButton combatButton = new TextButton("Fight!", skin);
        table.add(lootButton);
        table.add(combatButton);

        lootButton.addListener(e -> {
            if (lootButton.isPressed()) {
                game.setScreen(new LootScreen(game, player, enemy));
            }
            return false;
        });

        combatButton.addListener(e -> {
            if (combatButton.isPressed()) {
                game.setScreen(new CombatScreen(game, player, enemy));
            }
            return false;
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    //called when this screen becomes current screen
    public void show() {
//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean keyDown(int keyCode) {
//                if (keyCode == Input.Keys.SPACE) {
//                    game.setScreen(new CombatScreen(game, player, enemy));
//                }
//                return true;
//            }
//        });
    }

    //hides screen+input when another screen is called
    @Override
    public void hide(){
        //Gdx.input.setInputProcessor(null);
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
    }
}
