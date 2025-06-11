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

public class CombatScreen implements Screen {
    private final Main game;
    private final Player player;
    private final Enemy enemy;
    private final Stage stage;
    private final Texture backgroundTexture;
    //private final Texture playerTexture;
    private final Texture enemyTexture;
    private final ScreenViewport stageViewport;

    public CombatScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        backgroundTexture = new Texture("Backgrounds/combatbg_temp_red.png");
        //playerTexture = new Texture("Gardener/FemaleType1/Idle/Idle1.png");
        Animations playerAnimations = new Animations("atlas/FemaleType1Atlas.atlas", "EmptyIdle", 0.8f);
        player.setAnimations(playerAnimations);

        enemyTexture = new Texture("Enemy/Dandelion/Idle/Idle1.png");

        stageViewport = new ScreenViewport();
        stage = new Stage(stageViewport);
        stage.addActor(player.provideMoveButtons());
        player.setTarget(enemy);
        enemy.setTarget(player);

        Skin skin = new Skin(Gdx.files.internal("button/Buttons.json"));
        Button pauseButton = new Button(skin, "pause");

        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).size(40, 40).pad(10);

        pauseButton.addListener(e -> {
            if (pauseButton.isPressed()) {
                game.setScreen(new MainMenuScreen(game, player, enemy));
            }
            return false;
        });

        stage.addActor(table);
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

        // Debug info
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Enemy health: " + enemy.getHealth());
    }

    private void drawRoom() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.worldWidth, game.worldHeight);
        player.draw(game.batch, 1.1f, 2.1f, 3f, 3.4f);
        //game.batch.draw(playerTexture, 1.1f, 2.1f, 3f, 3.4f);
        game.batch.draw(enemyTexture, 4.1f, 2.1f, 3f, 3.4f);
        player.provideHealthBar(game.batch);
        enemy.provideHealthBar(game.batch);
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
        enemyTexture.dispose();
        stage.dispose();
    }

}
