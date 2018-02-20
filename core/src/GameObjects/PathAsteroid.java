package GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/13/16.
 */
public class PathAsteroid extends Asteroid {

    public PathAsteroid(float x, float y, float r, Level lvl) {
        super(x, y, r, 0, "path_planet.png", lvl);
    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch) {
        if(lvl.canFire)super.draw(batch);
    }

    @Override
    public void update(float t) {

    }

    @Override
    public void collide() {

    }
}
