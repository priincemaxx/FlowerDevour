package io.github.some_example_name;

//TODO: add texture and associated method for drawing.

import java.io.Serializable;

public class Entity implements Serializable
{
    private int health;
    private int maxHealth;
    private String name;
    private Entity target;
    //private texture...

    public Entity()
    {
    }

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
    public Entity getTarget()
    {
        return target;
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
    public void setTarget(Entity target) throws ActionException
    {
        if (target == null)
        {
            throw new ActionException("Attempting to target nothing!");
        }

        this.target = target;
    }

    /**
     * Executes selected action on target.
     */
    public void doMove()
    {

    }

    @Override
    public String toString()
    {
        return "Entity{" +
            "health=" + health +
            ", maxHealth=" + maxHealth +
            ", name='" + name + '\'' +
            '}';
    }
}
