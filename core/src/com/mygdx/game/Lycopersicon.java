package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

import com.mygdx.game.Screens.LycopersiconScreen;

public class Lycopersicon extends Game {


	@Override
	public void create () {

		setScreen(new LycopersiconScreen(this));
	}


	@Override
	public void render () {
/*

*/
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
