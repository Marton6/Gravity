package UI;

import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/30/16.
 */
public class Wave extends GameObject {
    private float speed;

    public Wave(float x, float y, Level lvl) {
        super(x, y, 0, 0, 0, "ui/wave.png", lvl);
        speed = 300;
    }

    public void update(float t) {
        w += t*speed;
        h += t*speed;
        speed/=2;

        if(speed<=1){
            lvl.removeUI(this);
        }
    }
}
