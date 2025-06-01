package io.github.some_example_name;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameMaster
{
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Tool> tools = new ArrayList<Tool>();
    private ArrayList<PassiveItem> passives = new ArrayList<PassiveItem>();

    public void initializeEnemies()
    {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("JSONs/enemies.json"))
        {
            enemies = gson.fromJson(reader, new TypeToken<ArrayList<Enemy>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeTools()
    {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("JSONs/tools.json"))
        {
            tools = gson.fromJson(reader, new TypeToken<ArrayList<Tool>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializePassives()
    {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("JSONs/passives.json"))
        {
            passives = gson.fromJson(reader, new TypeToken<ArrayList<PassiveItem>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
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
