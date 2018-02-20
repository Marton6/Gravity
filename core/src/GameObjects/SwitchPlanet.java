package GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.soosmarton.gravity.Images;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/14/16.
 */
public class SwitchPlanet extends Planet {
    private boolean on;
    private Sprite sprite_off;
    private float def_mass;

    public SwitchPlanet(float x, float y, float r, float mass, Level lvl) {
        super(x, y, r, 0, "planet_switch.png", lvl);

        sprite_off = Images.getImage("path_planet.png");
        on = false;
        def_mass = mass;
    }

    @Override
    public void draw(Batch batch) {
        if(on)super.draw(batch);
        else batch.draw(sprite_off, x-w/2, y-h/2, w, h);
    }

    @Override
    public void tap() {
        if(on){
            on = false;
            mass = 0;
        }
        else{
            on=true;
            mass = def_mass;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
