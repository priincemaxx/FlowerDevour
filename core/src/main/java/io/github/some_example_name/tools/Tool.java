package io.github.some_example_name.tools;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for a weapon style item.
//Player will start off with some basic ones.

import io.github.some_example_name.Entity;
import io.github.some_example_name.Item;
import io.github.some_example_name.actions.BasicAction;

import java.io.Serializable;

public class Tool extends Item implements Serializable
{
    private BasicAction move;

    public Tool(String name, BasicAction move)
    {
        super(name);
        this.move = move;
    }
    public Tool(Tool tool)
    {
        super(tool);
        this.move = tool.move;
    }

    public BasicAction getMove()
    {
        return move;
    }

    public void setMove(BasicAction move)
    {
        this.move = move;
    }

    /**Executes action.
     * @param user Entity doing action.
     * @param target Entity targeted by action.
     */
    public void execute(Entity user, Entity target)
    {
        move.setUser(user);
        move.setTarget(target);

        move.execute();
    }

    @Override
    public String toString()
    {
        return super.toString() +
            "move=" + move +
            '}';
    }
}
