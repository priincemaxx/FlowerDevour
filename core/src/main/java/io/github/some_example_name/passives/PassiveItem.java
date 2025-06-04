package io.github.some_example_name.passives;

//Template for an item the player can find
//that passively helps the player.

import io.github.some_example_name.Entity;
import io.github.some_example_name.Item;
import io.github.some_example_name.Player;

import java.io.Serializable;

public class PassiveItem extends Item implements Serializable
{
    public PassiveItem()
    {

    }

    PassiveItem(PassiveItem passive)
    {
        super(passive.getName());
    }

    public PassiveItem(String name)
    {
        super(name);
    }

    public void onGet(Player owner)
    {

    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
