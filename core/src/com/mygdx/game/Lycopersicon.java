package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.IntroScreen;
import com.mygdx.game.Screens.LycopersiconScreen;

public class Lycopersicon extends Game {
	private IntroScreen tIntroScreen;
	private LycopersiconScreen tGameScreen;
	private Game game;


	@Override
	public void create () {

		setScreen(new LycopersiconScreen(this));
	}


	@Override
	public void render () {
/*
		if (getScreen().equals(tIntroScreen) && tIntroScreen.tElapsedTime > 2.5)
		{
			tIntroScreen.dispose();

			setScreen(tGameScreen);
		}
*/
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
