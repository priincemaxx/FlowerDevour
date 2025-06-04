package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture enemyTexture;
    ///this camera renders a 2D view, no perspective
    private OrthographicCamera camera;

    Player player;
    Enemy punyLeaf;

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
        
        player = new Player(20, 20, "The Player", playerTexture,
            new Tool(1, "Shovel", new BasicAction(1, -5, "Healing attack")));
        punyLeaf = new Enemy(15, 15, "Puny Leaf", enemyTexture, new BasicAction(0, -5, "Attack"));
        //TODO: sprite grouping

        backgroundViewport = new FillViewport(worldWidth, worldHeight);
        stageViewport = new ScreenViewport();
        entityViewport = new FillViewport(worldWidth, worldHeight);

        stage = new Stage(stageViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(player.provideMoveButtons());
        stage.addActor(player.provideProgressBars());
        /*
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        ///sets camera's viewport size, defines the games world coordinate system
        ///if set to "true" everything is upside down💀
        /// using width and height variables instead of hardcoding the 1000x500, same for FitViewport
        camera.setToOrtho(false, width, height);
        camera.update();

        playerTexture = new Texture(Gdx.files.internal("Gardener/FemaleType1/Idle/Idle1.png"));
        enemyTexture = new Texture(Gdx.files.internal("Enemy/Dandelion/Idle/Idle1.png"));

        player = new Player(20, 20, "The Player", playerTexture,
            new Tool(1, "Shovel", new BasicAction(1, -5, "Healing attack")));
        punyLeaf = new Enemy(15, 15, "Puny Leaf", enemyTexture, new BasicAction(0, -5, "Attack"));

        batch = new SpriteBatch();

        ///using FitViewport constructor that requires a camera
        ///now both game rendering and UI share the same coordinate system and camera
        stage = new Stage(new FitViewport(width, height, camera));
        Gdx.input.setInputProcessor(stage);

        int tmp = player.rewardTool(new Tool (2, "test", new BasicAction(0, -1, "test")));
        player.equipTool(tmp);

        Table root = new Table();
        root.add(new Button(new Skin(Gdx.files.internal("skin.json")))).fill();

        Table newTable = new Table();
        root.add(newTable);
        newTable.add(new Button(new Skin(Gdx.files.internal("skin.json"))));
        newTable.add(new Button(new Skin(Gdx.files.internal("skin.json"))));

        stage.addActor(root);
        */

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
    public void render() //FIXME: VIEWPORTS!!!!!!!
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
        batch.dispose();
        stage.dispose();
    }
}
