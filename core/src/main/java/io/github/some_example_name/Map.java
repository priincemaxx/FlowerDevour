package io.github.some_example_name;

import java.util.Random;

/**
 * A class to store basic information about the game's map.
 * <p>
 * The map consists of rows = {@code ROWS} and columns = {@code MAX_ROW_ROOMS}.
 * <p>
 * The first and last rows are reserved for the starting and boss {@link Room}s respectively.
 * Every other row is filled with from {@code MIN_ROW_ROOMS} to {@code MAX_ROW_ROOMS} rooms that are
 * randomly either a {@link CombatRoom} or a {@link LootRoom}.
 * <p>
 * The {@link Player} may only advance forward through the Map and may only advance to a {@link Room}
 * that has a path that connects it to the {@link Room} the {@link Player} is currently in.
 */
public class Map
{
    public final static int MAX_ROW_ROOMS = 4;
    public final static int MIN_ROW_ROOMS = 2;
    public final static int ROWS = 6;
    private Room rooms[][] = new Room[ROWS][MAX_ROW_ROOMS];
    private Room currentRoom;

    /**
     * Generates a new {@link Map}.
     */
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


    /**
     * Adds {@link Room}s to the {@link Map} at the given row
     *
     * @param row Index of the row to add the {@link Room}s to.
     */
    private void addRowRooms(int row)
    {
        Random random = new Random();

        int roomNumber = random.nextInt(MIN_ROW_ROOMS, MAX_ROW_ROOMS + 1);

        for (int i = 0; i < roomNumber; i++)
        {
            int roomType = random.nextInt(4);

            if (roomType == 0) //consider initializing room here instead of advance() or move to a separate method
            {
                rooms[row][i] = new LootRoom();
            } else
            {
                rooms[row][i] = new CombatRoom();
            }
        }
    }

    /**
     * Adds paths to the {@link Room}s in the specified row.
     *
     * @param row Index of row with {@link Room}s that will receive paths.
     */
    private void addRoomPaths(int row) //Row can't be the first row
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
     * Used to advance the {@link Player} through the {@link Room}s of the {@link Map}.
     *
     * @param index Index of path the {@link Player} wishes to travel to.
     * @return The {@link Room} that the {@link Player} will travel to.
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

        if (currentRoom.getClass() == CombatRoom.class) //consider moving this to a separate method
        {
            Random random = new Random();

            int type = random.nextInt(3);

            if (type == 0)
            {
                currentRoom = new CombatRoom(GameMaster.provideRandomEnemy());
            } else
            {
                currentRoom = new CombatRoom(GameMaster.provideRandomEnemy(), GameMaster.provideRandomTool());
            }
        } else
        {
            currentRoom = new LootRoom(GameMaster.provideRandomPassive());
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

    public Room getRoom(int row, int col)
    {
        return rooms[row][col];
    }
}
