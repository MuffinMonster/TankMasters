package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu implements Screen {


    private Main main;

    private OrthographicCamera cam;
    private String menuStr = "Press any key to start!";

    private float fW = 0, fH = 0;

    public Menu(Main m){
        main = m;
        fW = Main.font.getBounds(menuStr).width;
        fH = Main.font.getBounds(menuStr).height;
        cam = new OrthographicCamera();
        cam.setToOrtho(false,1280,720);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        Main.batch.begin();
        Main.font.draw(Main.batch,menuStr,Gdx.graphics.getWidth()/2- fW/2,Gdx.graphics.getHeight()/2-fH/2);
        Main.batch.end();

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

    }
}
