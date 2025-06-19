package io.github.some_example_name;

import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.tools.Tool;


/**
 * This is an object that stores basic data for a combat room.
 * <p>
 * It should always store an {@link Enemy} and occasionally a {@link Tool} to reward
 * the {@link Player} with.
 * <p>
 * There should be no reason to change this class. If you disagree, bring
 * it up with the rest of the team.
 */
public class CombatRoom extends Room
{
    private Enemy enemy;
    private Tool reward;

    public CombatRoom()
    {
        super();
    }

    /**
     * @param enemy  {@link Enemy} the player will encounter in this room.
     * @param reward The reward the player will get after defeating
     *               the enemy in this room.
     */
    public CombatRoom(Enemy enemy, Tool reward)
    {
        super();
        setEnemy(enemy);
        setReward(reward);
    }

    /**
     * @param enemy {@link Enemy} the player will encounter in this room.
     */
    public CombatRoom(Enemy enemy)
    {
        super();
        setEnemy(enemy);
        setReward(null);
    }

    public Enemy getEnemy()
    {
        return enemy;
    }

    public Tool getReward()
    {
        return reward;
    }

    public void setEnemy(Enemy enemy)
    {
        this.enemy = enemy;
    }

    public void setReward(Tool reward)
    {
        this.reward = reward;
    }
}
