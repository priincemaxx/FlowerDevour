package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class Room
{
    private String backgroundPath;
    //0 left path, MAX right path, else middle paths;
    private Room paths[] = new Room[Map.MAX_ROW_ROOMS];

    public Room()
    {

    }

    public String getBackgroundPath() { return backgroundPath; }
    public void setBackgroundPath(String path) { backgroundPath = path; }

    public Room[] getPaths()
    {
        return paths;
    }

    public void setPaths(Room paths[])
    {
        this.paths = paths;
    }
}
