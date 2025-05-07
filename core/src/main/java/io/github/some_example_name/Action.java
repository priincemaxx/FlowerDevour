package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//The purpose of this class is to create a template for
//describing actions taken in combat by either the player or the enemy.

public class Action
{
    int dmg;
    int heal;
    //etc.

    public Action(int dmg, int heal /*,...*/)
    {
        this.dmg = dmg;
        this.heal = heal;
    }

    private void action()
    {
        ;
    }
}
