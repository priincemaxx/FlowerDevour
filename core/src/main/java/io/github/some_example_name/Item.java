package io.github.some_example_name;

//ROUGH ESTIMATE, ONLY FOR ARCHITECTURAL PURPOSES
//Provides basic data fields to child classes

public class Item
{
    private int id;
    private String name;
    //private texture texture;


    public Item(int id, String name)
    {
        setId(id);
        setName(name);
    }
    public Item (Item item)
    {
        this.id = item.getId();
        this.name = item.getName();
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
