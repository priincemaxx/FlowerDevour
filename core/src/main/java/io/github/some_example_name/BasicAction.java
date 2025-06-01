package io.github.some_example_name;

//TODO: figure out a data-driven way to pass BasicAction child classes to enemies and items
//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//The purpose of this class is to create a template for
//describing actions taken in combat by either the player or the enemy.


public class BasicAction
{
    private Entity user, target;
    private int userChange, targetChange;
    private String name;

    public BasicAction(Entity user, Entity target, int userChange, int targetChange, String name)
    {
        setUser(user);
        setTarget(target);
        setUserChange(userChange);
        setTargetChange(targetChange);
        setName(name);
    }
    public BasicAction(int userChange, int targetChange, String name)
    {
        setUserChange(userChange);
        setTargetChange(targetChange);
        setName(name);

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
    public String getName()
    {
        return name;
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
    public void setName(String name) throws ActionException
    {
        if (name == null || name.isEmpty())
        {
            throw new ActionException("Invalid action name!");
        }

        this.name = name;
    }

    /**Executes itself on user and target.
     * @throws ActionException When user or target is unset.
     */
    public void execute() throws ActionException
    {
        if (user == null || target == null)
        {
            throw new ActionException("Invalid target or targets!");
        }

        int userHp = user.getHealth();
        int targetHp = target.getHealth();

        userHp += userChange;
        targetHp += targetChange;

        user.setHealth(userHp);
        target.setHealth(targetHp);

        Map map = new Map();
        map.traverse();
    }
}
