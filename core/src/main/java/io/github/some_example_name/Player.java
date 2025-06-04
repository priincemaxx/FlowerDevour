package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.some_example_name.passives.PassiveContainer;
import io.github.some_example_name.passives.PassiveItem;
import io.github.some_example_name.tools.Tool;
import io.github.some_example_name.tools.ToolContainer;
import io.github.some_example_name.tools.ToolContainerException;

public class Player extends Entity
{
    public static final int EQUIP_SLOTS = 4;
    public static final int INVENTORY_SLOTS = 20;
    public static final int MAX_PASSIVES = 100;

    private ToolContainer equippedTools = new ToolContainer(EQUIP_SLOTS);
    private ToolContainer inventory = new ToolContainer(INVENTORY_SLOTS);
    private PassiveContainer passives = new PassiveContainer(MAX_PASSIVES);
    private int selectedTool = 0;

    private double damageModifier = 1;


    public Player(int health, int maxHealth, String name, Tool starterTool)
    {
        super(health, maxHealth, name);
        equippedTools.moveInside(new Tool(starterTool));
    }

    public Player(int health, int maxHealth, String name, Texture texture, ToolContainer starterTools)
    {
        super(health, maxHealth, name, texture);
        setEquippedTools(starterTools);
    }
    public Player(int health, int maxHealth, String name, Texture texture, Tool starterTool)
    {
        super(health, maxHealth, name, texture);
        equippedTools.moveInside(new Tool(starterTool));
    }

    public void setDamageModifier(double damageModifier)
    {
        this.damageModifier = damageModifier;
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
    public void setPlayerTarget(Entity target)
    {
        super.setTarget(target);
    }

    public double getDamageModifier()
    {
        return damageModifier;
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
    public int rewardPassive(PassiveItem reward)
    {
        return passives.moveInside(reward);
    }

    public int getEquippedCount()
    {
        return equippedTools.filledSlots();
    }
    public int getUnequippedCount()
    {
        return inventory.filledSlots();
    }
    public int getPassivesCount()
    {
        return passives.filledSlots();
    }

    public void doMove() throws PlayerException
    {
        if (equippedTools.getTool(selectedTool) == null)
        {
            throw new PlayerException("Trying to act on nothing!");
        }

        Tool actingTool = new Tool(equippedTools.getTool(selectedTool));

        int targetChange = actingTool.getMove().getTargetChange();

        targetChange *= damageModifier;

        actingTool.getMove().setTargetChange(targetChange);

        actingTool.execute(this, super.getTarget());
    }


    /** Provides buttons that do the associated action.
     * @return Table with buttons to be added to a stage.
     */
    public Table provideMoveButtons()
    {
        //final int width = 200;
        //final int height = 40;
        Table moveButtons = new Table();
        //Skin skin1 = new Skin(Gdx.files.internal("buttonSkin_temp/skin.json"));
        Skin skin = new Skin(Gdx.files.internal("button/TextButton.json"));
        moveButtons.setFillParent(true);
        moveButtons.bottom().pad(15);
        moveButtons.defaults().growX().padLeft(10).padRight(10).height(50);
        //moveButtons.setDebug(true);
        for (int currentSlot = 0; currentSlot < EQUIP_SLOTS; currentSlot++)
        {
            if (currentSlot > 0 && currentSlot % 2 == 0)
            {
                moveButtons.row();
            }
            if (equippedTools.getTool(currentSlot) == null)
            {
                TextButton blankButton = new TextButton(" ", skin);
                blankButton.setDisabled(true);
                moveButtons.add(blankButton).uniform();
                continue;
            }

            String moveName = equippedTools.getTool(currentSlot).getMove().getName();
            TextButton moveButton = new TextButton(moveName, skin);
            moveButtons.add(moveButton).uniform();
            final int tmpCurrentSlot = currentSlot;
            moveButton.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    setSelectedTool(tmpCurrentSlot);
                    doMove();
                    //System.out.println("I work!");
                }
            });
        }

        return moveButtons;
    }

    //ughhhhhh
    public Table provideProgressBars()
    {
        Table progressBars = new Table();
        Skin skin = new Skin(Gdx.files.internal("testskin/default-ui1.json"));
        progressBars.setFillParent(true);
        progressBars.top().padTop(70);
        progressBars.defaults().growX().pad(10);
        progressBars.setDebug(true);
//progressBars.setDebug(true);
        ProgressBar healthBar = new ProgressBar(0, 20, 5, false, skin);
        progressBars.add(healthBar);

        return progressBars;
    }
}
