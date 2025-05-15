package io.github.some_example_name;

public class Player extends Entity
{
    private Tool starterTool;

    public Player(int health, int maxHealth, String name, Tool starterTool)
    {
        super(health, maxHealth, name);
        setStarterTool(starterTool);
    }

    public Tool getStarterTool()
    {
        return starterTool;
    }

    public void setStarterTool(Tool starterTool)
    {
        this.starterTool = starterTool;
    }

    public void doMove(Entity user, Entity target)
    {
        starterTool.callAction(user, target);
    }
}
