package com.tp1.game;

import android.graphics.Color;
import android.graphics.Paint;

import com.tp1.framework.Game;
import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidGame;

public class GameOverScreen extends Screen{

	public GameOverScreen(Game game) 
	{
		super(game);
	}

	@Override
	public void update(float deltaTime) 
	{
		
	}

	@Override
	public void paint(float deltaTime) 
	{
		game.getGraphics().clearScreen(0);
		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setTextSize(100);
		game.getGraphics().drawString("GAME OVER", 75, 100, p);
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
		((AndroidGame)game).goToMenu();
	}

}
