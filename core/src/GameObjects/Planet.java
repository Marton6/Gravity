package GameObjects;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Images;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/11/16.
 */
public class Planet extends GameObject {

    public Planet(float x, float y, float r, float mass, Level lvl) {
        super(x, y, r*2, r*2, mass, "planet.png", lvl);
    }

    public Planet(float x, float y, float r, float mass, String sprite_name, Level lvl) {
        super(x, y, r*2, r*2, mass, sprite_name, lvl);
    }

    @Override
    public void collide() {

    }
}
