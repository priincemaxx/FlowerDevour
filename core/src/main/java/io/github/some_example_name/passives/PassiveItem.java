package io.github.some_example_name.passives;

//Template for an item the player can find
//that passively helps the player.

import io.github.some_example_name.Item;

import java.io.Serializable;

public class PassiveItem extends Item implements Serializable
{
    public PassiveItem()
    {

    }

    public PassiveItem(String name)
    {
        super(name);
    }

    public void execute()
    {

    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
