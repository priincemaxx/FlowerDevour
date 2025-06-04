package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//player lacks try catch blocks
//graphics
//passive items
//map/rooms/drawing rooms

//buttons and progress bars (health bars) on tables
//sprites in stage?

//scenes: combat, loot, pause menu, main menu, map

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter
{
    //textures
    Texture backgroundTexture;
    Texture playerTexture;
    Texture enemyTexture;
    SpriteBatch spriteBatch;
    //viewports
    FillViewport entityViewport;
    FillViewport backgroundViewport;
    ScreenViewport stageViewport;

    private Stage stage;
    private Skin skin;
    Player player = new Player(20, 20, "The Player",
        new Tool("Shovel", new BasicAction(1, -5, "Healing attack")));
    Enemy punyLeaf = new Enemy(15, 15, "Puny Leaf", new BasicAction(0, -5, "Attack"));

    @Override
    public void create()
    {
        //params set by startup window size
        //background, viewport and window should all be the same size
        //changing them will require changing size/X/Y values of basically everything visually
        int worldWidth = 8;
        int worldHeight = 6;
        backgroundTexture = new Texture("Backgrounds/combatbg_temp_red.png");
        playerTexture = new Texture("Gardener/FemaleType1/Idle/Idle1.png");
        enemyTexture = new Texture("Enemy/Dandelion/Idle/Idle1.png");
        spriteBatch = new SpriteBatch();
        //TODO: sprite grouping

        backgroundViewport = new FillViewport(worldWidth, worldHeight);
        stageViewport = new ScreenViewport();
        entityViewport = new FillViewport(worldWidth, worldHeight);

        stage = new Stage(stageViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(player.provideMoveButtons());
        stage.addActor(player.provideProgressBars());

        player.setTarget(punyLeaf);
        punyLeaf.setTarget(player);
    }

    @Override
    public void resize(int width, int height)
    {
        stageViewport.update(width, height, true);
        backgroundViewport.update(width, height, true);
        entityViewport.update(width, height, true);

        stage.getViewport().update(width, height);
    }

    @Override
    public void render()
    {
        input();
        logic();
        drawCombatRoom();

//        ScreenUtils.clear(Color.WHITE);
        stage.act();
        stage.draw();
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Puny Leaf health: " + punyLeaf.getHealth());
    }

    private void input() {

    }

    private void logic() {

    }

    private void drawCombatRoom() {
        ScreenUtils.clear(Color.BLACK);
        backgroundViewport.apply();
        spriteBatch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        spriteBatch.begin();
        float worldWidth = backgroundViewport.getWorldWidth();
        float worldHeight = backgroundViewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        System.out.println("AAAAAAAAAAAAAAAAA");
        spriteBatch.end();

        entityViewport.apply();
        spriteBatch.setProjectionMatrix(entityViewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(playerTexture, 1.1f, 2.1f, 3f, 3.4f);
        spriteBatch.draw(enemyTexture, 4.1f, 2.1f, 3f, 3.4f);
        spriteBatch.end();

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}

