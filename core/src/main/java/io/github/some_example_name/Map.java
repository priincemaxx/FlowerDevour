package io.github.some_example_name;
import java.util.Random;

public class Map
{
    public final static int MAX_ROW_ROOMS = 4;
    public final static int MIN_ROW_ROOMS = 2;
    public final static int ROWS = 6;
    private Room rooms[][] = new Room[ROWS][MAX_ROW_ROOMS];
    private Room currentRoom;

    public Map()
    {
        rooms[0][0] = new Room(); //starting room
        rooms[ROWS - 1][0] = new Room(); //boss room

        for (int i = 1; i < ROWS - 1; i++)
        {
            addRowRooms(i);
        }
        for (int i = 1; i < ROWS; i++)
        {
            addRoomPaths(i);
        }

        currentRoom = rooms[0][0];
    }



    private void addRowRooms(int row)
    {
        Random random = new Random();

        int roomNumber = random.nextInt(MIN_ROW_ROOMS, MAX_ROW_ROOMS + 1);

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


    /**
     * @param index Index of path the player wishes to travel to.
     * @return The Room that the player has traveled to.
     * @throws MapException Throws when there isn't a path with the given index.
     */
    public Room advance(int index) throws MapException
    {
        Room[] possiblePaths = currentRoom.getPaths();

        if (possiblePaths[index] == null)
        {
            throw new MapException("Invalid advance");
        }

        currentRoom = possiblePaths[index];

        possiblePaths = currentRoom.getPaths();

        if (currentRoom.getClass() == CombatRoom.class)
        {
            Random random = new Random();

            int type = random.nextInt(3);

            if (type == 0)
            {
                currentRoom = new CombatRoom(GameMaster.provideEnemy());
            }
            else
            {
                currentRoom = new CombatRoom(GameMaster.provideEnemy(), GameMaster.provideTool());
            }
        }
        else
        {
            currentRoom = new LootRoom(GameMaster.providePassive());
        }

        currentRoom.setPaths(possiblePaths);

        return currentRoom;
    }

    public void print()
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
