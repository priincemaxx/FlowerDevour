package io.github.some_example_name;

//TODO: add texture and associated method for drawing.

public class Entity
{
    private int health;
    private int maxHealth;
    private String name;
    //private texture...

    public Entity(int health, int maxHealth, String name)
    {
        setMaxHealth(maxHealth);
        setHealth(health);
        setName(name);
    }

    public int getHealth()
    {
        return health;
    }
    public int getMaxHealth()
    {
        return maxHealth;
    }
    public String getName()
    {
        return name;
    }

    public void setHealth(int health)
    {
        if (health >= maxHealth)
        {
            this.health = this.maxHealth;
            return;
        }
        if (health <= 0)
        {
            this.health = 0;
            return;
        }

        this.health = health;
    }
    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public void doMove(Entity target)
    {

    }
}
