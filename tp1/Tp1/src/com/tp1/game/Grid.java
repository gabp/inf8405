package com.tp1.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;

import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGame;

public class Grid
{
	final int numberOfColumns = 8, numberOfLines = 8;
	Gem[][] gems;
	int _x = 75, _y = 400;
	AndroidGame _game;
	int _numberOfSelectedGems = 0;
	int _score = 0;
	Gem gem1 = null, gem2 = null;
	private static Grid _instance;
	boolean _animSwitch = false;
	
	private Grid()
	{
		_instance = this;
		_game = Bejewello.getGame();
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
	
	public static Grid getInstance()
	{
		return (_instance == null) ? new Grid() : _instance;
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
	
	public void updateScore()
	{
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(50);
		_game.getGraphics().drawRect(0, 0, 350, 100, Color.BLACK);
		_game.getGraphics().drawString("Score: " + _score, 50, 50, p);
	}
	
	public void gemChanged(Gem g)
	{
		Gem temp = g;
		List<Gem> gemListH = new ArrayList<Gem>();
		List<Gem> gemListV = new ArrayList<Gem>();
		gemListH.add(g);
		gemListV.add(g);
		
		//horizontal
		while (	temp != null && 
				temp.leftNeighbor() != null &&
				temp.leftNeighbor()._gemType == g._gemType)
		{
			temp = temp.leftNeighbor();
			gemListH.add(temp);
		}
		temp = g;
		while (	temp != null && 
				temp.rightNeighbor() != null &&
				temp.rightNeighbor()._gemType == g._gemType)
		{
			temp = temp.rightNeighbor();
			gemListH.add(temp);
		}	
		//vertical
		temp = g;
		while (	temp != null && 
				temp.topNeighbor() != null && 
				temp.topNeighbor()._gemType == g._gemType)
		{
			temp = temp.topNeighbor();
			gemListV.add(temp);
		}
		temp = g;
		while (	temp != null && 
				temp.bottomNeighbor() != null &&
				temp.bottomNeighbor()._gemType == g._gemType)
		{
			temp = temp.bottomNeighbor();
			gemListV.add(temp);
		}
		
		if(gemListH.size() >= 3)
		{
			_score += gemListH.size()*10;
			Assets.click.play(30);

			for(Gem g2 : gemListH)
			{
				g2.highlight();
				g2.disapear();
			}
		}
		
		if (gemListV.size() >= 3)
		{
			Grid.getInstance()._score += gemListV.size()*10;
			Assets.click.play(30);	
			
			for(Gem g2 : gemListV)
			{
				g2.highlight();
				g2.disapear();
			}
		}
		
		if (gemListH.size() >= 3 || gemListV.size() >= 3)
		{
			waitUntilGemsDisapear();
		}
	}
	
	public void waitUntilGemsDisapear()
	{
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	boolean ready = true;
		                for(Gem[] column : gems)
		                {
		                	for (Gem g : column)
		                	{
		                		if(g._disapearing)
		                		{
		                			ready = false;
		                		}
		                	}
		                }
		                if(ready)
		                {
		                	//moveLines();
		                }
		                else
		                {
		                	waitUntilGemsDisapear();
		                }
		            }
		        }, 
		        500 
		);
	}
	
	public void moveLines()
	{	
		List<Pair> lines = new ArrayList<Pair>();
		
		for (Gem[] column : gems)
		{
			for (Gem g : column)
			{				
				if(g.bottomNeighbor() != null && !g._disapeared && g.bottomNeighbor()._disapeared)
				{
					List<Gem> line = new ArrayList<Gem>();
					Gem temp = g;
					int numberOfShifts = 0;
					
					while(temp.bottomNeighbor() != null && temp.bottomNeighbor()._disapeared)
					{
						temp = temp.bottomNeighbor();
						numberOfShifts++;
					}
					
					line.add(g);
					while(temp.topNeighbor() != null)
					{
						temp = temp.topNeighbor();
						line.add(temp);
					}
					
					Pair<List<Gem>, Integer> pair = new Pair<List<Gem>, Integer>(line, numberOfShifts);
					lines.add(pair);
				}
			}
		}
		
		for(Pair<List<Gem>, Integer> line : lines)
		{
			moveLineDown(line);
		}
		
		//replacer les gems qui sont invisible et les faire reapparaitre
		
		
		/*
		if(g.topNeighbor() != null)
		{			
			List<Gem> list = new ArrayList<Gem>();
			Gem temp2 = g;
			while(temp2.topNeighbor() != null)
			{
				temp2 = temp2.topNeighbor();
				list.add(temp2);					
			}
			
			g.changeGemType();
			g._y = temp2._y;
			
			moveLineDown(list, 1);	
		}
		
		g._disapeared = false;*/
	}
	
	public void moveLineDown(Pair<List<Gem>, Integer> line)
	{
		if(line.first.size() > 0)
		{
			for (Gem g : line.first)
			{
				g.moveTo(g._x, g._y + line.second * g._height);
			}
		}
	}
	
	public void update(TouchEvent event)
	{				
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				g.update(event);
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
				deselectAll();
				gem1 = null;
				gem2 = null;
				_numberOfSelectedGems = 0;
			}
			else
			{
				deselectAll();
				gem1 = null;
				gem2 = null;
				_numberOfSelectedGems = 0;
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
		gem1.moveTo(gem2._x, gem2._y);
		gem2.moveTo(gem1._x, gem1._y);
	}

	public void deselectAll()
	{
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				g.deselect();
			}
		}	
	}

	public void paint()
	{
		for(Gem[] column : gems)
		{
			for(Gem g : column)
			{
				g.paint();
			}
		}	
	}
}
