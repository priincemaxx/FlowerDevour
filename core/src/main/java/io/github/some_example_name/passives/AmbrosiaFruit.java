package io.github.some_example_name.passives;

import io.github.some_example_name.Player;


/**
 * Passive item that increases player's
 * maxHealth by 10 and heals them to full
 * on pick up.
 */
public class AmbrosiaFruit extends PassiveItem
{
    public void onGet(Player owner)
    {
        int currentMax = owner.getMaxHealth();

        currentMax += 10;

        owner.setMaxHealth(currentMax);
        owner.setHealth(currentMax);
    }
}
