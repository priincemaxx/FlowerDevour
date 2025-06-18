package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for an enemy encounter in the map

import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.tools.Tool;

import java.util.Objects;


public class CombatRoom extends Room
{
    private Enemy enemy;
    private Tool reward;

    public CombatRoom()
    {

    }

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

    public Enemy getRandomEnemy() //why call it "get random enemy" if you're getting a specific one???? also why is this here and not the GameMaster class????
    {
        Enemy enemy;
        //loop to ensure we only get dandelion //that is incredibly inefficient
        //will probably need something similar to select only boss

        //wtf??? why???
        while (true)
        {
            enemy = GameMaster.provideRandomEnemy();
            if (Objects.equals(enemy.getName(), "Dandelion Trooper"))
            {
                return enemy;
            }
        }
    }

}
