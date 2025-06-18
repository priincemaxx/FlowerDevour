package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.github.some_example_name.actions.BasicAction;
import io.github.some_example_name.screens.MainMenuScreen;
import io.github.some_example_name.tools.Tool;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game
{
    public SpriteBatch batch;
    public FillViewport viewport;

    public static final int worldWidth = 8;
    public static final int worldHeight = 6;

    public int getWorldWidth()
    {
        return worldWidth;
    }

    public int getWorldHeight()
    {
        return worldHeight;
    }

    Player player = new Player(20, 20, "The Player",
        new Tool("Shovel", new BasicAction(1, -5, "Healing attack")));
    //Enemy punyLeaf = new Enemy(15, 15, "Puny Leaf", new BasicAction(0, -5, "Attack"));


    @Override
    public void create()
    {
        player.rewardTool(GameMaster.provideRandomTool());

        batch = new SpriteBatch();
        viewport = new FillViewport(worldWidth, worldHeight);
        this.setScreen(new MainMenuScreen(this, player));
    }

    public void render()
    {
        super.render();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

}

