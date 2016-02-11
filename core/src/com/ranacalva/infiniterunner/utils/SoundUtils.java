package com.ranacalva.infiniterunner.utils;


import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundUtils {

    Sound jump = Gdx.audio.newSound(Gdx.files.getFileHandle("data/Jump.wav", Files.FileType.Internal));
    Sound hit = Gdx.audio.newSound(Gdx.files.getFileHandle("data/Hit_Hurt.wav", Files.FileType.Internal));


    public void PlaySound_Jump(){
        jump.play();
    }

    public void PlaySound_Hit(){
        hit.play();
    }
}
