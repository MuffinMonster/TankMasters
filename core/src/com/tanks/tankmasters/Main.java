package com.tanks.tankmasters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

enum GameState{
	MENU,PLAYING,GAME_OVER
}

public class Main extends Game {

	InputHandler iH;
	GameLogic gL;
	Thread gT;

	static SpriteBatch batch;
	static BitmapFont font;
	static BitmapFont.TextBounds bounds;

	Menu menu;

	Battleground battleground;

	GameState gameState;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		gL = new GameLogic(this);
		gT = new Thread(gL);

		menu = new Menu(this);
		battleground = new Battleground(this);
		iH = new InputHandler(this);

		gameState = GameState.MENU;

		setScreen(menu);
	}

	public void setGameState(GameState s){
		gameState = s;
		switch(s){
			case PLAYING:
				gL.start();
				//gT.start();
				break;
			case GAME_OVER:
				stopGameL();
				break;
			case MENU:
				stopGameL();
				break;
		}
	}

	public void close(){
		stopGameL();
		this.dispose();
		Gdx.app.exit();
	}

	public void stopGameL(){
		if(GameLogic.running)
		{
			gL.stop();
			try {
				gT.join();
				Gdx.app.log("App","Successfully closed GameLogic thread!");
			} catch (InterruptedException e) {
				e.printStackTrace();
				Gdx.app.log("ERROR","GameLogic thread didn't close successfully!");
			}
		}
	}

	@Override
	public void render () {
		super.render();
	}


}
