package io.github.some_example_name.tools;

import io.github.some_example_name.Entity;
import io.github.some_example_name.Item;
import io.github.some_example_name.actions.BasicAction;

import java.io.Serializable;

/**
 * Parent class for Items with BasicActions.
 * <p>
 * This is the main way for the Player to interact with enemies.
 * <p>
 * There should be no reason to change this class. If you disagree, bring
 * it up with the rest of the team.
 */
public class Tool extends Item implements Serializable
{
    private BasicAction move;

    public Tool()
    {

    }

    public Tool(String name, BasicAction move)
    {
        super(name);
        setMove(move);
    }

    public Tool(Tool tool)
    {
        super(tool);
        setMove(new BasicAction(tool.getMove()));
    }

    public BasicAction getMove()
    {
        return move;
    }

    public void setMove(BasicAction move)
    {
        this.move = move;
    }

    /**
     * Executes BasicAction.
     *
     * @param user   Entity doing action.
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
