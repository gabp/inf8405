package com.tp1.game;

import com.tp1.framework.Game;
import com.tp1.framework.Input.TouchEvent;

public class Grid
{
	final int numberOfColumns = 8, numberOfLines = 8;
	Gem[][] gems;
	int _x = 300, _y = 300;
	
	public Grid()
	{
		gems = new Gem[numberOfColumns][numberOfLines];
		for(int i=0; i<numberOfColumns; i++)
		{
			for(int j=0; j<numberOfLines; j++)
			{
				gems[i][j] = new Gem();
			}
		}
		for(int i=0; i<numberOfColumns; i++)
		{
			for(int j=0; j<numberOfLines; j++)
			{
				gems[i][j].assignPosition(_x, _y, i, j);
			}
		}	
	}
	
	public void update(Game game, TouchEvent event)
	{
		
	}
	
	public void paint(Game game)
	{
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				g.paint(game);
			}
		}	
	}
}
