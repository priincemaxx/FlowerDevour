package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for an enemy encounter in the map

import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.tools.Tool;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class CombatRoom extends Room
{
    private Enemy enemy;
    private Tool reward;

    public CombatRoom()
    {

    }

    public CombatRoom(Enemy enemy, Tool reward)
    {
        setEnemy(enemy);
        setReward(reward);
    }
    public CombatRoom(Enemy enemy)
    {
        setEnemy(enemy);
        setReward(null);
    }

    public Enemy getEnemy()
    {
        return enemy;
    }
    public Tool getReward()
    {
        return reward;
    }

    public void setEnemy(Enemy enemy)
    {
        this.enemy = enemy;
    }

    public void setReward(Tool reward)
    {
        this.reward = reward;
    }

    //public void setBackgroundPath() { setBackgroundPath("Backgrounds/combatbg_temp_red.png"); }

    public void drawRoom(FillViewport backgroundViewport, SpriteBatch spriteBatch,
                         Texture backgroundTexture, FillViewport entityViewport,
                         Texture playerTexture, Texture enemyTexture) {
        ScreenUtils.clear(Color.WHITE);
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


}
