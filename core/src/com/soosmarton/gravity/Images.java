package com.soosmarton.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marton on 7/17/16.
 */
public class Images {
    static HashMap<String, Sprite> sprites;
    static ArrayList<String> names;
    public static Sound cSound;

    public static void load(){
        sprites = new HashMap<String, Sprite>();
        names = new ArrayList<String>();

        FileHandle handle = Gdx.files.internal("sprite_list");
        String data = handle.readString();

        String[] lines = data.split("\n");

        for(String line:lines){
            sprites.put(line, new Sprite(new Texture(line)));
            names.add(line);
        }

        cSound = Gdx.audio.newSound(Gdx.files.internal("sounds/c_sound.mp3"));
    }

    public static Sprite getImage(String s){
        return sprites.get(s);
    }

    public static void dispose(){
        for(String s:names){
            sprites.get(s).getTexture().dispose();
        }
    }
}
