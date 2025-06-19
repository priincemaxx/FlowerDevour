package io.github.some_example_name.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.*;
import io.github.some_example_name.enemies.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * quick little something to have access to combat and loot rooms
 * should be easier to implement the actual map when class isnt blank
 */

public class MapScreen implements Screen
{
    public Main game;
    public Player player;
    public Enemy enemy;
    private Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final Texture backgroundTint;
    private Map map;
    private Table table;

    public MapScreen(Main game, Player player)
    {
        this.game = game;
        this.player = player;
        this.map = new Map();

        backgroundTexture = new Texture("Backgrounds/MapBackground.png");
        backgroundTint = new Texture("Backgrounds/backgroundTint.png");

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));
        stage = new Stage(new ScreenViewport());

        stage.addActor(new PauseMenuScreen(game, player, PauseMenuScreen.TO_MAP).getPauseButton());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //table.debug();

        drawMapButtons();
    }

    public void drawMapButtons()
    {
        table.clear();

        for (int row = 0; row < Map.ROWS; row++)
        {
            table.row();
            for (int col = 0; col < Map.MAX_ROW_ROOMS; col++)
            {
                Room room = map.getRoom(row, col);
                if (room != null)
                {
                    Button button = createRoomButton(room);
                    table.add(button).pad(10).width(40).height(40);
                }
                /*else //literally doesn't do anything
                {
                    /// TODO: should add empty space for null rooms
                    table.add().width(40).height(40);
                }*/
            }
        }
    }

    public Button createRoomButton(Room room) //deleted unused arguments
    {
        String roomStyle;
        if (room instanceof LootRoom)
        {
            roomStyle = "lootRoom";
        }
        else if (room instanceof CombatRoom)
        {
            roomStyle = "combatRoom";
        }
        else
        {
            /// temptorary use of boss room; room also doesnt switch to anything, its just empty
            roomStyle = "bossRoom";
        }

        Button button = new Button(skin, roomStyle);

        button.setUserObject(room);

        button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Room clickedRoom = (Room) button.getUserObject();
                switchToRoom(clickedRoom);
            }
        });

        return button;
    }

    private void switchToRoom(Room room)
    {
        if (room instanceof LootRoom)
        {
            game.setScreen(new LootScreen(game, player));
        }
        else if (room instanceof CombatRoom)
        {
            //game.setScreen(new CombatScreen(game, player, enemy));
            game.setScreen(new CombatScreen(game, player));
        }
        else
        {
            System.out.println("Didnt switch!!!");
        }
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(Color.WHITE);

        drawRoom();

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void drawRoom()
    {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.getWorldWidth(), game.getWorldHeight());

        game.batch.end();
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
    }
}
