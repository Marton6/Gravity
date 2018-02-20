package com.soosmarton.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.io.BufferedReader;
import java.util.ArrayList;

import GameObjects.AG_Field;
import GameObjects.Asteroid;
import GameObjects.DarkMatter;
import GameObjects.PathAsteroid;
import GameObjects.Planet;
import GameObjects.SpawnPoint;
import GameObjects.SwitchPlanet;
import GameObjects.TargetPlanet;
import UI.Button;

/**
 * Created by marton on 7/2/16.
 *
 */

//Todo: Level Parser/Loader and levels

public class Level {
    private ArrayList<GameObject> gameObjects, temp, temp_del, ui, ui_temp, ui_temp_del;
    private boolean queued, queued_del, ui_queued, ui_queued_del;
    private Camera camera;
    private Vector2 SpawnPoint;
    public int currentLevel, levelToLoad;
    public boolean canFire;
    private float alpha;
    PathAsteroid a1, a2, a3, a4;

    public Level(Camera camera) {
        gameObjects=new ArrayList<GameObject>();

        temp = new ArrayList<GameObject>();
        temp_del = new ArrayList<GameObject>();
        queued = false;
        queued_del = false;

        ui = new ArrayList<GameObject>();

        ui_temp = new ArrayList<GameObject>();
        ui_temp_del = new ArrayList<GameObject>();
        ui_queued = false;
        ui_queued_del = false;

        this.camera = camera;

        levelToLoad = -2;
        currentLevel = -1;

        alpha = 1;

        a1 = new PathAsteroid(-1, -1, 1, this);
        a2 = new PathAsteroid(-1, -1, .75f, this);
        a3 = new PathAsteroid(-1, -1, .5f, this);
        a4 = new PathAsteroid(-1, -1, .25f, this);

        canFire = true;
    }

    public void spawnObject(GameObject go){
        temp.add(go);
        queued = true;
    }

    public void removeObject(GameObject go){
        queued_del = true;
        temp_del.add(go);
    }

    public void draw(Batch batch){
        if(alpha >=0)
            batch.setColor(1, 1, 1, alpha);
        else {
            batch.setColor(1, 1, 1, .001f);
            alpha = 1;
        }
        for(GameObject go:gameObjects){
            go.draw(batch);
        }
        for(GameObject go:ui){
            go.draw(batch);
        }
    }

    public void update(float t){
        for(GameObject go:gameObjects){
            go.update(t);
        }
        for(GameObject go:ui){
            go.update(t);
        }

        if(queued){
            gameObjects.addAll(temp);
            temp.clear();
            queued = false;
        }

        if(queued_del){
            gameObjects.removeAll(temp_del);
            temp_del.clear();
            queued_del = false;
        }

        if(ui_queued){
            ui.addAll(ui_temp);
            ui_temp.clear();
            ui_queued=false;
        }

        if(ui_queued_del){
            ui.removeAll(ui_temp_del);
            ui_temp_del.clear();
            ui_queued_del=false;
        }

        if(Gdx.input.justTouched()) {
            try {
                getUiAt(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)), null).tap();
            } catch (Exception e) {
            }

            try{
                getObjectAt(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)), null).tap();
            }
            catch (Exception e){}
        }
        if(levelToLoad != -2){
            loadLevel_i(levelToLoad);
            levelToLoad = -2;
        }
    }

    public void addUI(GameObject go){
        ui_temp.add(go);
        ui_queued = true;
    }

    public void removeUI(GameObject go){
        ui_temp_del.add(go);
        ui_queued_del = true;
    }

    public void dispose() {
        for(GameObject go:gameObjects){
            go.dispose();
        }
    }

    public GameObject getObjectAt(float x, float y, GameObject caller){
        for(GameObject go:gameObjects){
            if(go.x - go.w/2 <= x && (go.x + go.w/2) >= x && go.y - go.h/2<=y && (go.y + go.h/2) >= y && go!=caller)return go;
        }
        return null;
    }

    public GameObject getObjectAt(Vector3 v, GameObject caller){
        return getObjectAt(v.x, v.y, caller);
    }

    public GameObject getUiAt(float x, float y, GameObject caller){
        for(GameObject go:ui){
            if(go.x - go.w/2 <= x && (go.x + go.w/2) >= x && go.y - go.h/2<=y && (go.y + go.h/2) >= y && go!=caller)return go;
        }
        return null;
    }

    public GameObject getUiAt(Vector3 v, GameObject caller){
        return getUiAt(v.x, v.y, caller);
    }

    public void empty() {
        temp_del.addAll(gameObjects);
        queued_del = true;

        ui_temp_del.addAll(ui);
        ui_queued_del = true;
    }

    public ArrayList<GameObject> getObjects() {
        return gameObjects;
    }

    public void loadLevel(int level) {
        levelToLoad = level;
    }

    private void loadLevel_i(int level) {
        empty();
        alpha = 1;
        currentLevel = level;
        System.out.println(currentLevel);
        SpawnPoint = null;

        a1 = new PathAsteroid(-1, -1, 1, this);
        a2 = new PathAsteroid(-1, -1, .75f, this);
        a3 = new PathAsteroid(-1, -1, .5f, this);
        a4 = new PathAsteroid(-1, -1, .25f, this);

        ui.add(a1);
        ui.add(a2);
        ui.add(a3);
        ui.add(a4);

        FileHandle handle = Gdx.files.internal("levels/"+level+".lvl");
        String data = handle.readString();

        String[] lines = data.split("\n");

        for(String line:lines){
            String[] info = line.split(";");
            if(info[0].matches("B")){
                //Button
                addUI(new Button(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]),
                        Integer.parseInt(info[5]), "ui/"+info[6], Boolean.parseBoolean(info[7]), this));
            }
            else if(info[0].matches("P")){
                //Planet
                spawnObject(new Planet(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]), this));
            }
            else if(info[0].matches("TP")){
                //Planet
                spawnObject(new TargetPlanet(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]), this));
            }
            else if (info[0].matches("AP")){
                //Switch Planet
                spawnObject(new SwitchPlanet(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]), this));
            }
            else if(info[0].matches("A")){
                //Asteroid
                spawnObject(new Asteroid(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]), this));
            }
            else if(info[0].matches("AGF")){
                //Asteroid
                spawnObject(new AG_Field(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]), this));
            }
            else if(info[0].matches("DM")){
                //Asteroid
                spawnObject(new DarkMatter(Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                        Float.parseFloat(info[3]), Float.parseFloat(info[4]), this));
            }
            else if(info[0].matches("SP")){
                SpawnPoint = new Vector2();
                SpawnPoint.x = Float.parseFloat(info[1]);
                SpawnPoint.y = Float.parseFloat(info[2]);

                addUI(new SpawnPoint(SpawnPoint.x, SpawnPoint.y, this));
                canFire = true;
            }
        }
    }

    public Vector2 getSpawnPoint() {
        return SpawnPoint;
    }

    public void showAim(float x, float y) {
        if(SpawnPoint==null)return;

        a1.setPos(SpawnPoint.x + .2f * x, SpawnPoint.y + .2f * y);
        a2.setPos(SpawnPoint.x + .4f * x, SpawnPoint.y + .4f * y);
        a3.setPos(SpawnPoint.x + .6f * x, SpawnPoint.y + .6f * y);
        a4.setPos(SpawnPoint.x + .8f * x, SpawnPoint.y + .8f * y);

    }

    public void hideAim() {
        a1.setPos(-1, -1);
        a2.setPos(-1, -1);
        a3.setPos(-1, -1);
        a4.setPos(-1, -1);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}