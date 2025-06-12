package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.some_example_name.passives.PassiveContainer;
import io.github.some_example_name.passives.PassiveItem;
import io.github.some_example_name.tools.Tool;
import io.github.some_example_name.tools.ToolContainer;
import io.github.some_example_name.tools.ToolContainerException;

import java.util.HashMap;
import java.util.Map;

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
        Tool actingTool = equippedTools.getTool(selectedTool);

        if (actingTool == null)
        {
            throw new PlayerException("Trying to act on nothing!");
        }

        /// does Polearm type weapon animation
        if (animations != null && animations.getAnimation("PolearmAttack") != null) {
            setCurrentAnimation(animations.getAnimation("PolearmAttack"));
        }

        actingTool.execute(this, super.getTarget());
    }


    /** Provides buttons that do the associated action.
     * @return Table with buttons to be added to a stage.
     */
    public Table provideMoveButtons()
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
                    setSelectedTool(tmpCurrentSlot);
                    doPolearmAttack();
                    //doMove();
                    //System.out.println("I work!");
                }
            });
        }

        return moveButtons;
    }

    /**
     * Provides dynamic health bar
     * @param batch - spritebatch of screen
     */
    public void provideHealthBar(SpriteBatch batch) {
        batch.draw(new Texture("other/barborder_temp1.png"), 1.25f, 4.65f, 2.5f, 0.2f);
        float maxWidth = 2.4f;
        float width = (float) getHealth() /getMaxHealth() * maxWidth;
        batch.draw(new Texture("other/healthBar.png"), 1.3f, 4.7f, width, 0.1f);
    }

    public boolean isDead() {
        if(getHealth() == 0) {
            return true;
        }
        return false;
    }

    /// animation testing

    private Animations animations;

    public void setupAnimations() {
        Map<String, Float> animData = new HashMap<>();
        animData.put("GardenerEmptyIdle", 0.75f);
        animData.put("GardenerPolearmIdle", 0.75f);
        animData.put("GardenerEmptyAttack", 0.08f);
        animData.put("GardenerPolearmAttack", 0.08f);
        animData.put("GardenerEmptyDamage", 0.1f);
        animData.put("GardenerPolearmDamage", 0.1f);


        Animations playerAnimations = new Animations("atlas/FemaleType1Atlas.atlas", animData);
        setAnimations(playerAnimations);
        setCurrentAnimation(playerAnimations.getAnimation("GardenerPolearmIdle"));
    }

    public void doPolearmAttack() throws PlayerException {
        Tool actingTool = equippedTools.getTool(selectedTool);

        if (actingTool == null)
        {
            throw new PlayerException("Trying to act on nothing!");
        }

        performAnimation("GardenerPolearmAttack");
        actingTool.execute(this, super.getTarget());
    }

    public void doArmMovement() {
        performAnimation("GardenerEmptyAttack");
    }

    public void doEmptyIdle() {
        performAnimation("GardenerEmptyIdle");
    }

    public void doPolearmIdle() {
        performAnimation("GardenerPolearmIdle");
    }

    public void takeEmptyDamage() {
        performAnimation("GardenerEmptyDamage");
    }

    public void takePolearmDamage() {
        performAnimation("GardenerPolearmDamage");
    }

    @Override
    public String setIdle() {
        return "GardenerPolearmIdle";
    }

}
