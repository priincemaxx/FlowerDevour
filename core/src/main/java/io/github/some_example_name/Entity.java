package io.github.some_example_name;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.some_example_name.actions.ActionException;


/**
 * Base class for Enemies and the Player.
 * <p>
 * Holds basic statistics like health, name, etc.
 * <p>
 * Each Entity has a target Entity. This field is used
 * in combination with the doMove() method.
 * <p>
 * At it's most basic, doMove() should somehow set the
 * targets for a BasicAction and then execute it.
 * <p>
 * The turn of an Entity should look like this:
 * <p></p>
 * 1. Set the targets of the BasicAction.
 * <p>
 * 2. Execute the BasicAction.
 * </p>
 * 3. Pass the turn.
 */
public class Entity implements Serializable
{
    private int health;
    private int maxHealth;
    private String name;
    private Entity target;
    private Texture texture; //should later replace with TextureRegion class
    private boolean turnOver;

    protected Animations animations;
    protected Animation<TextureRegion> currentAnimation;
    protected float stateTime = 0f;

    public Entity()
    {

    }

    public Entity(int health, int maxHealth, String name)
    {
        setMaxHealth(maxHealth);
        setHealth(health);
        setName(name);
    }

    public Entity(int health, int maxHealth, String name, Texture texture)
    {
        setMaxHealth(maxHealth);
        setHealth(health);
        setName(name);
        setTexture(texture);
    }


    //for copying objects
    public Entity(Entity entity) throws EntityException
    {
        if (entity == null)
        {
            throw new EntityException("Trying to copy nothing.");
        }

        setMaxHealth(entity.getMaxHealth());
        setHealth(entity.getHealth());
        setName(entity.getName());
        //etc. TODO: copy remaining fields.
    }

    public int getHealth()
    {
        return health;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public String getName()
    {
        return name;
    }

    public Entity getTarget()
    {
        return target;
    }

    public Texture getTexture()
    {
        return texture;
    }

    /**
     * @param health New health value, it cannot exceed maxHealth.
     */
    public void setHealth(int health)
    {
        if (health >= maxHealth)
        {
            this.health = this.maxHealth;
            return;
        }
        if (health <= 0)
        {
            this.health = 0;
            return;
        }

        this.health = health;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Mandatory to set up before using doMove()
     *
     * @param target The Entity that will be targeted.
     * @throws ActionException
     */
    public void setTarget(Entity target) throws ActionException
    {
        if (target == null)
        {
            throw new ActionException("Attempting to target nothing!");
        }

        this.target = target;
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public void setTurnOver(boolean value)
    {
        turnOver = value;
    }

    public boolean getTurnOver()
    {
        return turnOver;
    }

    /**
     * Abstract method.
     * Executes selected BasicAction on target.
     */
    public void doMove()
    {

    }

    public boolean isDead()
    {
        if (getHealth() == 0)
        {
            return true;
        }
        return false;
    }

    public void setAnimations(Animations animations)
    {
        this.animations = animations;
    }

    /// sets default animation //redundant comment, improve it with actual documentation or remove it
    public void setDefaultAnimation(String animationName)
    {
        if (animations != null)
        {
            animations.setDefaultAnimation(animationName);
            this.currentAnimation = animations.getDefaultAnimation();
        }
    }

    /**
     * Performs animation once and goes back to default.
     *
     * @param animationName Name of animation to perform.
     */
    public void performAnimation(String animationName)
    {
        if (animations != null)
        {
            animations.performAnimation(animationName);
        }
    }


    public boolean isAnimationFinished()
    {
        return animations.isCurrentAnimationFinished();
    }

    public void update(float delta)
    {
        if (animations != null)
        {
            animations.update(delta);
        } else
        {
            stateTime += delta;
        }
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height)
    {
        if (animations != null)
        {
            animations.draw(batch, x, y, width, height);
        } else if (texture != null)
        {
            batch.draw(texture, x, y, width, height);
        }
    }

    @Override
    public String toString()
    {
        return "Entity{" +
            "health=" + health +
            ", maxHealth=" + maxHealth +
            ", name='" + name + '\'' +
            '}';
    }
}
