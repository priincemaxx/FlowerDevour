package io.github.some_example_name;

import io.github.some_example_name.passives.PassiveItem;

/**
 * This is an object that stores basic data for a loot room.
 * <p>
 * It stores a single PassiveItem to reward the player with upon
 * entering.
 */
public class LootRoom extends Room
{
    private PassiveItem reward;

    public LootRoom()
    {
        super();
    }

    /**
     * @param reward The PassiveItem to reward the Player with.
     */
    public LootRoom(PassiveItem reward)
    {
        super();
        setReward(reward);
    }

    public PassiveItem getReward()
    {
        return reward;
    }

    public void setReward(PassiveItem reward)
    {
        this.reward = reward;
    }
}
