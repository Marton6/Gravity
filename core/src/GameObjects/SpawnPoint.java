package GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/13/16.
 */
public class SpawnPoint extends GameObject {
    public SpawnPoint(float x, float y, Level lvl) {
        super(x, y, 3f, 3f, 0, "planet.png", lvl);
    }

    @Override
    public void draw(Batch batch) {
        if(lvl.canFire) super.draw(batch);
    }
}
