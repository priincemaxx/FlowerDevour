package io.github.some_example_name.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.CombatRoom;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.Enemy;

public class CombatScreen implements Screen {
    public Main game;
    public Player player;
    //TODO: take enemy from CombatRoom (once map exists)
    //string of enemy (type? name?) will need to be passed into enemy.draw (animation)
    public CombatRoom combatRoom;
    public Enemy enemy;
    private final Stage stage;
    private final Stage gameEndStage;
    private final Texture backgroundTexture;
    private final Texture backgroundTint;
    //private final Texture victory; //couldnt get png
    private final Texture defeat;

    public CombatScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        enemy.setTurnOver(true);

        backgroundTexture = new Texture("Backgrounds/combatbg_temp_red.png");
        backgroundTint = new Texture("Backgrounds/backgroundTint.png");
        defeat = new Texture("other/Defeat_temp.png");

        player.setupAnimations();
        /// will have to change based on weapon type
        player.setDefaultAnimation("GardenerPolearmIdle");
        enemy.setupAnimations("Dandelion Trooper");

        gameEndStage = new Stage(new ScreenViewport());
        stage = new Stage(new ScreenViewport());
        stage.addActor(player.provideMoveButtons(enemy.getTurnOver()));
        player.setTarget(enemy);
        enemy.setTarget(player);
        stage.addActor(new PauseMenuScreen(game, player, enemy, 2).getPauseButton());

        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(new PauseMenuScreen(game, player, enemy, 2).getPauseButton()).size(40, 40).pad(10);
        Button inventoryButton =new Button(new Skin(Gdx.files.internal("button/Buttons.json")), "inventory");

        inventoryButton.addListener(e ->
        {
            if (inventoryButton.isPressed())
            {
                game.setScreen(new InventoryScreen(game, player));
            }
            return false;
        });

        table.add(inventoryButton);
        stage.addActor(table);
    }

    public CombatScreen(Main game, Player player) {
        combatRoom = new CombatRoom();
        this.game = game;
        this.player = player;
        enemy = combatRoom.getRandomEnemy();
        enemy.setTurnOver(true);

        backgroundTexture = new Texture("Backgrounds/Combat.png");
        backgroundTint = new Texture("Backgrounds/backgroundTint.png");
        defeat = new Texture("other/Defeat_temp.png");

        player.setupAnimations();
        /// will have to change based on weapon type
        player.setDefaultAnimation("GardenerPolearmIdle");
        enemy.setupAnimations(enemy.getName());

        gameEndStage = new Stage(new ScreenViewport());
        stage = new Stage(new ScreenViewport());
        stage.addActor(player.provideMoveButtons(enemy.getTurnOver()));
        player.setTarget(enemy);
        enemy.setTarget(player);
        stage.addActor(new PauseMenuScreen(game, player, enemy, 2).getPauseButton());

        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(new PauseMenuScreen(game, player, enemy, 2).getPauseButton()).size(40, 40).pad(10);
        Button inventoryButton = new Button(new Skin(Gdx.files.internal("button/Buttons.json")), "inventory");

        inventoryButton.addListener(e ->
        {
            if (inventoryButton.isPressed())
            {
                game.setScreen(new InventoryScreen(game, player));
            }
            return false;
        });

        table.add(inventoryButton);
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
        enemy.update(delta);
        drawRoom();

        stage.act();
        stage.draw();

        if(enemy.isDead()) {
            //TODO: delay for animation
            drawVictoryScreen();
            player.setTurnOver(false);
        }
        if(player.isDead()) {
            //TODO: delay
            drawDefeatScreen();
        }
        if(player.getTurnOver() && player.isAnimationFinished()){
            enemy.setTurnOver(true);
            enemy.animateAttack();
            enemy.doMove(player);
            player.animateTakePolearmDamage();
            player.setTurnOver(false);
        }
        // Debug info
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Enemy health: " + enemy.getHealth());
    }

    private void drawRoom() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        player.provideHealthBar(game.batch);
        enemy.provideHealthBar(game.batch);

        if(player.isDead()) {
            //TODO: player death animation
        } else player.draw(game.batch, 1.1f, 2.2f, 3f, 3.4f);
        if(enemy.isDead()) {
            //TODO: enemy death animation
        } else enemy.draw(game.batch, 4.1f, 2.2f, 3f, 3.4f);

        game.batch.end();
    }

    //TODO: add reward item, make space on screen for reward item + description
    private void drawVictoryScreen() {
        Gdx.input.setInputProcessor(gameEndStage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTint, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        //TODO: change to item frame + reward item and change to victory
        game.batch.draw(defeat, 1, 2.5f, 6, 2);
        game.batch.end();

        gameEndStage.act();
        gameEndStage.draw();

        Table table = new Table();
        TextButton claimReward = new TextButton("Claim reward" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        table.setFillParent(true);
        table.bottom().row();
        table.add(claimReward).pad(100).growX().height(60);
        gameEndStage.addActor(table);

        claimReward.addListener(e -> {
            if (claimReward.isPressed()) {
                //TODO: add reward item to inventory
                //maybe another popup/animation for "claimed!" or smthn to delay return to map
                game.setScreen(new MapScreen(game, player));
            }
            return false;
        });
    }

    private void drawDefeatScreen() {
        Gdx.input.setInputProcessor(gameEndStage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTint, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        //TODO: change to item frame + reward item and change to victory
        game.batch.draw(defeat, 1, 2.5f, 6, 2);
        game.batch.end();

        gameEndStage.act();
        gameEndStage.draw();

        Table table = new Table();
        TextButton returnToMenu = new TextButton("Return to main menu" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        table.setFillParent(true);
        table.bottom().row();
        table.add(returnToMenu).pad(100).growX().height(60);
        gameEndStage.addActor(table);

        returnToMenu.addListener(e -> {
            if (returnToMenu.isPressed()) {
                game.setScreen(new MainMenuScreen(game, player));
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
        backgroundTint.dispose();
        stage.dispose();
        gameEndStage.dispose();
    }

}
