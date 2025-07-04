package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Provides basic data fields to child classes

import java.io.Serializable;

/**
 * Parent class for all Items a player can have.
 * <p>
 * Has a name.
 */
public class Item implements Serializable
{
    private String name;
    //private texture texture;

    public Item()
    {

    }

    public Item(String name)
    {
        setName(name);
    }

    public Item(Item item)
    {
        setName(item.getName());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Abstract method for Items that can do something.
     *
     * @param user   Entity that owns the item.
     * @param target Entity the item is affecting.
     */
    public void execute(Entity user, Entity target)
    {

    }

    public void update()
    {

    }

    @Override
    public String toString()
    {
        return "Item{" +
            "name='" + name + '\'' +
            '}';
    }
}
