package com.tp1.game;

import com.tp1.framework.Game;
import com.tp1.framework.Input.TouchEvent;

public class Grid
{
	final int numberOfColumns = 8, numberOfLines = 8;
	Gem[][] gems;
	int _x = 75, _y = 400;
	Game _game;
	int _numberOfSelectedGems = 0;
	Gem gem1 = null, gem2 = null;
	
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
				
				gems[i][j].initNeighbors();
				gems[i][j]._topNeighbor = inGrid(i,j-1) ? gems[i][j-1] : null;
				gems[i][j]._bottomNeighbor = inGrid(i,j+1) ? gems[i][j+1] : null;
				gems[i][j]._leftNeighbor = inGrid(i-1,j) ? gems[i-1][j] : null;
				gems[i][j]._rightNeighbor = inGrid(i+1,j) ? gems[i+1][j] : null;
			}
		}	
		
		//so that we don't have a line of 3 of the same type
		fixGemTypes();
	}
	
	public void fixGemTypes()
	{
		boolean fixed = false;
		
		while(!fixed)
		{
			fixed = true;
			for(Gem[] column : gems)
			{
				for(Gem g : column)
				{
					//vertical lines
					if(g.topNeighbor() != null && 
					   g.topNeighbor().topNeighbor() != null && 
					   g.topNeighbor()._gemType == g._gemType && 
					   g.topNeighbor().topNeighbor()._gemType == g._gemType)
					{
						g.topNeighbor().changeGemType();
						fixed = false;
					}
					if(	g.topNeighbor() != null && 
						g.bottomNeighbor() != null &&
						g.topNeighbor()._gemType == g._gemType && 
						g.bottomNeighbor()._gemType == g._gemType)
					{
						g.changeGemType();
						fixed = false;
					}
					if(	g.bottomNeighbor() != null && 
						g.bottomNeighbor().bottomNeighbor() != null &&
						g.bottomNeighbor()._gemType == g._gemType && 
						g.bottomNeighbor().bottomNeighbor()._gemType == g._gemType)
					{
						g.bottomNeighbor().changeGemType();
						fixed = false;
					}
					
					//horizontal lines
					if(g.leftNeighbor() != null && 
					   g.leftNeighbor().leftNeighbor() != null && 
					   g.leftNeighbor()._gemType == g._gemType && 
					   g.leftNeighbor().leftNeighbor()._gemType == g._gemType)
					{
						g.leftNeighbor().changeGemType();
						fixed = false;
					}
					if(g.leftNeighbor() != null && 
					   g.rightNeighbor() != null &&
					   g.leftNeighbor()._gemType == g._gemType && 
					   g.rightNeighbor()._gemType == g._gemType)
					{
						g.changeGemType();
						fixed = false;
					}
					if(g.rightNeighbor() != null && 
					   g.rightNeighbor().rightNeighbor() != null &&
					   g.rightNeighbor()._gemType == g._gemType && 
					   g.rightNeighbor().rightNeighbor()._gemType == g._gemType)
					{
						g.rightNeighbor().changeGemType();
						fixed = false;
					}
				}
			}
		}
	}

	public boolean inGrid(int i, int j)
	{
		if(i >= 0 && i < numberOfColumns && j >= 0 && j < numberOfLines)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void update(Game game, TouchEvent event)
	{		
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				g.update(game, event);
			}
		}	
		
		if(getNumberOfSelectedGems() == 2)
		{
			for(Gem[] column : gems)
			{
				for(Gem g : column)
				{					
					if(g.isSelected())
					{
						if(gem1 == null)
						{
							gem1 = g;
						}
						else if(gem2 == null)
						{
							gem2 = g;
						}
					}
				}
			}
			
			if(gem1.isNeighbor(gem2))
			{
				switchGems(gem1, gem2);	
				checkForLines();
				deselectAll(game);
				gem1 = null;
				gem2 = null;
				_numberOfSelectedGems = 0;
			}
			else
			{
				deselectAll(game);
				gem1 = null;
				gem2 = null;
				_numberOfSelectedGems = 0;
			}
		}
	}
	
	private void checkForLines()
	{
		Gem.GemType lastHorizontal = null, lastVertical = null;
		int lineLength = 1, columnLength = 1;
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				if(lastHorizontal == g._gemType)
				{
					lineLength++;
				}
				else
				{
					lineLength = 1;
				}
				lastHorizontal = g._gemType;
			}
		}	
		
		for(int j=0; j<numberOfLines; j++)
		{
			for(int i=0; i<numberOfColumns; i++)
			{
				if(lastVertical == gems[i][j]._gemType)
				{
					columnLength++;
				}
				else
				{
					columnLength = 1;
				}
				lastVertical = gems[i][j]._gemType;
			}
		}
		
		
	}

	public int getNumberOfSelectedGems()
	{
		int number = 0;
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				if(g.isSelected())
				{
					number++;
				}
			}
		}
		return number;
	}
	
	public void switchGems(Gem gem1, Gem gem2)
	{
		int x = gem1._x;
		int y = gem1._y;
		
		gem1._x = gem2._x;
		gem1._y = gem2._y;
		
		gem2._x = x;
		gem2._y = y;
	}

	public void deselectAll(Game game)
	{
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				g.deselect(game);
			}
		}	
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
