package io.github.some_example_name.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.Enemy;

public class CombatScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private Stage stage;
    private final Texture backgroundTexture;
    private final Texture enemyTexture;

    public CombatScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        backgroundTexture = new Texture("Backgrounds/combatbg_temp_red.png");
        enemyTexture = new Texture("Enemy/Dandelion/Idle/Idle1.png");

        player.setupAnimations();

        stage = new Stage(new ScreenViewport());
        stage.addActor(player.provideMoveButtons());
        player.setTarget(enemy);
        enemy.setTarget(player);

        stage.addActor(new PauseMenuScreen(game, player, enemy).getPauseButton());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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
        game.batch.draw(backgroundTexture, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        player.draw(game.batch, 1.1f, 2.2f, 3f, 3.4f);
        //game.batch.draw(playerTexture, 1.1f, 2.1f, 3f, 3.4f);
        game.batch.draw(enemyTexture, 4.1f, 2.2f, 3f, 3.4f);
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
        Gdx.input.setInputProcessor(null);
    }

    @Override public void dispose() {
        backgroundTexture.dispose();
        enemyTexture.dispose();
        stage.dispose();
    }

}
