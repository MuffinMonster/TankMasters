package com.tanks.tankmasters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Battleground implements Screen {

    Main main;

    Player p;

    OrthographicCamera cam;

    public Battleground(Main m){
        main = m;

        p = new Player(360,620);
        cam = new OrthographicCamera();
        cam.setToOrtho(false,1280,720);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        Main.batch.begin();
        Main.font.draw(Main.batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 0, Gdx.graphics.getHeight());
        Main.batch.end();

        p.render(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        p.dispose();
    }
}
