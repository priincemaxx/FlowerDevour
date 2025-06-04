package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for an item the player can find
//that passively helps the player.

import java.io.Serializable;

public class PassiveItem extends Item implements Serializable
{
    //private passive passive;

    public PassiveItem()
    {

    }

    public PassiveItem(String name)
    {
        super(name);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
