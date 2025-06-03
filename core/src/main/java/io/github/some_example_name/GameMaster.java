package io.github.some_example_name;

import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class GameMaster
{
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Tool> tools = new ArrayList<Tool>();
    private ArrayList<PassiveItem> passives = new ArrayList<PassiveItem>();

    public void initializeEnemies()
    {
        String enemiesJson = "";
        Json json = new Json();

        try
        {
            File enemies = new File("JSONs/enemies.json");
            Scanner Reader = new Scanner(enemies);
            while (Reader.hasNextLine())
            {
                enemiesJson = (enemiesJson + Reader.nextLine());
            }
            Reader.close();

            enemies = json.fromJson(ArrayList<>.class, enemies);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
/*
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

 */

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
