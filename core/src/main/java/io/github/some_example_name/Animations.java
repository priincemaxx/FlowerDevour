package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class Animations {
    private final java.util.Map<String, Animation<TextureRegion>> animations = new HashMap<>();    private final TextureAtlas atlas;
    private String defaultAnimation = null;

    public Animations(String atlasPath, Map<String, Float> animationData) {
        atlas = new TextureAtlas(Gdx.files.internal(atlasPath));

        for (java.util.Map.Entry<String, Float> entry : animationData.entrySet()) {
            String name = entry.getKey();
            float duration = entry.getValue();

            Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(name);
            if (frames != null && frames.size > 0) {
                Animation<TextureRegion> anim = new Animation<>(duration, frames, Animation.PlayMode.NORMAL);
                animations.put(name, anim);
                if (defaultAnimation == null) {
                    defaultAnimation = name;
                }
            }
        }
    }

    public Animation<TextureRegion> getAnimation(String name) {
        return animations.get(name);
    }

    public Animation<TextureRegion> getDefaultAnimation() {
        return animations.get(defaultAnimation);
    }
}
