package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;

public class GameLogic implements Runnable {

    Main main;
    Battleground battleground;
    Player player;
    static boolean running = true;
    private float deltaTime = 0;
    private long  startTime = 0;

    public GameLogic(Main m,Battleground bg){
        main = m;
        battleground = bg;
        player = battleground.getPlayer();
    }

    public void start(){
        running = true;
    }

    public void stop(){
        running = false;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        Gdx.app.log("App","Started GameLogic thread!");
        while(running){

            //Quicknote: More iterations: Smoother simulation and heavier performance hit, less iterations is other way around
            Main.world.step(1/45f,6, 4); // timestep, velocityIterations, positionIterations

            player.update(deltaTime);

            try {
                long sleepTime = Math.max(15L + (long) Math.min((0.015f - deltaTime) * 1000f,0), 0);
                //Gdx.app.log("App","Sleeptime:"+sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Gdx.app.log("WARNING","GameLogic thread was interrupted!");
            }

            deltaTime = (float)(System.currentTimeMillis() - startTime)/1000f;
            startTime = System.currentTimeMillis();
            //Gdx.app.log("Delta",""+deltaTime+", Lps:"+(1f/deltaTime));
        }

    }
}
