package io.github.some_example_name;

//Template for enemies, their stats

import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;

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

    @Override
    public String toString()
    {
        return "Enemy{" +
            "move=" + move +
            super.toString() +
            '}';
    }
}
