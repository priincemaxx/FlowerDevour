package io.github.some_example_name.enemies;

//Template for enemies, their stats

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Animations;
import io.github.some_example_name.Entity;
import io.github.some_example_name.actions.BasicAction;
import io.github.some_example_name.tools.Tool;

public class Enemy extends Entity implements Serializable
{
    private BasicAction move;

    public Enemy()
    {

    }

    public Enemy(int health, int maxHealth, String name, BasicAction move)
    {
        super(health, maxHealth, name);
        setMove(move);
        move.setUser(this);
    }

    public Enemy(int health, int maxHealth, String name, Texture texture, BasicAction move)
    {
        super(health, maxHealth, name, texture);
        setMove(move);
        move.setUser(this);
    }

    public BasicAction getMove()
    {
        return move;
    }

    public void setMove(BasicAction move)
    {
        this.move = move;
    }

    public void doMove()
    {
        move.setTarget(super.getTarget());

        move.execute();
    }

    /**
     * Provides dynamic health bar
     * @param batch - spritebatch of screen
     */
    public void provideHealthBar(SpriteBatch batch) {
        batch.draw(new Texture("other/barborder_temp1.png"), 4.25f, 4.65f, 2.5f, 0.2f);
        float maxWidth = 2.4f;
        float width = (float) getHealth() /getMaxHealth() * maxWidth;
        batch.draw(new Texture("other/healthBar.png"), 4.3f, 4.7f, width, 0.1f);
    }

    //@Override
    public boolean isDead() {
        if(getHealth() == 0) {
            return true;
        }
        return false;
    }

    public void setupAnimations() {
        Map<String, Float> animData = new HashMap<>();
        animData.put("DefaultIdle", 0.5f);
        animData.put("Attack", 0.08f);
        animData.put("Damage", 0.1f);

        Animations enemyAnimations = new Animations("atlas/DandelionAtlas.atlas", animData);
        setAnimations(enemyAnimations);
        setCurrentAnimation(enemyAnimations.getAnimation("DefaultIdle"));
    }

    public void doDefaultIdle() {
        performAnimation("DefaultIdle");
    }

    public void takeDamage() {
        performAnimation("Damage");
    }

    public void doAttack() {
        performAnimation("Attack");
    }

    @Override
    public String toString()
    {
        return "Enemy{" +
            "move=" + move +
            super.toString() +
            '}';
    }
}
