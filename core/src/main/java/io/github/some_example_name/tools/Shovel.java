package io.github.some_example_name.tools;

import io.github.some_example_name.Entity;
import io.github.some_example_name.Tool;
import io.github.some_example_name.actions.SmallHurt;

public class Shovel extends Tool
{
    @Override
    public void callAction(Entity user, Entity target)
    {
        SmallHurt smallhurt = new SmallHurt(user, target);
        smallhurt.execute();
    }
}
