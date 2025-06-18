package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.some_example_name.enemies.Enemy;
import io.github.some_example_name.passives.PassiveItem;
import io.github.some_example_name.tools.Tool;

import java.util.ArrayList;
import java.util.Random;


/**
 * Responsible for files and the actual game.
 * <p>
 * Game objects initialized from JSONs.
 * <p>
 * For JSON writing, don't forget to declare the class of the object
 * when writing them. Refer to the enemies.json file for reference.
 */
public class GameMaster
{
    static private ArrayList<Enemy> enemies = initializeEnemies();
    static private ArrayList<Tool> tools = initializeTools();
    static private ArrayList<PassiveItem> passives = initializePassives();


    static public ArrayList<Enemy> initializeEnemies()
    {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();

        Json json = new Json();
        JsonValue jsonReader = new JsonReader().parse(Gdx.files.internal("JSONs/enemies.json"));

        for (JsonValue enemyJson : jsonReader.iterator())
        {
            String text = enemyJson.toString();
            Enemy newEnemy = json.fromJson(Enemy.class, text);

            enemies.add(newEnemy);
        }

        return enemies;
    }

    static public ArrayList<Tool> initializeTools()
    {
        ArrayList<Tool> tools = new ArrayList<Tool>();

        Json json = new Json();
        JsonValue jsonReader = new JsonReader().parse(Gdx.files.internal("JSONs/tools.json"));

        for (JsonValue toolJson : jsonReader.iterator())
        {
            String text = toolJson.toString();
            Tool newTool = json.fromJson(Tool.class, text);

            tools.add(newTool);
        }

        return tools;
    }

    static public ArrayList<PassiveItem> initializePassives()
    {
        ArrayList<PassiveItem> passives = new ArrayList<PassiveItem>();

        Json json = new Json();
        JsonValue jsonReader = new JsonReader().parse(Gdx.files.internal("JSONs/passives.json"));

        for (JsonValue passiveJson : jsonReader.iterator())
        {
            String text = passiveJson.toString();
            PassiveItem newPassive = json.fromJson(PassiveItem.class, text);

            passives.add(newPassive);
        }

        return passives;
    }

    static public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    static public ArrayList<Tool> getTools()
    {
        return tools;
    }

    static public ArrayList<PassiveItem> getPassives()
    {
        return passives;
    }

    /**
     * @return Returns a copy of a random Enemy from the list.
     */
    static public Enemy provideRandomEnemy()
    {
        Random random = new Random();

        int index = random.nextInt(enemies.size());

        return new Enemy(enemies.get(index));
    }

    /**
     * @return Returns a copy of a random Tool from the list.
     */
    static public Tool provideRandomTool()
    {
        Random random = new Random();

        int index = random.nextInt(tools.size());

        return new Tool(tools.get(index));
    }

    /**
     * @return Returns a copy of a random PassiveItem from the list.
     */
    static public PassiveItem provideRandomPassive()
    {
        Random random = new Random();

        int index = random.nextInt(passives.size());

        return new PassiveItem(passives.get(index));
    }

    /**
     * @param name Name of Enemy to return.
     * @return Returns Enemy with given name if it exists, else
     * returns null.
     */
    static public Enemy searchForEnemy(String name)
    {
        for (Enemy enemy : enemies)
        {
            if (enemy.getName().equals(name))
            {
                return new Enemy(enemy);
            }
        }

        return null;
    }

    /**
     * @param name Name of Tool to return.
     * @return Returns Tool with given name if it exists, else
     * returns null.
     */
    static public Tool searchForTool(String name)
    {
        for (Tool tool : tools)
        {
            if (tool.getName().equals(name))
            {
                return new Tool(tool);
            }
        }

        return null;
    }

    /**
     * @param name Name of PassiveItem to return.
     * @return Returns PassiveItem with given name if it exists, else
     * returns null.
     */
    static public PassiveItem searchForPassive(String name)
    {
        for (PassiveItem passiveItem : passives)
        {
            if (passiveItem.getName().equals(name))
            {
                return new PassiveItem(passiveItem);
            }
        }

        return null;
    }

    /**
     * @param index Index of Enemy to return.
     * @return Returns Enemy with given index, else
     * throws.
     */
    static public Enemy searchForEnemy(int index)
    {
        return new Enemy(enemies.get(index));
    }

    /**
     * @param index Index of Tool to return.
     * @return Returns Tool with given index, else
     * throws.
     */
    static public Tool searchForTool(int index)
    {
        return new Tool(tools.get(index));
    }

    /**
     * @param index Index of PassiveItem to return.
     * @return Returns PassiveItem with given index, else
     * throws.
     */
    static public PassiveItem searchForPassive(int index)
    {
        return new PassiveItem(passives.get(index));
    }
}
