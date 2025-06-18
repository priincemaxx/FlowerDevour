package io.github.some_example_name.enemies;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.Animations;
import io.github.some_example_name.Entity;
import io.github.some_example_name.EntityException;
import io.github.some_example_name.Player;
import io.github.some_example_name.actions.BasicAction;


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

    //for copying objects
    public Enemy(Enemy enemy)
    {
        super(enemy);
        setMove(enemy.getMove());
    }

    public BasicAction getMove()
    {
        return move;
    }

    public void setMove(BasicAction move) throws EntityException
    {
        if (move == null)
        {
            throw new EntityException("Null move.");
        }

        this.move = move;
    }

    public void doMove()
    {
        move.setTarget(super.getTarget());
        move.execute();
    }

    public void doMove(Player player)
    {
        move.setUser(player);
        move.setTarget(super.getTarget());
        move.execute();
    }

    /**
     * Provides dynamic health bar
     *
     * @param batch - spritebatch of screen
     */
    public void provideHealthBar(SpriteBatch batch)
    {
        batch.draw(new Texture("other/barborder_temp1.png"), 4.25f, 4.75f, 2.5f, 0.2f);
        float maxWidth = 2.4f;
        float width = (float) getHealth() / getMaxHealth() * maxWidth;
        batch.draw(new Texture("other/healthBar.png"), 4.3f, 4.8f, width, 0.1f);
    }

    public void setupAnimations(String name)
    {
        Map<String, Float> animData = new HashMap<>();
        animData.put("Idle", 0.5f);
        animData.put("Attack", 0.08f);
        animData.put("Damage", 0.1f);

        Animations enemyAnimations;
        if (Objects.equals(name, "Dandelion Trooper"))
        {
            enemyAnimations = new Animations("atlas/DandelionAtlas.atlas", animData);
            setAnimations(enemyAnimations);
        } else
        {
            enemyAnimations = new Animations("atlas/DandelionAtlas.atlas", animData);
            setAnimations(enemyAnimations);
        }
        //setAnimations(enemyAnimations);
        setDefaultAnimation("Idle");
    }

    /// performs these animations once
    public void animateIdle()
    {
        performAnimation("Idle");
    }

    public void animateTakeDamage()
    {
        performAnimation("Damage");
    }

    public void animateAttack()
    {
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
