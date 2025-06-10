package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.github.some_example_name.actions.BasicAction;
import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.screens.MainMenuScreen;
import io.github.some_example_name.tools.Tool;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game
{
    public SpriteBatch batch;
    public BitmapFont font;
    public FillViewport viewport;
    //make setters getters
    public int worldWidth = 8;
    public int worldHeight = 6;

    Player player = new Player(20, 20, "The Player",
        new Tool("Shovel", new BasicAction(1, -5, "Healing attack")));
    Enemy punyLeaf = new Enemy(15, 15, "Puny Leaf", new BasicAction(0, -5, "Attack"));


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FillViewport(worldWidth, worldHeight);
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        //allows us to switch between screens
        //should be main menu -> start button -> map interface
        this.setScreen(new MainMenuScreen(this, player, punyLeaf));
    }

    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}

