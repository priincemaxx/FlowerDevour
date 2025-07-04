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


/**
 * This is a basic Enemy that can do one {@link BasicAction}.
 * <p>
 * The turn of an Enemy should look like this:
 * <p>
 * 1. Set the Enemy's target.
 * <p></p>
 * 2. The Enemy does its move.
 * <p>
 * 3. The Enemy passes it's turn.
 */
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

    /**
     * Set's the {@link BasicAction}'s user to this {@link Enemy}
     * and target to {@link Enemy}'s target, then executes it.
     */
    public void doMove()
    {
        move.setUser(this);
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
        }
        else
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
