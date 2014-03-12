package com.tp1.game;

import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidGame;

public class Bejewello extends AndroidGame
{
	private static Bejewello _instance;
	
	//returns the init screen
	@Override
	public Screen getInitScreen() 
	{
		if(_instance == null)
		{
			_instance = this;
			
			//first time so we need to load the images
			return ScreenManager.getInstance().getLoadingScreen();
		}
		else
		{
			//dont load the images and go directly to the game screen
			ScreenManager.getInstance().getGameScreen()._grid = new Grid();
			_instance.setScreen(ScreenManager.getInstance().getGameScreen());
			return ScreenManager.getInstance().getGameScreen();
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		getCurrentScreen().backButton();
	}
	
	//singleton
	public static AndroidGame getGame()
	{
		return _instance;
	}
	
}
