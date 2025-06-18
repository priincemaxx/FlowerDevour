package io.github.some_example_name.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;
import io.github.some_example_name.enemies.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;

//temporary functionality purely for button
public class PauseMenuScreen implements Screen
{
    public Main game;
    public Player player;
    public Enemy enemy;
    private final Stage stage;
    private final Skin skin;
    private final Texture background;
    private int returnCase;

    public static final int TO_MAP = 1;
    public static final int TO_COMBAT = 2;
    public static final int TO_LOOT = 3;


    /**
     * @param game Mandatory parameter for Screen class.
     * @param player Initialized player.
     * @param enemy Optional. An initialized enemy.
     * @param returnCase A choice between TO_MAP, TO_COMBAT, TO_LOOT.
     */
    public PauseMenuScreen(Main game, Player player, Enemy enemy, int returnCase)
    {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        this.returnCase = returnCase;

        this.stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));

        background = new Texture(Gdx.files.internal("Backgrounds/Pause.png"));
    }

    /**
     * @param game Mandatory parameter for Screen class.
     * @param player Initialized player.
     * @param returnCase A choice between TO_MAP, TO_COMBAT, TO_LOOT.
     */
    public PauseMenuScreen(Main game, Player player, int returnCase)
    {
        this.game = game;
        this.player = player;
        this.returnCase = returnCase;

        this.stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));

        background = new Texture(Gdx.files.internal("Backgrounds/Pause.png"));

    }


    public Table getPauseButton()
    {
        Button pauseButton = new Button(new Skin(Gdx.files.internal("button/Buttons.json")), "pause");
        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).size(55, 55).pad(10);

        pauseButton.addListener(e ->
        {
            if (pauseButton.isPressed())
            {
                game.setScreen(new PauseMenuScreen(game, player, enemy, returnCase));
            }
            return false;
        });
        return table;
    }

    @Override
    public void render(float delta)
    {
//        Gdx.gl.glClearColor(Color.BLACK);
//
//        game.batch.begin();
//        game.batch.draw();
//        game.batch.end();

//        stage.act(delta);
//        stage.draw();
        drawPause();
    }

    public void drawPause()
    {
        Gdx.input.setInputProcessor(stage);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.getWorldWidth(), game.getWorldHeight());
        game.batch.end();

        stage.act();
        stage.draw();

        Table table = new Table();
        TextButton backToMenu = new TextButton("Exit to main menu", new Skin(Gdx.files.internal("button/Buttons.json")));
        TextButton continueGame = new TextButton("Continue", new Skin(Gdx.files.internal("button/Buttons.json")));
        table.setFillParent(true);
        table.add(continueGame).pad(50).growX().height(60).uniform();
        table.row();
        table.add(backToMenu).pad(50).growX().height(60).uniform();
        stage.addActor(table);

        backToMenu.addListener(e ->
        {
            if (backToMenu.isPressed())
            {
                game.setScreen(new MainMenuScreen(game, player));
            }
            return false;
        });
        continueGame.addListener(e ->
        {
            if (continueGame.isPressed())
            {
                switch (returnCase)
                {
                    case TO_MAP:
                        game.setScreen(new MapScreen(game, player));
                        break;
                    case TO_COMBAT:
                        game.setScreen(new CombatScreen(game, player, enemy));
                        break;
                    case TO_LOOT:
                        game.setScreen(new LootScreen(game, player));
                        break;
                }

            }
            return false;
        });
    }


    @Override
    public void resize(int width, int height)
    {
        game.viewport.update(width, height, true);
    }


    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        stage.dispose();
        skin.dispose();
        background.dispose();
    }
}
