package io.github.some_example_name.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    //perhaps tint should be moved to game master
    private final Texture backgroundTint;
    private final Texture enemyTexture;
    //private final Texture victory;
    private final Texture defeat;

    public CombatScreen(Main game, Player player, Enemy enemy) {
        //this.combatRoom = combatRoom;
        //this.enemy = combatRoom.enemy?;
        this.game = game;
        this.player = player;
        this.enemy = enemy;

        backgroundTexture = new Texture("Backgrounds/combatbg_temp_red.png");
        backgroundTint = new Texture("Backgrounds/backgroundTint.png");
        enemyTexture = new Texture("Enemy/Dandelion/Idle/Idle1.png");
        //victory = new Texture("other/Victory_temp.png"); //i couldnt get image
        defeat = new Texture("other/Defeat_temp.png");

        player.setupAnimations();

        gameEndStage = new Stage(new ScreenViewport());

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

        if(enemy.isDead()) {
            drawVictoryScreen();
        }
        if(player.isDead()) {
            drawDefeatScreen();
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
        //game.batch.draw(playerTexture, 1.1f, 2.1f, 3f, 3.4f);
        if(enemy.isDead()) {
            //TODO: enemy death animation
            //for now it just vanishes
            //i dont know if enemy animation will delay victory screen
        } else game.batch.draw(enemyTexture, 4.1f, 2.2f, 3f, 3.4f);



        game.batch.end();
    }

    //TODO: add reward item, make space on screen for reward item + description
    private void drawVictoryScreen() {
        Gdx.input.setInputProcessor(gameEndStage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTint, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        //TODO: change to victory
        game.batch.draw(defeat, 1, 2.5f, 6, 2);
        game.batch.end();

        gameEndStage.act();
        gameEndStage.draw();

        Table victoryButtons = new Table();
        TextButton claimReward = new TextButton("Claim reward" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        victoryButtons.setFillParent(true);
        victoryButtons.bottom().row();
        victoryButtons.add(claimReward).pad(100).growX().height(60);
        gameEndStage.addActor(victoryButtons);

        claimReward.addListener(e -> {
            if (claimReward.isPressed()) {
                //TODO: should enemy be passed to map?
                game.setScreen(new MapScreen(game, player, enemy));
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
        game.batch.draw(defeat, 1, 2.5f, 6, 2);
        game.batch.end();

        gameEndStage.act();
        gameEndStage.draw();

        Table defeatButtons = new Table();
        TextButton mainMenu = new TextButton("Return to main menu" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        //dunno if we need that button
        //TextButton playAgain = new TextButton("Play again?" ,new Skin(Gdx.files.internal("button/Buttons.json")));
        defeatButtons.setFillParent(true);
        defeatButtons.bottom().row();
        defeatButtons.add(mainMenu).pad(100).growX().height(60);
        //defeatButtons.add(playAgain).pad(100).growX().height(60).uniform();
        gameEndStage.addActor(defeatButtons);

        mainMenu.addListener(e -> {
            if (mainMenu.isPressed()) {
                //TODO: should enemy be passed to map?
                game.setScreen(new MainMenuScreen(game, player, enemy));
            }
            return false;
        });
//        playAgain.addListener(e -> {
//            if (playAgain.isPressed()) {
//                //TODO: should enemy be passed to map?
//                game.setScreen(new MapScreen(game, player, enemy));
//            }
//            return false;
//        });
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
        gameEndStage.dispose();
        backgroundTint.dispose();
    }

}
