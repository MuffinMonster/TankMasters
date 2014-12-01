package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;

public class GameLogic implements Runnable {

    Main main;

    static boolean running = true;

    public GameLogic(Main m){
        main = m;

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

            Gdx.app.log("App","Loop");

            try {
                Thread.sleep((long) 0.15);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Gdx.app.log("WARNING","GameLogic thread was interrupted!");
            }
        }

    }
}
