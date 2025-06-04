package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.enemies.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CombatScreen implements Screen {
    public Main game;
    CombatRoom combatRoom = new CombatRoom();

    Player player;
    Enemy enemy;
    private Stage stage;
    //textures
    Texture backgroundTexture;
    Texture playerTexture;
    Texture enemyTexture;
    //SpriteBatch spriteBatch;
    //viewports
    FillViewport entityViewport;
    //FillViewport backgroundViewport;
    ScreenViewport stageViewport;

    public CombatScreen(Main game, Player player, Enemy punyLeaf) {
        this.game = game;
        this.player = player;
        this.enemy = punyLeaf;

        //params set by startup window size
        //background, viewport and window should all be the same size
        //changing them will require changing size/X/Y values of basically everything visually


        //combatRoom.setBackground("Backgrounds/combatbg_temp_red.png");
        //combatRoom.setBackgroundPath("Backgrounds/combatbg_temp_red.png");
        backgroundTexture = new Texture("Backgrounds/combatbg_temp_red.png");
        playerTexture = new Texture("Gardener/FemaleType1/Idle/Idle1.png");
        enemyTexture = new Texture("Enemy/Dandelion/Idle/Idle1.png");

        //spriteBatch = new SpriteBatch();
        //TODO: sprite grouping?

         //viewport = new FillViewport(worldWidth, worldHeight);
        stageViewport = new ScreenViewport();
        entityViewport = new FillViewport(game.worldWidth, game.worldHeight);

        stage = new Stage(stageViewport);
//        Gdx.input.setInputProcessor(stage);

        stage.addActor(player.provideMoveButtons());
        //stage.addActor(player.provideProgressBars());

        player.setTarget(enemy);
        enemy.setTarget(player);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
//        //switches screens upon spacebar input
//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean keyDown(int keyCode) {
//                if (keyCode == Input.Keys.SPACE) {
//                    game.setScreen(new LootScreen(game, player, enemy));
//                }
//                return true;
//            }
//        });
    }

    @Override
    public void hide(){

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        //combatRoom.drawRoom(backgroundViewport, spriteBatch, backgroundTexture, entityViewport, playerTexture, enemyTexture);
        drawRoom();

        stage.act();
        stage.draw();
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Puny Leaf health: " + enemy.getHealth());
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void drawRoom() {
        ScreenUtils.clear(Color.WHITE);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.worldWidth, game.worldHeight);
        System.out.println("AAAAAAAAAAAAAAAAA");
        game.batch.end();

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(playerTexture, 1.1f, 2.1f, 3f, 3.4f);
        game.batch.draw(enemyTexture, 4.1f, 2.1f, 3f, 3.4f);
        game.batch.end();
    }
}
