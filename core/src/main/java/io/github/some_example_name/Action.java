package io.github.some_example_name;

//TODO: figure out a data-driven way to pass Action child classes to enemies and items
//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//The purpose of this class is to create a template for
//describing actions taken in combat by either the player or the enemy.

public class Action
{
    protected Entity user, target;

    public Action(Entity user, Entity target)
    {
        setUser(user);
        setTarget(target);
    }

    public Entity getUser()
    {
        return user;
    }
    public Entity getTarget()
    {
        return target;
    }

    public void setUser(Entity user)
    {
        this.user = user;
    }
    public void setTarget(Entity target)
    {
        this.target = target;
    }

    public void execute()
    {
        ;
    }
}
