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
    private String defaultAnimation = null;
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
                Animation<TextureRegion> anim = new Animation<>(duration, frames, Animation.PlayMode.NORMAL);
                animations.put(name, anim);
                if (defaultAnimation == null)
                {
                    defaultAnimation = name;
                    currentAnimation = anim;
                }
            }
        }
    }

    public Animation<TextureRegion> getAnimation(String name)
    {
        return animations.get(name);
    }

    public Animation<TextureRegion> getDefaultAnimation()
    {
        return animations.get(defaultAnimation);
    }

    public void performAnimation(String animationName)
    {
        Animation<TextureRegion> anim = animations.get(animationName);
        if (anim != null && !animationName.equals(defaultAnimation))
        {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            setCurrentAnimation(anim);
        }
    }

    public void setDefaultAnimation(String name)
    {
        if (animations.containsKey(name))
        {
            defaultAnimation = name;
            Animation<TextureRegion> anim = animations.get(name);
            anim.setPlayMode(Animation.PlayMode.LOOP);
            currentAnimation = anim;
        }
    }

    public void setCurrentAnimation(Animation<TextureRegion> animation)
    {
        this.currentAnimation = animation;
        this.stateTime = 0f;
    }

    public boolean isCurrentAnimationFinished()
    {
        return currentAnimation != null && currentAnimation.isAnimationFinished(stateTime);
    }

    public void update(float delta)
    {
        if (currentAnimation != null)
        {
            stateTime += delta;
            if (currentAnimation.isAnimationFinished(stateTime))
            {
                if (!currentAnimation.equals(animations.get(defaultAnimation)))
                {
                    Animation<TextureRegion> defaultAnim = animations.get(defaultAnimation);
                    setCurrentAnimation(defaultAnim);
                    defaultAnim.setPlayMode(Animation.PlayMode.LOOP);
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
