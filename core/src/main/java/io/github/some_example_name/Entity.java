package io.github.some_example_name;

//TODO: add texture and associated method for drawing.

import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.some_example_name.actions.ActionException;

public class Entity implements Serializable
{
    private int health;
    private int maxHealth;
    private String name;
    private Entity target;
    private Texture texture; //should later replace with TextureRegion class

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

    /**
     * Executes selected action on target.
     */
    public void doMove()
    {

    }

    public void provideHealthBar() {

    }

    public boolean isDead()
    {
        return false;
    }

    public void setAnimations(Animations animations) {
        this.animations = animations;
    }

    /// sets default animation
    public void setDefaultAnimation(String animationName) {
        if (animations != null) {
            animations.setDefaultAnimation(animationName);
            this.currentAnimation = animations.getDefaultAnimation();
        }
    }

    /// performs animation once and goes back to default
    public void performAnimation(String animationName) {
        if (animations != null) {
            animations.performAnimation(animationName);
        }
    }

    public void update(float delta) {
        if (animations != null) {
            animations.update(delta);
        } else {
            stateTime += delta;
        }
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        if (animations != null) {
            animations.draw(batch, x, y, width, height);
        } else if (texture != null) {
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
