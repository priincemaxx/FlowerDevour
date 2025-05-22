package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter
{
    private Stage stage;
    private Stage stage2;
    private Skin skin;
    Player player = new Player(20, 20, "The Player",
        new Tool(1, "Shovel", new BasicAction(1, -5, "Healing attack")));
    Enemy punyLeaf = new Enemy(15, 15, "Puny Leaf", new BasicAction(0, -5, "Attack"));

    @Override
    public void create()
    {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        stage2 = new Stage(new FitViewport(1920, 1080));

        Table table = new Table();
        TextButton button = new TextButton("BEEG TEST", new Skin(Gdx.files.internal("skin.json")));
        table.addActor(button);
        stage2.addActor(table);

        int tmp = player.rewardTool(new Tool (2, "test", new BasicAction(0, -1, "test")));
        player.equipTool(tmp);

        stage.addActor(player.provideMoveButtons());

        player.setTarget(punyLeaf);
        punyLeaf.setTarget(player);
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height);
        stage2.getViewport().update(width, height);
    }

    @Override
    public void render()
    {
        ScreenUtils.clear(Color.WHITE);
        stage.act();
        stage.draw();
        stage2.act();
        stage2.draw();
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Puny Leaf health: " + punyLeaf.getHealth());
    }

    @Override
    public void dispose()
    {
        stage.dispose();
        stage2.dispose();
    }
}
