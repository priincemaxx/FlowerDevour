package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    private Skin skin;
    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture enemyTexture;

    Player player;
    Enemy punyLeaf;

    @Override
    public void create()
    {
        playerTexture = new Texture(Gdx.files.internal("Gardener/FemaleType1/Idle/Idle1.png"));
        enemyTexture = new Texture(Gdx.files.internal("Enemy/Dandelion/Idle/Idle1.png"));

        player = new Player(20, 20, "The Player", playerTexture,
            new Tool(1, "Shovel", new BasicAction(1, -5, "Healing attack")));
        punyLeaf = new Enemy(15, 15, "Puny Leaf", enemyTexture, new BasicAction(0, -5, "Attack"));

        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(1000, 500));
        Gdx.input.setInputProcessor(stage);

        int tmp = player.rewardTool(new Tool (2, "test", new BasicAction(0, -1, "test")));
        player.equipTool(tmp);

        //Table testTable = new Table();

        //Table playerButtons = player.provideMoveButtons();

        //testTable.add(playerButtons);
        //testTable.add(new Button(new Skin(Gdx.files.internal("skin.json"))));
        //playerButtons.add(new Button(new Skin(Gdx.files.internal("skin.json"))));

        //stage.addActor(testTable);
        //FIXME: clean up goddamn viewport garbage
        Table root = new Table();
        root.add(new Button(new Skin(Gdx.files.internal("skin.json")))).fill();

        Table newTable = new Table();
        root.add(newTable);
        newTable.add(new Button(new Skin(Gdx.files.internal("skin.json"))));
        newTable.add(new Button(new Skin(Gdx.files.internal("skin.json"))));

        stage.addActor(root);

        player.setTarget(punyLeaf);
        punyLeaf.setTarget(player);
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render() //FIXME: VIEWPORTS!!!!!!!
    {
        ScreenUtils.clear(Color.WHITE);

        batch.begin();

        batch.draw(player.getTexture(), 0, 200);
        batch.draw(punyLeaf.getTexture(), 300, 200);

        batch.end();

        stage.act();
        stage.draw();
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Puny Leaf health: " + punyLeaf.getHealth());
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        stage.dispose();
    }
}
