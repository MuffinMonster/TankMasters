package com.tanks.tankmasters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {

    int x = 0, y = 0,size = 20;

    private ShapeRenderer renderer;

    public Player(int posX, int posY){
        x = posX;
        y = posY;
        renderer = new ShapeRenderer();
    }

    public void render(float delta){

        Main.batch.begin();
        Main.batch.setColor(0, 1, 0, 1);

        Main.batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(x,y,size/2);
        renderer.end();
    }

    public void dispose(){
        renderer.dispose();
    }

}
