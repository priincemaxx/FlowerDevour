package io.github.some_example_name;

import java.util.Arrays;

public class ToolContainer
{
    private Tool[] container;

    public ToolContainer(int size)
    {
        container = new Tool[size];
        Arrays.fill(container, null);
    }
    public ToolContainer(ToolContainer tools)
    {
        int size = tools.getContainer().length;

        container = Arrays.copyOf(tools.getContainer(), size);
    }

    public Tool[] getContainer()
    {
        return container;
    }

    public void setContainer(Tool[] container)
    {
        this.container = container;
    }

    public int moveInside(Tool tool) throws ToolContainerException
    {
        if (tool == null)
        {
            throw new ToolContainerException("Trying to move nothing inside!");
        }

        for (int currentSlot = 0; currentSlot < container.length; currentSlot++)
        {
            if (container[currentSlot] == null)
            {
                container[currentSlot] = new Tool(tool);
                return currentSlot;
            }
        }

        throw new ToolContainerException("Container already full!");
    }

    public void moveInside(int containerIndex, Tool tool) throws ToolContainerException
    {
        if (containerIndex > container.length - 1 || containerIndex < 0)
        {
            throw new ToolContainerException("Selection out of bounds");
        }
        if (tool == null)
        {
            throw new ToolContainerException("Trying to move nothing inside!");
        }
        if (container[containerIndex] != null)
        {
            throw new ToolContainerException("Slot is already full!");
        }

        container[containerIndex] = new Tool(tool);
    }

    public Tool moveOutside(int containerIndex) throws ToolContainerException
    {
        if (containerIndex > container.length - 1 || containerIndex < 0)
        {
            throw new ToolContainerException("Selection out of bounds");
        }
        if (container[containerIndex] == null)
        {
            throw new ToolContainerException("Trying to move out nothing!");
        }

        Tool leavingTool = new Tool(container[containerIndex]);
        container[containerIndex] = null;
        return leavingTool;
    }

    public int moveOneInto(int containerIndex, ToolContainer receiver)
    {
        Tool movingTool = this.moveOutside(containerIndex);

        return receiver.moveInside(movingTool);
    }
    public void moveOneInto(int containerIndex, int receiverIndex, ToolContainer receiver)
    {
        Tool movingTool = this.moveOutside(containerIndex);

        receiver.moveInside(receiverIndex, movingTool);
    }

    public Tool getTool(int containerIndex)
    {
        return container[containerIndex];
    }

    public int filledSlots()
    {
        int filledSlots = 0;
        for (Tool tool : container)
        {
            if (tool != null)
            {
                filledSlots++;
            }
        }

        return filledSlots;
    }
}
