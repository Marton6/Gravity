package com.soosmarton.gravity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import GameObjects.Asteroid;
import GameObjects.Planet;

public class Game extends ApplicationAdapter implements InputProcessor{
	public final static float GAME_HEIGHT = 150;
	public final static float GAME_WIDTH = 100;

	private SpriteBatch batch;
	private Level lvl;
	private OrthographicCamera camera;
	private FitViewport viewport;

	private float startX, startY, endX, endY;

	@Override
	public void create () {
		batch = new SpriteBatch();

		float aspect_ratio = Gdx.graphics.getHeight()/Gdx.graphics.getWidth();

		camera = new OrthographicCamera();
		viewport = new FitViewport(GAME_WIDTH*aspect_ratio, GAME_HEIGHT, camera);
		viewport.apply();
		camera.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);

        Images.load();

		lvl = new Level(camera);
		//spawn objects
        /*lvl.spawnObject(new Planet(50, 75, 15, 10, lvl));
        lvl.spawnObject(new Planet(40, 40, 15, 10, lvl));
        lvl.spawnObject(new Asteroid(20, 100, 2, 1, lvl));*/
		startX = 0;
		startY = 0;
		endX = 0;
		endY = 0;

		lvl.loadLevel(-1);

        Gdx.input.setInputProcessor(this);
    }

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        lvl.draw(batch);

		batch.end();
        lvl.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		batch.dispose();
        lvl.dispose();
        Images.dispose();
	}

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        startX = screenX;
        startY = screenY;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 v3Start = new Vector3(startX, startY, 0);
        Vector3 v3End = new Vector3(endX, endY, 0);

        camera.unproject(v3Start);
        camera.unproject(v3End);

        try{
            if(lvl.canFire && (Math.abs(v3End.x -v3Start.x) > 20 || Math.abs(v3End.y - v3Start.y) > 20)){
                Asteroid a = new Asteroid(lvl.getSpawnPoint().x, lvl.getSpawnPoint().y, 1.5f, 1, lvl);
                a.applyVelocity(v3End.x -v3Start.x, v3End.y - v3Start.y);
                lvl.spawnObject(a);
                lvl.canFire = false;
            }
        }catch(Exception e){}

        lvl.hideAim();

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        endX = screenX;
        endY = screenY;

        Vector3 v3Start = new Vector3(startX, startY, 0);
        Vector3 v3End = new Vector3(endX, endY, 0);

        camera.unproject(v3Start);
        camera.unproject(v3End);

        lvl.showAim(v3End.x - v3Start.x, v3End.y - v3Start.y);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}