package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Player;

import static java.lang.Thread.sleep;

public class InventoryScreen implements Screen
{

    Main game;
    Player player;

    final Skin skin;
    Stage stage;

    Table buttons;

    public InventoryScreen(Main game, Player player)
    {
        this.game = game;
        this.player = player;

        skin = new Skin(Gdx.files.internal("button/Buttons.json"));
        stage = new Stage(new ScreenViewport());

        //Gdx.graphics.setContinuousRendering(false);
        //Gdx.graphics.requestRendering();

        buttons = new Table();

        buttons.setFillParent(true);

        buttons.add(player.provideEquippedSlots(this));
        buttons.row();
        buttons.add(player.provideInventory(this));

        stage.addActor(buttons);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(Color.WHITE);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    public void updateButtons()
    {
        buttons.remove();

        buttons = new Table();

        buttons.setFillParent(true);

        buttons.add(player.provideEquippedSlots(this));
        buttons.row();
        buttons.add(player.provideInventory(this));

        stage.addActor(buttons);
    }

    @Override
    public void resize(int width, int height)
    {
        game.viewport.update(width, height, true);
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
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
