package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;


/**
 * Responsible for files and the actual game.
 *
 * Game objects initialized from JSONs.
 *
 * For JSON writing, don't forget to declare the class of the object
 * when writing them. Refer to the enemies.json file for reference.
 */
public class GameMaster
{
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Tool> tools = new ArrayList<Tool>();
    private ArrayList<PassiveItem> passives = new ArrayList<PassiveItem>();



    public void initializeEnemies()
    {
        Json json = new Json();
        JsonValue jsonReader = new JsonReader().parse(Gdx.files.internal("JSONs/enemies.json"));

        for (JsonValue enemyJson : jsonReader.iterator())
        {
            String text = enemyJson.toString();
            Enemy newEnemy = json.fromJson(Enemy.class, text);

            enemies.add(newEnemy);
        }
    }

    public void initializeTools()
    {
        Json json = new Json();
        JsonValue jsonReader = new JsonReader().parse(Gdx.files.internal("JSONs/tools.json"));

        for (JsonValue toolJson : jsonReader.iterator())
        {
            String text = toolJson.toString();
            Tool newTool = json.fromJson(Tool.class, text);

            tools.add(newTool);
        }
    }

    public void initializePassives()
    {
        Json json = new Json();
        JsonValue jsonReader = new JsonReader().parse(Gdx.files.internal("JSONs/passives.json"));

        for (JsonValue passiveJson : jsonReader.iterator())
        {
            String text = passiveJson.toString();
            PassiveItem newPassive = json.fromJson(PassiveItem.class, text);

            passives.add(newPassive);
        }
    }



    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    public ArrayList<Tool> getTools()
    {
        return tools;
    }

    public ArrayList<PassiveItem> getPassives()
    {
        return passives;
    }

}
