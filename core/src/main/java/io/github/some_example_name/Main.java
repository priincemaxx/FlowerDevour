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
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter
{
    private Stage stage;
    private Skin skin;
    Player player = new Player(20, 20, "The Player", new Tool(1, "Shovel", new BasicAction(1, -5)));
    Enemy punyLeaf = new Enemy(15, 15, "Puny Leaf", new BasicAction(0, -5));

    @Override
    public void create()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        skin = new Skin(Gdx.files.internal("skin.json"));

        TextButton actButton = new TextButton("Act", skin);
        root.add(actButton);

        actButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Player health: " + player.getHealth());
                System.out.println("Puny Leaf health: " + punyLeaf.getHealth());
                player.doMoveOn(punyLeaf);
                punyLeaf.doMoveOn(player);
            }
        });
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render()
    {
        ScreenUtils.clear(Color.WHITE);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
