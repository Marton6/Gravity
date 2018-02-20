package GameObjects;

import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Level;

import java.util.ArrayList;

/**
 * Created by marton on 7/30/16.
 */
public class AG_Field extends GameObject{
    public AG_Field(float x, float y, float w, float h, Level lvl) {
        super(x, y, w, h, 0, "ag_field.png", lvl);
        colliding = false;
    }

    @Override
    public void update(float t) {
        super.update(t);
        ArrayList<GameObject> objs = lvl.getObjects();

        for(GameObject go:objs){
            if(0 <= go.getX() - x + w/2 && go.getX() - x + w/2 <= w && go.getY() - y + h/2  >=0 && go.getY() - y + h/2 <= h)
                go.disableMass();
        }
    }
}
