package io.github.some_example_name;

//TODO: figure out a data-driven way to pass BasicAction child classes to enemies and items
//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//The purpose of this class is to create a template for
//describing actions taken in combat by either the player or the enemy.

public class BasicAction
{
    protected Entity user, target;
    protected int userChange, targetChange;

    public BasicAction(Entity user, Entity target, int userChange, int targetChange)
    {
        setUser(user);
        setTarget(target);
        setUserChange(userChange);
        setTargetChange(targetChange);
    }
    public BasicAction(int userChange, int targetChange)
    {
        setUserChange(userChange);
        setTargetChange(targetChange);

        setUser(null);
        setTarget(null);
    }

    public Entity getUser()
    {
        return user;
    }
    public Entity getTarget()
    {
        return target;
    }
    public int getUserChange()
    {
        return userChange;
    }
    public int getTargetChange()
    {
        return targetChange;
    }

    public void setUser(Entity user)
    {
        this.user = user;
    }
    public void setTarget(Entity target)
    {
        this.target = target;
    }
    public void setUserChange(int userChange)
    {
        this.userChange = userChange;
    }
    public void setTargetChange(int targetChange)
    {
        this.targetChange = targetChange;
    }

    public void execute()
    {
        if (user == null || target == null)
        {
            System.out.println("You have no (or not enough) targets!");
            return;
        }

        int userHp = user.getHealth();
        int targetHp = target.getHealth();

        userHp += userChange;
        targetHp += targetChange;

        user.setHealth(userHp);
        target.setHealth(targetHp);
    }
}
