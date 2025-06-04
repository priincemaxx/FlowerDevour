package io.github.some_example_name.passives;

import io.github.some_example_name.tools.Tool;
import io.github.some_example_name.tools.ToolContainerException;

import java.util.Arrays;

public class PassiveContainer
{
    private PassiveItem[] container;

    public PassiveContainer(int size)
    {
        container = new PassiveItem[size];
        Arrays.fill(container, null);
    }

    public PassiveContainer(PassiveContainer passives)
    {
        int size = passives.getContainer().length;

        container = Arrays.copyOf(passives.getContainer(), size);
    }

    public PassiveItem[] getContainer()
    {
        return container;
    }

    public void setContainer(PassiveItem[] container)
    {
        this.container = container;
    }

    public int moveInside(PassiveItem passive) throws PassiveContainerException
    {
        if (passive == null)
        {
            throw new PassiveContainerException("Trying to move nothing inside!");
        }

        for (int currentSlot = 0; currentSlot < container.length; currentSlot++)
        {
            if (container[currentSlot] == null)
            {
                container[currentSlot] = new PassiveItem(passive);
                return currentSlot;
            }
        }

        throw new PassiveContainerException("Container already full!");
    }

    public PassiveItem moveOutside(int containerIndex) throws PassiveContainerException
    {
        if (containerIndex > container.length - 1 || containerIndex < 0)
        {
            throw new PassiveContainerException("Selection out of bounds");
        }
        if (container[containerIndex] == null)
        {
            throw new PassiveContainerException("Trying to move out nothing!");
        }

        PassiveItem leavingPassive = new PassiveItem(container[containerIndex]);
        container[containerIndex] = null;
        return leavingPassive;
    }
}
