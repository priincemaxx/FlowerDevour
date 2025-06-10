package io.github.some_example_name.enemies;

//Template for enemies, their stats

import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.Entity;
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

    public void provideHealthBar(SpriteBatch batch) {
        batch.draw(new Texture("barborder_temp1.png"), 4.25f, 4.55f, 2.5f, 0.2f);
        //math
        batch.draw(new Texture("healthBar.png"), 4.3f, 4.6f, 2.4f, 0.1f);
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
