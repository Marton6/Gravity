package com.soosmarton.gravity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by marton on 7/2/16.
 */
public class GameObject {
    public static final float G = 6.67f;
    public boolean colliding;
    protected float x, y, w, h, mass, tmass;
    private Sprite sprite;
    protected Level lvl;

    public GameObject(float x, float y, float w, float h, float mass, String sprite_name, Level lvl) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.mass = mass;
        this.lvl = lvl;
        this.colliding = true;

        sprite = Images.getImage(sprite_name);

        tmass = mass;
    }

    public void draw(Batch batch){
        batch.draw(sprite, x-w/2, y-h/2, w, h);
    }

    public float getDistanceSquared(GameObject go){
        return (x-go.getX())*(x-go.getX())+(y-go.getY())*(y-go.getY());
    }

    public void update(float t){}

    public void tap(){}

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public float getMass() {
        return mass;
    }

    public void collide(){

    }

    public void dispose() {

    }

    public void disableMass(){
        tmass = mass;
        mass = 0;
    }

    public void enableMass(){
        mass = tmass;
    }
}
