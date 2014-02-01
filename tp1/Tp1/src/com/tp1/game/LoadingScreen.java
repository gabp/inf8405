package com.tp1.game;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Screen;
import com.tp1.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen
{

	public LoadingScreen(Game game) 
	{
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		
		//load images
        Assets.menu = g.newImage("mexican.jpg", ImageFormat.RGB565);
        Assets.menuButton = g.newImage("black-button.png", ImageFormat.RGB565);
        Assets.gemRuby = g.newImage("ruby.png", ImageFormat.RGB565);
        Assets.gemTopaz = g.newImage("topaz.png", ImageFormat.RGB565);
        Assets.gemZircon = g.newImage("zircon.png", ImageFormat.RGB565);
        
        //load sounds
        Assets.click = game.getAudio().createSound("Collision8-Bit.ogg");	
        
        game.setScreen(new MainMenuScreen(game));
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
