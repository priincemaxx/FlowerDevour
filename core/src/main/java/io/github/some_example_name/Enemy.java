package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Template for enemies, their stats

public class Enemy extends Entity
{
    private BasicAction move;

    public Enemy(int health, int maxHealth, String name, BasicAction move)
    {
        super(health, maxHealth, name);
        setMove(move);
        move.setUser(this);
    }

    public BasicAction getMove()
    {
        return move;
    }

    public void setMove(BasicAction move)
    {
        this.move = move;
    }

    public void doMove(Entity target)
    {
        move.setTarget(target);

        move.execute();
    }
}
