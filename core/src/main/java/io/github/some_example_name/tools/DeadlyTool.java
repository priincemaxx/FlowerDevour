package io.github.some_example_name.tools;

import io.github.some_example_name.Entity;
import io.github.some_example_name.Player;
import io.github.some_example_name.actions.BasicAction;

public class DeadlyTool extends Tool
{
    DeadlyTool()
    {
        super();
    }

    @Override
    public void execute(Entity user, Entity target)
    {
        BasicAction move = super.getMove();

        int targetChange = move.getTargetChange();


        if (user.getClass() == Player.class)
        {
            double modifier = 1;
            modifier = ((Player) user).getDamageModifier();
            int newChange = (int) (targetChange * modifier);
            move.setTargetChange(newChange);
        }

        super.execute(user, target);

        move.setTargetChange(targetChange);
    }
}
