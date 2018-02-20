package GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.soosmarton.gravity.Level;

import UI.PassWave;

/**
 * Created by marton on 7/12/16.
 */
public class TargetPlanet extends Planet {
    private boolean switchLvl;
    private float switchTime;

    public TargetPlanet(float x, float y, float r, float mass, Level lvl) {
        super(x, y, r, mass, "target_planet.png", lvl);
        switchLvl = false;
        switchTime = .5f;
    }

    @Override
    public void update(float t) {
        if(switchLvl){
            if(switchTime <= 0){
                lvl.loadLevel(lvl.currentLevel+1);
                switchLvl = false;
            }
            switchTime -= t;
        }
        super.update(t);
    }

    @Override
    public void collide() {
        super.collide();
        switchLvl = true;
    }
}
