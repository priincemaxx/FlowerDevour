package io.github.some_example_name.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.tools.ToolContainer;

import static java.lang.Thread.sleep;

public class LootScreen implements Screen {
    public Main game;
    public Player player;
    public Enemy enemy;
    private ToolContainer chest;
    private final Stage stage;
    private final Stage rewardStage;
    private final Texture backgroundTexture;
    private final Texture backgroundTint;
    private final Texture chestTexture;
    boolean chestOpen = false;

    private final Texture temporary;

    public LootScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        backgroundTexture = new Texture("Backgrounds/lootbg_temp.png");
        backgroundTint = new Texture("Backgrounds/backgroundTint.png");
        chestTexture = new Texture("other/Chest.png");
        //something to act as the item frame
        //TODO: item frame, reward item icon on it
        temporary = new Texture("other/Defeat_temp.png");

        player.setupAnimations();

        stage = new Stage(new ScreenViewport());
        stage.addActor(new PauseMenuScreen(game, player, enemy).getPauseButton());
        player.setupAnimations();

        TextButton openChestButton = new TextButton("Open!" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        Table chestTable = new Table();
        chestTable.bottom().pad(10).setFillParent(true);
        chestTable.add(openChestButton).bottom().growX().height(60).padLeft(100).padRight(100).padBottom(20);

        rewardStage = new Stage(new ScreenViewport());

        openChestButton.addListener(e -> {
            if (openChestButton.isPressed()) {
                chestOpen = true;
            }
            return false;
        });

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

        if(chestOpen) {
            player.attackArmMovement();
            //TODO: animation delay
            //TODO: ANIMATION chest opens
            drawRewardScreen();
        }
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

    //TODO: add reward item, make space on screen for reward item + description
    private void drawRewardScreen() {
        Gdx.input.setInputProcessor(rewardStage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTint, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        //TODO: change to item frame + reward item
        game.batch.draw(temporary, 1, 2.5f, 6, 2);
        game.batch.end();

        rewardStage.act();
        rewardStage.draw();

        Table table = new Table();
        TextButton claimReward = new TextButton("Claim reward" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        table.setFillParent(true);
        table.bottom().row();
        table.add(claimReward).pad(100).growX().height(60);
        rewardStage.addActor(table);

        claimReward.addListener(e -> {
            if (claimReward.isPressed()) {
                //TODO: add reward item to inventory
                //maybe another popup/animation for "claimed!" or smthn to delay return to map
                game.setScreen(new MapScreen(game, player, enemy));
            }
            return false;
        });
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
        stage.dispose();
    }

}
