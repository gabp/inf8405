package com.tp1.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Screen;
import com.tp1.framework.Graphics.ImageFormat;
import com.tp1.framework.implementation.AndroidGame;
import com.tp1.framework.implementation.AndroidGraphics;

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
		Graphics g = game.getGraphics();
		g.clearScreen(Color.rgb(0x0c, 0xc3, 0xcc));
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(80);
		g.drawString("GAME OVER", 75, 150, p);
		Assets.win = g.newImage("homer_win.png", ImageFormat.RGB565);
		((AndroidGraphics) g).drawScaledImage(Assets.win, 50, 250, 500, 500, 0, 0, Assets.win.getWidth(), Assets.win.getHeight());
		
		g.drawString("Final score: "+ Grid.getInstance()._score, 75, 850, p);
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
