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

    private Animations animations;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    public void setAnimations(Animations animations) {
        this.animations = animations;
    }

    public void setAnimations(Animations animations, String defaultAnimation) {
        this.animations = animations;
        this.currentAnimation = animations.getAnimation(defaultAnimation); // default animation
    }

    public void performAnimation(String animationName) {
        if (animations != null && animations.getAnimation(animationName) != null) {
            setCurrentAnimation(animations.getAnimation(animationName));
        }

    }

    public void setCurrentAnimation(Animation<TextureRegion> animation) {
        this.currentAnimation = animation;
        this.stateTime = 0f;
    }

    public void update(float delta) {
        stateTime += delta;
        if (currentAnimation != null && currentAnimation.isAnimationFinished(stateTime)) {
            Animation<TextureRegion> idle = animations.getAnimation(setIdle());
            if (idle != null) {
                setCurrentAnimation(idle);
            }
        }
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        if (currentAnimation != null) {
            TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
            batch.draw(frame, x, y, width, height);
        }
    }

    public String setIdle() {
        return "DefaultIdle";
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
