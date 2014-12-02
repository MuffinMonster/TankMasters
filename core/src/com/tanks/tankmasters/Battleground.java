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

        p = new Player(0,0);
        cam = new OrthographicCamera();
        cam.setToOrtho(false,Main.WORLDSIZE_WIDTH,Main.WORLDSIZE_HEIGHT);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        Main.batch.begin();
        Main.font.draw(Main.batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 0, Gdx.graphics.getHeight());
        Main.batch.end();

        p.render(cam,delta);


        Main.debugRenderer.render(Main.world,cam.projection);
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

    public Player getPlayer(){ return p; }

}
