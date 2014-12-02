package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;

public class GameLogic implements Runnable {

    Main main;
    Battleground battleground;
    Player player;
    static boolean running = true;

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
        Gdx.app.log("App","Started GameLogic thread!");
        while(running){

            //Quicknote: More iterations: Smoother simulation and heavier performance hit, less iterations is other way around
            Main.world.step(1/300f,6, 2); // timestep, velocityIterations, positionIterations

            player.update();

            try {
                Thread.sleep(15L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Gdx.app.log("WARNING","GameLogic thread was interrupted!");
            }
        }

    }
}
