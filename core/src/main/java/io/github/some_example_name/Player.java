package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.ApplicationAdapter;


public class Player extends Entity
{
    public static final int EQUIP_SLOTS = 4;
    public static final int INVENTORY_SLOTS = 20;
    public static final int MAX_PASSIVES = 100;

    private ToolContainer equippedTools = new ToolContainer(EQUIP_SLOTS);
    private ToolContainer inventory = new ToolContainer(INVENTORY_SLOTS);
    private int selectedTool = 0;



    public Player(int health, int maxHealth, String name, ToolContainer starterTools)
    {
        super(health, maxHealth, name);
        setEquippedTools(starterTools);
    }
    public Player(int health, int maxHealth, String name, Tool starterTool)
    {
        super(health, maxHealth, name);
        equippedTools.moveInside(new Tool(starterTool));
    }

    public void setEquippedTools(ToolContainer equippedTools)
    {
        this.equippedTools = new ToolContainer(equippedTools);
    }
    public void setSelectedTool(int selectedTool) throws PlayerException
    {
        if (selectedTool > EQUIP_SLOTS - 1 || selectedTool < 0)
        {
            throw new ToolContainerException("Trying to select tool out of range!");
        }

        this.selectedTool = selectedTool;
    }


    /**
     * Equips tool to first empty equip slot.
     * @param inventoryIndex Index of tool to equip.
     * @return Place in equip index.
     */
    public int equipTool(int inventoryIndex)
    {
        return inventory.moveOneInto(inventoryIndex, equippedTools);
    }
    /**Equips tool to selected slot.
     * @param equipSlotIndex Selected slot.
     * @param inventoryIndex Index of tool to equip.
     */
    public void equipTool(int equipSlotIndex, int inventoryIndex)
    {
        inventory.moveOneInto(inventoryIndex, equipSlotIndex, equippedTools);
    }
    /**Unequips tool to first empty inventory slot.
     * @param equipSlotIndex Index of tool to unequip.
     * @return Place in inventory index.
     */
    public int unequipTool(int equipSlotIndex)
    {
        return equippedTools.moveOneInto(equipSlotIndex, inventory);
    }
    /**Unequips tool to selected inventory slot.
     * @param inventoryIndex Selected slot.
     * @param equipSlotIndex Index of tool to unequip.
     */
    public void unequipTool(int inventoryIndex, int equipSlotIndex)
    {
        equippedTools.moveOneInto(equipSlotIndex, inventoryIndex, inventory);
    }
    public int rewardTool(Tool reward) //TODO: add try-catch block for when the player has a full inventory
    {
        return inventory.moveInside(reward);
    }

    public void doMoveOn(Entity target) throws PlayerException
    {
        Tool actingTool = equippedTools.getTool(selectedTool);

        if (actingTool == null)
        {
            throw new PlayerException("Trying to act on nothing!");
        }

        actingTool.callAction(this, target);
    }
    /** Creates action buttons for combat.
     * @param root Root table to add buttons to.
     * @param skin Skin of the buttons.
     */
    public void createActionButton(Table root, Skin skin, Stage stage, Player player, Enemy enemy)
    {
        Gdx.input.setInputProcessor(stage);
        root.bottom().padBottom(20);
        root.defaults().expandX().uniform().fillX().pad(20);

        Tool toolButton1 = equippedTools.getTool(0);
        Tool toolButton2 = equippedTools.getTool(1);
        root.row();
        Tool toolButton3 = equippedTools.getTool(2);
        Tool toolButton4 = equippedTools.getTool(3);

        toolButton1.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Player health: " + player.getHealth());
                System.out.println("Puny Leaf health: " + enemy.getHealth());
                player.doMoveOn(enemy);
                enemy.doMoveOn(player);
            }
        });



//        for(int i=0; i<EQUIP_SLOTS; i++)
//        {
//            Tool currentTool = equippedTools.getTool(i);
//            if(currentTool != null)
//            {
//                TextButton toolButton = new TextButton(currentTool.getName() , skin);
//                root.add(toolButton);
//            } else {
//                TextButton disabledButton = new TextButton("No tool equipped", skin);
//                root.add(disabledButton)
//                disabledButton.setDisabled(true);
//            }
//            if(i == 1) {root.row(); }
//        }
    }

}
