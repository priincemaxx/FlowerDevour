package io.github.some_example_name;
import java.util.Random;

public class Map
{
    public final static int MAX_ROW_ROOMS = 4;
    public final static int MIN_ROW_ROOMS = 2;
    public final static int ROWS = 6;
    private Room rooms[][] = new Room[ROWS][MAX_ROW_ROOMS];

    public Map()
    {
        rooms[0][MIN_ROW_ROOMS] = new Room(); //starting room
        rooms[ROWS - 1][MIN_ROW_ROOMS] = new Room(); //boss room

        for (int i = 1; i < ROWS - 1; i++)
        {
            addRowRooms(i);
        }
        for (int i = 1; i < ROWS; i++)
        {
            addRoomPaths(i);
        }
    }

    private void addRowRooms(int row)
    {
        Random random = new Random();

        int roomNumber = random.nextInt(MIN_ROW_ROOMS, MAX_ROW_ROOMS);

        for (int i = 0; i < roomNumber; i++)
        {
            int roomType = random.nextInt(4);

            if (roomType == 0)
            {
                rooms[row][i] = new LootRoom();
            }
            else
            {
                rooms[row][i] = new CombatRoom();
            }
        }
    }

    //Row can't be the first row
    private void addRoomPaths(int row)
    {
        for (int i = 1; i < MAX_ROW_ROOMS; i++)
        {
            if (rooms[row][i] == null)
            {
                continue;
            }

            //Room tmpPaths[] = rooms[row][i].getPaths();

            for (int j = 0; j < MAX_ROW_ROOMS; j++)
            {
                if (rooms[row - 1][j] == null)
                {
                    continue;
                }
                rooms[row - 1][j].setPaths(rooms[row]);
            }
        }
    }

    public void traverse()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < MAX_ROW_ROOMS; j++)
            {
                if (rooms[i][j] == null)
                {
                    continue;
                }

                System.out.print(rooms[i][j] + " ");
            }
            System.out.println();
        }
    }
}
