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

    static public Enemy provideEnemy()
    {
        Random random = new Random();

        int index = random.nextInt(enemies.size());

        return new Enemy(enemies.get(index));
    }

    static public Tool provideTool()
    {
        Random random = new Random();

        int index = random.nextInt(tools.size());

        return new Tool(tools.get(index));
    }

    static public PassiveItem providePassive()
    {
        Random random = new Random();

        int index = random.nextInt(passives.size());

        return new PassiveItem(passives.get(index));
    }
}
