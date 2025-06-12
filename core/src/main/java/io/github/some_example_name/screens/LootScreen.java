package io.github.some_example_name.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Animations;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.screens.MainMenuScreen;
import io.github.some_example_name.tools.Tool;

public class LootScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private Tool chest;
    private Stage stage;
    private final Texture backgroundTexture;
    private final Texture chestTexture;
    private final ScreenViewport stageViewport;

    public LootScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        backgroundTexture = new Texture("Backgrounds/lootbg_temp.png");
        chestTexture = new Texture("Chest.png");

        player.setupAnimations();

        stageViewport = new ScreenViewport();
        stage = new Stage(stageViewport);
        player.setupAnimations();
        //stage.addActor(player.provideMoveButtons());
        //player.setTarget(enemy);
        //enemy.setTarget(player);

        Button pauseButton = new Button(new Skin(Gdx.files.internal("button/Buttons.json")), "pause");
        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).size(55, 55).pad(10);

        TextButton openChestButton = new TextButton("Open!" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        Table chestTable = new Table();
        chestTable.bottom().pad(10).setFillParent(true);
        chestTable.add(openChestButton).bottom().growX().height(60).padLeft(100).padRight(100).padBottom(20);

        pauseButton.addListener(e -> {
            if (pauseButton.isPressed()) {
                game.setScreen(new MainMenuScreen(game, player, enemy));
            }
            return false;
        });

        openChestButton.addListener(e -> {
            if (openChestButton.isPressed()) {
                player.attackArmMovement();
                //TODO: ANIMATION chest opens
            }
            return false;
        });

        stage.addActor(table);
        stage.addActor(chestTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        player.update(delta);
        drawRoom();

        stage.act();
        stage.draw();
    }

    private void drawRoom() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        player.draw(game.batch, 1.1f, 2.2f, 3f, 3.4f);
        //TODO: ANIMATION
        game.batch.draw(chestTexture, 4.1f, 2.2f, 3f, 3.4f);

        game.batch.end();
    }

    @Override public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override public void pause() {
    }

    @Override public void resume() {
    }

    @Override public void hide() {
    }

    @Override public void dispose() {
        backgroundTexture.dispose();
        //playerTexture.dispose();
        //enemyTexture.dispose();
        stage.dispose();
    }

}
