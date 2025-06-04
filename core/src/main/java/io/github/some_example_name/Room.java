package io.github.some_example_name;

public class Room
{
    //0 left path, MAX right path, else middles paths;
    private Room paths[] = new Room[Map.MAX_ROW_ROOMS];

    public Room()
    {

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
