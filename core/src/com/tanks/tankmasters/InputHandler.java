package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputHandler implements InputProcessor {

    Main main;

    Player p;

    public InputHandler(Main m){
        main = m;
        Gdx.input.setInputProcessor(this);
        p = main.battleground.getPlayer();
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.ESCAPE){
            Gdx.app.log("Input","App close issued!");
            main.close();
            return true;
        }

        if(main.gameState == GameState.MENU){
            Gdx.app.log("App","Switching to battlegrounds");
            main.setGameState(GameState.PLAYING);
            main.setScreen(main.battleground);
        }

        handlePlayerInput(keycode);

        return true;
    }

    private void handlePlayerInput(int keycode) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            p.turnLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            p.turnRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            p.moveForward();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            p.moveBackward();
        }

    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A || keycode == Input.Keys.D)
            p.resetTurn();
        if(keycode == Input.Keys.W || keycode == Input.Keys.S)
            p.resetMove();

        handlePlayerInput(-1);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        p.updateTurret((double)screenX / Gdx.graphics.getWidth(), (double)screenY / Gdx.graphics.getHeight());
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
