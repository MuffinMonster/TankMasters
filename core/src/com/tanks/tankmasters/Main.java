package com.tanks.tankmasters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

enum GameState{
	MENU,PLAYING,GAME_OVER
}

public class Main extends Game {

	InputHandler iH;
	GameLogic gL;
	Thread gT;

	static World world;
	static final int WORLDSIZE_WIDTH = 96, WORLDSIZE_HEIGHT = 54;//36 + 18 = 54
	static Box2DDebugRenderer debugRenderer;

	static SpriteBatch batch;
	static BitmapFont font;
	static BitmapFont.TextBounds bounds;

	Menu menu;

	Battleground battleground;

	GameState gameState;

	@Override
	public void create () {
		//Box2D
		world = new World(new Vector2(0,0), false); // Gravity(Vector2), AllowSleep
		debugRenderer = new Box2DDebugRenderer();//true,true,true,true,true,true
		//Box2D end

		batch = new SpriteBatch();
		font = new BitmapFont();

		menu = new Menu(this);
		battleground = new Battleground(this);
		iH = new InputHandler(this);

		gameState = GameState.MENU;

		gL = new GameLogic(this,battleground);
		gT = new Thread(gL);

		setScreen(menu);
	}

	public void setGameState(GameState s){
		gameState = s;
		switch(s){
			case PLAYING:
				gL.start();
				gT.start();
				break;
			case GAME_OVER:
				stopGameL();
				break;
			case MENU:
				stopGameL();
				break;
		}
	}

	public void disposeWorld(){
		Array<Body> bList = new Array<Body>();
		world.getBodies(bList);
		for(Body b: bList){
			world.destroyBody(b);
		}
		world.dispose();
		Gdx.app.log("App","Cleaned box2d World");
	}

	// Application closing/disposing
	public void close(){
		Main.batch.dispose();
		Main.font.dispose();
		setScreen(null); //Prevent rendering while disposing environment
		battleground.dispose();
		stopGameL();
		disposeWorld();
		this.dispose();
		Gdx.app.exit();
	}

	// Game logic thread shutdown
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
