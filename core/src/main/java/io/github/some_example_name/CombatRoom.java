package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for an enemy encounter in the map

public class CombatRoom
{
    private Enemy enemy;
    private Tool reward;

    public CombatRoom(Enemy enemy, Tool reward)
    {
        setEnemy(enemy);
        setReward(reward);
    }
    public CombatRoom(Enemy enemy)
    {
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
