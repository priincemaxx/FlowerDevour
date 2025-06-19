package io.github.some_example_name;

/**
 * Parent class for all rooms that the {@link Player} could enter from the {@link Map}.
 */
public class Room
{
    private String backgroundPath;
    //0 left path, MAX right path, else middle paths;
    private Room paths[] = new Room[Map.MAX_ROW_ROOMS];

    public Room()
    {

    }

    public String getBackgroundPath()
    {
        return backgroundPath;
    }

    public void setBackgroundPath(String path)
    {
        backgroundPath = path;
    }

    public Room[] getPaths()
    {
        return paths;
    }

    public void setPaths(Room paths[])
    {
        this.paths = paths;
    }
}
