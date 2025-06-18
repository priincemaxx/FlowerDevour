package io.github.some_example_name;

//Template for an enemy encounter in the map

import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.tools.Tool;


/**
 * This is an object that stores basic data for a combat room.
 * <p>
 * It stores an Enemy and sometimes a Tool to reward the Player
 * with.
 * <p>
 * THAT'S IT!!!!!!!!!!!!
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
     * @param enemy  Enemy the player will encounter in this room.
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
     * @param enemy Enemy the player will encounter in this room.
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
