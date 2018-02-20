package GameObjects;

import com.soosmarton.gravity.Game;
import com.soosmarton.gravity.GameObject;
import com.soosmarton.gravity.Images;
import com.soosmarton.gravity.Level;

import UI.PassWave;
import UI.Wave;

/**
 * Created by marton on 7/11/16.
 */
public class Asteroid extends GameObject {
    protected float speedX, speedY;
    public Asteroid(float x, float y, float r, float mass, Level lvl) {
        super(x, y, r*2, r*2, mass, "planet.png", lvl);
        speedX = 0;
        speedY = 0;
    }

    public Asteroid(float x, float y, float r, float mass, String sprite_name, Level lvl) {
        super(x, y, r*2, r*2, mass, sprite_name, lvl);
        speedX = 0;
        speedY = 0;
    }

    @Override
    public void update(float t) {
        float forceX=0;
        float forceY=0;

        for(GameObject go :lvl.getObjects()){
            if(go == this)continue;
            float d = getDistanceSquared(go);

            //calculate gravitational force
            forceX += (G*mass*go.getMass()/d)*(go.getX()-x)/(Math.sqrt(d));
            forceY += (G*mass*go.getMass()/d)*(go.getY()-y)/(Math.sqrt(d));

            //detect collision
            if(d<=(w/2+go.getW()/2)*(w/2+go.getW()/2)){
                if(go.colliding) {
                    collide(go);
                    go.collide();
                }
            }
        }

        if(mass != 0) {
            speedX += forceX / mass;
            speedY += forceY / mass;
        }

        x+=speedX*t;
        y+=speedY*t;

        //check if the object is out of bounds
        if(x<0||y<0||x> Game.GAME_WIDTH||y>Game.GAME_HEIGHT){
            collide(null);
        }

        //enable mass if disabled
        if(mass == 0){
            enableMass();
        }
    }

    public void collide(GameObject go) {
        Images.cSound.play();
        lvl.canFire = true;
        lvl.removeObject(this);
        if(go instanceof TargetPlanet){
            lvl.addUI(new PassWave(x, y, lvl));
        }
        else lvl.addUI(new Wave(x, y, lvl));
    }

    public void applyVelocity(float velX, float velY){
        speedX+=velX;
        speedY+=velY;
    }
}
