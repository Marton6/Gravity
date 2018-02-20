package GameObjects;

import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/30/16.
 */
public class DarkMatter extends Planet {
    public DarkMatter(float x, float y, float r, float mass, Level lvl) {
        super(x, y, r, mass, "ui/wave.png", lvl);
    }
}
