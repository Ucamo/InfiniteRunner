package com.ranacalva.infiniterunner.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundUtils {

    Sound jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
    Sound hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit_hurt.wav"));

    public void PlaySound_Jump(){
        jump.play();
    }

    public void PlaySound_Hit(){
        hit.play();
    }
}
