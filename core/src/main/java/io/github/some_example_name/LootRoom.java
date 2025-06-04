package io.github.some_example_name;

import io.github.some_example_name.passives.PassiveItem;

public class LootRoom extends Room
{
    private PassiveItem reward;

    public LootRoom()
    {

    }

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
