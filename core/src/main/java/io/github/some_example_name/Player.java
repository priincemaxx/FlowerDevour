package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.some_example_name.passives.PassiveContainer;
import io.github.some_example_name.passives.PassiveItem;
import io.github.some_example_name.screens.InventoryScreen;
import io.github.some_example_name.tools.Tool;
import io.github.some_example_name.tools.ToolContainer;
import io.github.some_example_name.tools.ToolContainerException;

import java.util.HashMap;
import java.util.Map;

//TODO: based on the equipped weapon the default animation should change

/**
 * The class that represents the Player.
 * <p>
 * Holds {@link ToolContainer}s for equipped {@link Tool}s and
 * inventory, {@link PassiveContainer} for {@link io.github.some_example_name.passives.PassiveItem}s
 * and a damage modifier.
 */
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

    public Player(int health, int maxHealth, String name, ToolContainer starterTools)
    {
        super(health, maxHealth, name);
        setEquippedTools(starterTools);
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
     * Equips {@link Tool} to first empty equip slot.
     *
     * @param inventoryIndex Index of {@link Tool} to equip.
     * @return Place in equipSlots index.
     */
    public int equipTool(int inventoryIndex)
    {
        return inventory.moveOneInto(inventoryIndex, equippedTools);
    }

    /**
     * Equips {@link Tool} to selected slot.
     *
     * @param equipSlotIndex Selected slot.
     * @param inventoryIndex Index of {@link Tool} to equip.
     */
    public void equipTool(int equipSlotIndex, int inventoryIndex)
    {
        inventory.moveOneInto(inventoryIndex, equipSlotIndex, equippedTools);
    }

    /**
     * Unequips {@link Tool} to first empty inventory slot.
     *
     * @param equipSlotIndex Index of {@link Tool} to unequip.
     * @return Place in inventory index.
     */
    public int unequipTool(int equipSlotIndex)
    {
        return equippedTools.moveOneInto(equipSlotIndex, inventory);
    }

    /**
     * Unequips {@link Tool} to selected inventory slot.
     *
     * @param inventoryIndex Selected slot.
     * @param equipSlotIndex Index of {@link Tool} to unequip.
     */
    public void unequipTool(int inventoryIndex, int equipSlotIndex)
    {
        equippedTools.moveOneInto(equipSlotIndex, inventoryIndex, inventory);
    }

    /**
     * Rewards the {@link Player} with a new {@link Tool}.
     * {@link Tool} is put into {@link Player} inventory.
     *
     * @param reward The {@link Tool} the {@link Player} is being rewarded with.
     * @return Returns index of where the reward was placed in inventory.
     */
    public int rewardTool(Tool reward) //TODO: add try-catch block for when the player has a full inventory
    {
        return inventory.moveInside(reward);
    }

    /**
     * Rewards the {@link Player} with a new {@link PassiveItem}.
     * {@link PassiveItem} is put into {@link Player} passives.
     *
     * @param reward The {@link PassiveItem} the {@link Player} is being rewarded with.
     * @return Returns index of where the reward was placed in passives.
     */
    public int rewardPassive(PassiveItem reward)
    {
        return passives.moveInside(reward);
    }

    /**
     * @return Returns number of equipped {@link Tool}s.
     */
    public int getEquippedCount()
    {
        return equippedTools.filledSlots();
    }

    /**
     * @return Returns number of unequipped {@link Tool}s in inventory.
     */
    public int getUnequippedCount()
    {
        return inventory.filledSlots();
    }

    /**
     * @return Returns number of {@link PassiveItem}s the {@link Player} has.
     */
    public int getPassivesCount()
    {
        return passives.filledSlots();
    }

    /**
     * Mandatory to set target before executing.
     * <p>
     * Executes the {@link io.github.some_example_name.actions.BasicAction}
     * of the current selected {@link Tool}
     * from the equippedSlots on selected target {@link Entity}.
     *
     * @throws PlayerException Throws if selected {@link Tool} is null.
     */
    public void doMove() throws PlayerException
    {
        if (equippedTools.getTool(selectedTool) == null)
        {
            throw new PlayerException("Trying to act on nothing!");
        }

        Tool actingTool = new Tool(equippedTools.getTool(selectedTool));

        int targetChange = actingTool.getMove().getTargetChange();

        targetChange *= (int) damageModifier;

        actingTool.getMove().setTargetChange(targetChange);

        actingTool.execute(this, super.getTarget());

        /// performs attack animation //the lines of code below should really be moved to their own method
        animatePolearmAttack();
        setTurnOver(true);
    }


    /**
     * Provides buttons that execute the {@link io.github.some_example_name.actions.BasicAction}
     * of the according {@link Tool} from equippedSlots.
     *
     * @return {@link Table} with {@link Button}s to be added to
     * a {@link com.badlogic.gdx.scenes.scene2d.Stage} or {@link Table}.
     */
    public Table provideMoveButtons(boolean enemyTurnOver)
    {
        Table moveButtons = new Table();
        Skin skin = new Skin(Gdx.files.internal("button/Buttons.json"));
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
                    if (enemyTurnOver)
                    {
                        setSelectedTool(tmpCurrentSlot);
                        doMove();
                    }
                }
            });
        }

        return moveButtons;
    }

    /**
     * {@link Button}s to be used specifically in the {@link InventoryScreen}.
     * <p>
     * Pressing one of these {@link Button}s will unequip the according {@link Tool} and move it
     * to the inventory.
     *
     * @param inventoryScreen Mandatory parameter for updating {@link Button}s correctly.
     * @return Returns {@link Table} to be added to a {@link com.badlogic.gdx.scenes.scene2d.Stage}
     * or {@link Table}.
     */
    public Table provideEquippedSlots(InventoryScreen inventoryScreen)
    {
        Table equipTable = new Table();
        Skin skin = new Skin(Gdx.files.internal("button/Buttons.json"));

        equipTable.setFillParent(true);
        equipTable.bottom().pad(15);
        equipTable.defaults().growX().padLeft(10).padRight(10).height(50);

        //equipTable.setDebug(true);

        for (int currentSlot = 0; currentSlot < EQUIP_SLOTS; currentSlot++)
        {
            if (equippedTools.getTool(currentSlot) == null)
            {
                TextButton blankButton = new TextButton(" ", skin);
                blankButton.setDisabled(true);
                equipTable.add(blankButton).uniform();
                continue;
            }

            String toolName = equippedTools.getTool(currentSlot).getName();
            TextButton moveButton = new TextButton(toolName, skin);
            equipTable.add(moveButton).uniform();

            final int tmpCurrentSlot = currentSlot;
            moveButton.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    equippedTools.moveOneInto(tmpCurrentSlot, inventory);
                    inventoryScreen.updateButtons();
                }
            });
        }

        return equipTable;
    }

    /**
     * {@link Button}s to be used specifically in the {@link InventoryScreen}.
     * <p>
     * Pressing one of these buttons will equip the according {@link Tool} and move it
     * to equippedSlots.
     *
     * @param inventoryScreen Mandatory parameter for updating {@link Button}s correctly.
     * @return Returns {@link Table} to be added to a {@link com.badlogic.gdx.scenes.scene2d.Stage}
     * or {@link Table}.
     */
    public Table provideInventory(InventoryScreen inventoryScreen)
    {
        Table inventoryTable = new Table();
        Skin skin = new Skin(Gdx.files.internal("button/Buttons.json"));

        inventoryTable.setFillParent(true);
        inventoryTable.bottom().pad(15);
        inventoryTable.defaults().growX().padLeft(10).padRight(10).height(50);

        //inventoryTable.setDebug(true);

        for (int currentSlot = 0; currentSlot < INVENTORY_SLOTS; currentSlot++)
        {
            if (currentSlot > 0 && currentSlot % 5 == 0)
            {
                inventoryTable.row();
            }

            if (inventory.getTool(currentSlot) == null)
            {
                TextButton blankButton = new TextButton(" ", skin);
                blankButton.setDisabled(true);
                inventoryTable.add(blankButton).uniform();
                continue;
            }

            String toolName = inventory.getTool(currentSlot).getName();
            TextButton moveButton = new TextButton(toolName, skin);
            inventoryTable.add(moveButton).uniform();

            final int tmpCurrentSlot = currentSlot;
            moveButton.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    inventory.moveOneInto(tmpCurrentSlot, equippedTools);
                    inventoryScreen.updateButtons();
                }
            });
        }

        return inventoryTable;
    }

    /**
     * Provides dynamic health bar
     *
     * @param batch - spritebatch of screen
     */
    public void provideHealthBar(SpriteBatch batch)
    {
        batch.draw(new Texture("other/barborder_temp1.png"), 1.25f, 4.75f, 2.5f, 0.2f);
        float maxWidth = 2.4f;
        float width = (float) getHealth() / getMaxHealth() * maxWidth;
        batch.draw(new Texture("other/healthBar.png"), 1.3f, 4.8f, width, 0.1f);
    }

    /// animation testing

    /// sets up all animations
    /// (will probably have to somehow optimize this based on weapon type)
    public void setupAnimations()
    {
        Map<String, Float> animData = new HashMap<>();
        animData.put("GardenerEmptyIdle", 0.75f);
        animData.put("GardenerPolearmIdle", 0.75f);
        animData.put("GardenerEmptyAttack", 0.08f);
        animData.put("GardenerPolearmAttack", 0.08f);
        animData.put("GardenerEmptyDamage", 0.1f);
        animData.put("GardenerPolearmDamage", 0.1f);

        Animations playerAnimations = new Animations("atlas/FemaleType1Atlas.atlas", animData);
        setAnimations(playerAnimations);
        setDefaultAnimation("GardenerEmptyIdle");
    }

    /// temp attack animation
    public void animatePolearmAttack() throws PlayerException
    {
        performAnimation("GardenerPolearmAttack");
    }

    public void animateArmMovement()
    {
        performAnimation("GardenerEmptyAttack");
    }

    public void animatePolearmIdle()
    {
        performAnimation("GardenerPolearmIdle");
    }

    public void animateTakeEmptyDamage()
    {
        performAnimation("GardenerEmptyDamage");
    }

    public void animateTakePolearmDamage()
    {
        performAnimation("GardenerPolearmDamage");
    }

    /// rough structure of animations when inventory and weapon types are implemented
    /*
    public void animateAttack() {
        String weaponType = getWeaponType();
        String animName = "Gardener" + weaponType + "Attack";
        performAnimation(animName);
    }

    public void animateIdle() {
        String weaponType = getWeaponType();
        performAnimation("Gardener" + weaponType + "Idle");
    }

    public void animateTakeDamage() {
        String weaponType = getWeaponType();
        performAnimation("Gardener" + weaponType + "Damage");
    }
     */
}
