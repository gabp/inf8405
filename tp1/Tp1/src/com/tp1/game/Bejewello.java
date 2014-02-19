package com.tp1.game;

import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidGame;

public class Bejewello extends AndroidGame
{
	private static Bejewello _instance;
	
	@Override
	public Screen getInitScreen() 
	{
		_instance = this;
		return new LoadingScreen(this);
	}
	
	@Override
	public void onBackPressed() 
	{
		getCurrentScreen().backButton();
	}
	
	public static AndroidGame getGame()
	{
		return (_instance == null) ? new Bejewello() : _instance;
	}
	
}
