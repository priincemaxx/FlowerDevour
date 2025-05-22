package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for enemies, their stats

import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Entity
{
    private BasicAction move;

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
}
