package UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Level;

/**
 * Created by marton on 7/12/16.
 */
public class Button extends GameObject {
    protected int level;
    protected BitmapFont bf;
    protected boolean pressed;
    protected boolean showText;

    public Button(float x, float y, float w, float h, int level, String sprite_name, boolean showText, Level lvl) {
        super(x, y, w, h, 0, sprite_name, lvl);
        this.level = level;
        pressed = false;
        this.showText = showText;
        bf = new BitmapFont();
        bf.getData().setScale(.2f, .2f);
    }

    @Override
    public void update(float t) {
        if(pressed) {
            lvl.setAlpha(lvl.getAlpha() - 2*t);
            if(lvl.getAlpha() <=.01){
                pressed = false;
                //lvl.setAlpha(1);
                lvl.loadLevel(level);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(showText)
            bf.draw(batch, ""+level, x, y);
    }

    @Override
    public void tap() {
        pressed = true;
    }
}
