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
	        Assets.bart = g.newImage("bart.png", ImageFormat.RGB565);
	        Assets.homer = g.newImage("homer.png", ImageFormat.RGB565);
	        Assets.grandpa = g.newImage("grandpa.png", ImageFormat.RGB565);
	        Assets.maggie = g.newImage("maggie.png", ImageFormat.RGB565);
	        Assets.marge = g.newImage("marges-father.png", ImageFormat.RGB565);
	        Assets.duff = g.newImage("duff.png", ImageFormat.RGB565);
	        Assets.donut = g.newImage("donut.png", ImageFormat.RGB565);
	        
	        //load sounds
	        Assets.click = game.getAudio().createSound("Collision8-Bit.ogg");	
		}
        
        game.setScreen(ScreenManager.getInstance().getGameScreen());
	}

	@Override
	public void paint(float deltaTime) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() 
	{
		// TODO Auto-generated method stub
		
	}

}
