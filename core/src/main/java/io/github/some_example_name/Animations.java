package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class Animations
{

    private final Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private Animation<TextureRegion> currentAnimation;
    private final TextureAtlas atlas;
    private String defaultAnimationName = null;
    private float stateTime = 0f;

    public Animations(String atlasPath, Map<String, Float> animationData)
    {
        atlas = new TextureAtlas(Gdx.files.internal(atlasPath));

        for (Map.Entry<String, Float> entry : animationData.entrySet())
        {
            String name = entry.getKey();
            float duration = entry.getValue();

            Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(name);
            if (frames != null && frames.size > 0)
            {
                Animation<TextureRegion> animation = new Animation<>(duration, frames, Animation.PlayMode.NORMAL);
                animations.put(name, animation);
                if (defaultAnimationName == null)
                {
                    defaultAnimationName = name;
                    currentAnimation = animation;
                }
            }
        }
    }

    public Animation<TextureRegion> getAnimation(String name)
    {
        return animations.get(name);
    }

    public Animation<TextureRegion> getDefaultAnimationName() //why is the field a string, but the getter for it returns an animation??????
    {
        return animations.get(defaultAnimationName);
    }

    public void performAnimation(String animationName)
    {
        Animation<TextureRegion> animation = animations.get(animationName);
        if (animation != null && !animationName.equals(defaultAnimationName))
        {
            animation.setPlayMode(Animation.PlayMode.NORMAL);
            setCurrentAnimation(animation);
        }
    }

    public void setDefaultAnimationName(String name)
    {
        if (animations.containsKey(name))
        {
            defaultAnimationName = name;
            Animation<TextureRegion> animation = animations.get(name);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            currentAnimation = animation;
        }
    }

    public void setCurrentAnimation(Animation<TextureRegion> animation)
    {
        currentAnimation = animation;
        stateTime = 0f;
    }

    public boolean isCurrentAnimationFinished()
    {
        return (currentAnimation != null && currentAnimation.isAnimationFinished(stateTime));
    }

    public void update(float delta)
    {
        if (currentAnimation != null)
        {
            stateTime += delta;
            if (currentAnimation.isAnimationFinished(stateTime))
            {
                if (!currentAnimation.equals(animations.get(defaultAnimationName)))
                {
                    Animation<TextureRegion> defaultAnimation = animations.get(defaultAnimationName);
                    setCurrentAnimation(defaultAnimation);
                    defaultAnimation.setPlayMode(Animation.PlayMode.LOOP);
                }
            }
        }
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height)
    {
        if (currentAnimation != null)
        {
            boolean looping = currentAnimation.getPlayMode() == Animation.PlayMode.LOOP;
            TextureRegion frame = currentAnimation.getKeyFrame(stateTime, looping);
            batch.draw(frame, x, y, width, height);
        }
    }
}
