package UI;

import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/30/16.
 */
public class PassWave extends GameObject {
    private float speed;

    public PassWave(float x, float y, Level lvl) {
        super(x, y, 0, 0, 0, "planet.png", lvl);
        speed = 600;
    }

    public void update(float t) {
        w += t*speed;
        h += t*speed;

        if(w >= 300){
            lvl.removeUI(this);
        }
    }
}
