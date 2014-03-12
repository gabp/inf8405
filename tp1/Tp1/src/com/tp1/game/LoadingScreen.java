package com.tp1.game;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Screen;
import com.tp1.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen
{	
	static LoadingScreen _instance;
	boolean _loaded = false;
	
	public LoadingScreen(Game game) 
	{
		super(game);
		_instance = this;
	}

	@Override
	public void update(float deltaTime) 
	{
		if(!_loaded)
		{
			_loaded = true;
			Graphics g = game.getGraphics();
			
			//load images        
	        Assets.bart = g.newImage("beigne_green.png", ImageFormat.RGB565);
	        Assets.homer = g.newImage("beigne_brun.png", ImageFormat.RGB565);
	        Assets.grandpa = g.newImage("beigne_yellow.png", ImageFormat.RGB565);
	        Assets.maggie = g.newImage("beigne_rose.png", ImageFormat.RGB565);
	        Assets.marge = g.newImage("beigne_blue.png", ImageFormat.RGB565);
	        Assets.duff = g.newImage("beigne_noir.png", ImageFormat.RGB565);
	        Assets.donut = g.newImage("beigne_white.png", ImageFormat.RGB565);
	        
	        //load sounds
	        Assets.click = game.getAudio().createSound("Collision8-Bit.ogg");	
		}
        
        game.setScreen(ScreenManager.getInstance().getGameScreen());
	}

	@Override
	public void paint(float deltaTime) 
	{
		//
	}

	@Override
	public void pause() 
	{
		//
	}

	@Override
	public void resume() 
	{
		// 
	}

	@Override
	public void dispose() 
	{
		// 
	}

	@Override
	public void backButton() 
	{
		// 
	}

}
