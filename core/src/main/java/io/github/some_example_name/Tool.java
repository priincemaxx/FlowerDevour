package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for a weapon style item.
//Player will start off with some basic ones.

public class Tool extends Item
{
    private BasicAction move;

    public Tool(int id, String name, BasicAction move)
    {
        super(id, name);
        this.move = move;
    }

    public BasicAction getMove()
    {
        return move;
    }

    public void setMove(BasicAction move)
    {
        this.move = move;
    }

    public void callAction(Entity user, Entity target)
    {
        move.setUser(user);
        move.setTarget(target);

        move.execute();
    }
}
