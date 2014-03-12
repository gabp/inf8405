package com.tp1.game;

import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidGame;

public class Bejewello extends AndroidGame
{
	private static Bejewello _instance;
	
	@Override
	public Screen getInitScreen() 
	{
		if(_instance == null)
		{
			_instance = this;
			return ScreenManager.getInstance().getLoadingScreen();
		}
		else
		{
			ScreenManager.getInstance().getGameScreen()._grid = new Grid();
			_instance.setScreen(ScreenManager.getInstance().getGameScreen());
			return ScreenManager.getInstance().getGameScreen();
		}
		/*
		if(LoadingScreen._instance == null)
			return new LoadingScreen(_instance);
		else
		{
			GameScreen._instance._grid = new Grid();
			return GameScreen._instance;
		}*/
	}
	
	@Override
	public void onBackPressed() 
	{
		getCurrentScreen().backButton();
	}
	
	public static AndroidGame getGame()
	{
		return _instance;
	}
	
}
