package io.github.some_example_name;

import java.util.Arrays;

public class Player extends Entity
{
    public static final int EQUIP_SLOTS = 4;
    public static final int INVENTORY_SLOTS = 20;
    public static final int MAX_PASSIVES = 100;

    private Tool[] equippedTools = new Tool[EQUIP_SLOTS];
    private Tool[] inventory = new Tool[INVENTORY_SLOTS];
    private int selectedTool = 0;
    private int equippedCount = 0;



    public Player(int health, int maxHealth, String name, Tool[] starterTools)
    {
        super(health, maxHealth, name);
        setEquippedTools(starterTools);
        Arrays.fill(inventory, Tool.NULL_TOOL);
    }
    public Player(int health, int maxHealth, String name, Tool starterTool)
    {
        super(health, maxHealth, name);
        Arrays.fill(equippedTools, Tool.NULL_TOOL);
        equippedTools[0] = new Tool(starterTool);
        Arrays.fill(inventory, Tool.NULL_TOOL);
    }

    public Tool[] getEquippedTools()
    {
        return equippedTools;
    }
    public int getSelectedTool()
    {
        return selectedTool;
    }
    public int getEquippedCount()
    {
        return equippedCount;
    }

    public void setEquippedTools(Tool[] equippedTools) throws PlayerException
    {
        if (equippedTools.length != EQUIP_SLOTS)
        {
            throw new PlayerException("Incorrect size of array!");
        }

        int equippedCount = 0;
        for (int i = 0; i < EQUIP_SLOTS; i++)
        {
            if (!equippedTools[i].equals(Tool.NULL_TOOL))
            {
                equippedCount++;
                this.equippedTools[i] = new Tool(equippedTools[i]);
            }
        }
        setEquippedCount(equippedCount);
    }
    public void setSelectedTool(int selectedTool) throws PlayerException
    {
        if (selectedTool > EQUIP_SLOTS - 1 || selectedTool < 0)
        {
            throw new PlayerException("Trying to select tool out of range!");
        }

        this.selectedTool = selectedTool;
    }
    public void setEquippedCount(int equippedCount) throws PlayerException
    {
        if (equippedCount > EQUIP_SLOTS)
        {
            throw new PlayerException("Cannot have more than" + EQUIP_SLOTS + "equipped items!");
        }
        if (equippedCount <= 0)
        {
            throw new PlayerException("Cannot have 0 or less equipped items!");
        }

        this.equippedCount = equippedCount;
    }

    public void doMove(Entity user, Entity target)
    {
        equippedTools[selectedTool].callAction(user, target);
    }
}
