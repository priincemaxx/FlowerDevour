package io.github.some_example_name.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Animations;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;

import java.util.HashMap;
import java.util.Map;


public class LootScreen implements Screen
{
    public Main game;
    public Player player;
    private final Stage stage;
    private final Stage rewardStage;
    private final Texture backgroundTexture;
    private final Texture backgroundTint;
    private final Texture chestTexture;
    boolean chestOpen = false;

    private Animations chestAnimations;

    public LootScreen(Main game, Player player)
    {
        this.game = game;
        this.player = player;

        Map<String, Float> chestAnimationData = new HashMap<>();
        chestAnimationData.put("ChestOpen", 0.1f);
        chestAnimations = new Animations("atlas/ChestAtlas.atlas", chestAnimationData);

        backgroundTexture = new Texture("Backgrounds/Loot.png");
        backgroundTint = new Texture("Backgrounds/backgroundTint.png");
        chestTexture = new Texture("other/Chest.png");
        //something to act as the item frame
        //TODO: item frame, reward item icon on it

        player.setupAnimations();

        stage = new Stage(new ScreenViewport());
        stage.addActor(new PauseMenuScreen(game, player, PauseMenuScreen.TO_LOOT).getPauseButton());

        TextButton openChestButton = new TextButton("Open!", new Skin(Gdx.files.internal("button/Buttons.json")));
        Table chestTable = new Table();
        chestTable.bottom().pad(10).setFillParent(true);
        chestTable.add(openChestButton).bottom().growX().height(60).padLeft(100).padRight(100).padBottom(20);

        rewardStage = new Stage(new ScreenViewport());

        openChestButton.addListener(e ->
        {
            if (openChestButton.isPressed() && !chestOpen)
            {
                chestOpen = true;
                chestAnimations.performAnimation("ChestOpen");
                player.animateArmMovement();
            }
            return false;
        });

        stage.addActor(chestTable);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(Color.WHITE);
        player.update(delta);
        drawRoom();
        stage.act();
        stage.draw();
        if (chestOpen)
        {
            chestAnimations.update(delta);
            if (player.isAnimationFinished())
            {
                drawRewardScreen();
            }
        }
    }

    private void drawRoom()
    {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        player.draw(game.batch, 1.1f, 2.2f, 3f, 3.4f);
        if (chestOpen)
        {
            chestAnimations.draw(game.batch, 4.1f, 2.2f, 3f, 3.4f);
        } else
        {
            game.batch.draw(chestTexture, 4.1f, 2.2f, 3f, 3.4f);
        }
        game.batch.end();
    }

    //TODO: add reward item, make space on screen for reward item + description
    private void drawRewardScreen()
    {
        Gdx.input.setInputProcessor(rewardStage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTint, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        //TODO: change to item frame + reward item
        game.batch.end();

        Table table = new Table();
        table.setFillParent(true);
        table.center().top().padTop(150);

        table.add(drawTextField("Reward!", "victory")).width(400).height(100).padBottom(20).row();

        TextButton claimReward = new TextButton("Claim reward", new Skin(Gdx.files.internal("button/Buttons.json")));
        table.add(claimReward).pad(100).growX().height(60);

        rewardStage.addActor(table);

        claimReward.addListener(e ->
        {
            if (claimReward.isPressed())
            {
                //TODO: add reward item to inventory
                //maybe another popup/animation for "claimed!" or smthn to delay return to map
                game.setScreen(new MapScreen(game, player));
            }
            return false;
        });

        rewardStage.act();
        rewardStage.draw();
    }

    public TextField drawTextField(String message, String style)
    {
        TextField textField = new TextField(message, new Skin(Gdx.files.internal("button/Buttons.json")), style);
        textField.setAlignment(Align.center);
        return textField;
    }

    @Override
    public void resize(int width, int height)
    {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose()
    {
        backgroundTexture.dispose();
        backgroundTint.dispose();
        chestTexture.dispose();
        stage.dispose();
        rewardStage.dispose();
    }

}
