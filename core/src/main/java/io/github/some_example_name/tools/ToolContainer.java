package io.github.some_example_name.tools;

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



    /**Moves Tool inside to first open slot.
     * @param tool Tool to move.
     * @return Where Tool was placed.
     * @throws ToolContainerException When null argument or container is full.
     */
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
    /**Move Tool inside given index.
     * @param containerIndex Where to move.
     * @param tool Tool to move.
     * @throws ToolContainerException When null argument, out of bounds index,
     * or indexed slot is occupied.
     */
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
    /**Moves one Tool outside.
     * @param containerIndex Tool to move out index.
     * @return Indexed Tool.
     * @throws ToolContainerException When out of bounds index or indexed Tool is null.
     */
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

    /**Moves one Tool from container to different container.
     * @param containerIndex Tool to move out.
     * @param receiver Tool receiver.
     * @return Where Tool was placed in receiver or -1 if the tool
     * was unable to be moved.
     */
    public int moveOneInto(int containerIndex, ToolContainer receiver)
    {
        Tool movingTool = this.moveOutside(containerIndex);

        try
        {
            return receiver.moveInside(movingTool);
        }
        catch (ToolContainerException e)
        {
            System.out.println(e);
            this.moveInside(containerIndex, movingTool);
            return -1;
        }
    }
    /**Moves one Tool from container to specific slot in different container.
     * @param containerIndex Tool to move out.
     * @param receiverIndex Place to move tool to.
     * @param receiver Tool receiver.
     */
    public void moveOneInto(int containerIndex, int receiverIndex, ToolContainer receiver)
    {
        Tool movingTool = this.moveOutside(containerIndex);

        try
        {
            receiver.moveInside(receiverIndex, movingTool);
        }
        catch (ToolContainerException e)
        {
            System.out.println(e);
            this.moveInside(containerIndex, movingTool);
        }
    }


    /**Returns reference to Tool inside.
     * @param containerIndex Tool index.
     * @return Reference to indexed Tool.
     */
    public Tool getTool(int containerIndex)
    {
        return container[containerIndex];
    }

    /**
     * @return Number of occupied slots.
     */
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
