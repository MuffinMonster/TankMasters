package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

    Main main;

    public InputHandler(Main m){
        main = m;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.ESCAPE){
            main.setScreen(null); //Prevent rendering while disposing environment
            Main.batch.dispose();
            Main.font.dispose();
            main.battleground.dispose();
            main.close();
            return true;
        }

        if(main.gameState == GameState.MENU){
            Gdx.app.log("App","Switching to battlegrounds");
            main.setGameState(GameState.PLAYING);
            main.setScreen(main.battleground);
        }

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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
