package io.github.some_example_name.actions;

import io.github.some_example_name.Action;
import io.github.some_example_name.Entity;

public class SmallHurt extends Action
{
    public SmallHurt(Entity user, Entity target)
    {
        super(user, target);
    }

    @Override
    public void execute()
    {
        int targethp = target.getHealth();
        targethp -= 3;
        target.setHealth(targethp);
    }
}
