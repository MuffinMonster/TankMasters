package com.tanks.tankmasters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Battleground implements Screen {

    Main main;

    Player p;

    OrthographicCamera cam;

    Body border1;
    Body border2;
    Body border3;
    Body border4;

    public Battleground(Main m){
        main = m;

        p = new Player(0,0);
        cam = new OrthographicCamera();
        cam.setToOrtho(false,Main.WORLDSIZE_WIDTH,Main.WORLDSIZE_HEIGHT);

        setupWorldPhysicsBorders();
    }

    private void setupWorldPhysicsBorders() {

        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape pShape = new PolygonShape();

        //Top
        bDef.position.set(0,Main.WORLDSIZE_HEIGHT/2);
        pShape.setAsBox(Main.WORLDSIZE_WIDTH/2,2);
        border1 = Main.world.createBody(bDef);
        border1.createFixture(pShape,1.0f);
        //Bottom
        bDef.position.set(0,-Main.WORLDSIZE_HEIGHT/2);
        border2 = Main.world.createBody(bDef);
        border2.createFixture(pShape,1.0f);
        //Left
        bDef.position.set(-Main.WORLDSIZE_WIDTH/2,0);
        pShape.setAsBox(2,Main.WORLDSIZE_HEIGHT/2);
        border3 = Main.world.createBody(bDef);
        border3.createFixture(pShape,1.0f);
        //Right
        bDef.position.set(Main.WORLDSIZE_WIDTH/2,0);
        border4 = Main.world.createBody(bDef);
        border4.createFixture(pShape,1.0f);

        pShape.dispose();
        Gdx.app.log("App","Finished setting up Battleground borders");
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

        //Main.debugRenderer.render(Main.world,cam.projection);
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
