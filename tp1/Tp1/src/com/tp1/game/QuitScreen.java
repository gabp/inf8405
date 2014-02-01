package com.tp1.game;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Screen;
import com.tp1.framework.Graphics.ImageFormat;

public class QuitScreen extends Screen{

	public QuitScreen(Game game) 
	{
		super(game);
	}

	@Override
	public void update(float deltaTime) 
	{
		System.exit(0);
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
