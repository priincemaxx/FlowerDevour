package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

public class Animations {
    private Animation<TextureRegion> animation;

    public Animations(String atlasPath, String regionPrefix, float frameDuration) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));

        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(regionPrefix);
        animation = new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
