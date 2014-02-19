package com.tp1.game;

import android.graphics.Color;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Image;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGraphics;

public class Gem
{
	int _width, _height, _x, _y;
	public enum GemType {HOMER, BART, DUFF, DONUT, MAGGIE, MARGE, GRANDPA, GEM_NUMBER}
	GemType _gemType;
	boolean _selected;
	Gem _topNeighbor, _bottomNeighbor, _leftNeighbor, _rightNeighbor;
	Game _game;
	
	public Gem()
	{
		_game = Bejewello.getGame();
		_gemType = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		_width = 80;
		_height = 100;
		_selected = false;
	}
	
	public void initNeighbors()
	{
		_topNeighbor = new Gem();
		_bottomNeighbor = new Gem();
		_leftNeighbor = new Gem();
		_rightNeighbor = new Gem();
	}
	
	public void changeGemType()
	{
		GemType g = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		while(g == _gemType)
		{
			g = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		}
		
		_gemType = g;
	}
	
	public void paint()
	{
		Graphics g = _game.getGraphics();
		
		Image img = null;
		
		if(_gemType == GemType.BART)
		{		
			img = Assets.bart;
		}
		else if(_gemType == GemType.DONUT)
		{		
			img = Assets.donut;
		}
		else if(_gemType == GemType.DUFF)
		{		
			img = Assets.duff;
		}
		else if(_gemType == GemType.GRANDPA)
		{		
			img = Assets.grandpa;
		}
		else if(_gemType == GemType.HOMER)
		{		
			img = Assets.homer;
		}
		else if(_gemType == GemType.MAGGIE)
		{		
			img = Assets.maggie;
		}
		else if(_gemType == GemType.MARGE)
		{		
			img = Assets.marge;
		}
		
		((AndroidGraphics) g).drawScaledImage(img, _x, _y, _width, _height, 0, 0, img.getWidth(), img.getHeight());
	}
	
	public void update(TouchEvent event)
	{
		if(inBounds(event, _x, _y, _width, _height))
		{
			if(!isSelected())
			{
				select();	
				drawNeighbors(Color.YELLOW);
			}
			else
			{
				deselect();
				drawNeighbors(Color.BLACK);
			}
		}
	}
	
	public boolean isEqual(Gem g)
	{
		if(g == null)
		{
			return false;
		}
		if (_x == g._x && _y == g._y)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void changed()
	{
		Grid.getInstance().gemChanged(this);
	}
	
	public void assignPosition(int gridX, int gridY, int x, int y)
	{
		_x = gridX + x * _width;
		_y = gridY + y * _height;
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width,
            int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }
	
	public void select()
	{
		_game.getGraphics().drawRect(_x, _y, _width, _height, Color.GREEN);
		
		_selected = true;
	}
	
	public void deselect()
	{
		_game.getGraphics().drawRect(_x, _y, _width, _height, Color.BLACK);
		_selected = false;
	}
	
	public boolean isSelected()
	{
		return _selected;
	}
	
	public void updateNeighbors()
	{
		_topNeighbor = null;
		_bottomNeighbor = null;
		_rightNeighbor = null;
		_leftNeighbor = null;
		
		for (Gem[] columns : Grid.getInstance().gems)
		{
			for (Gem g : columns)
			{
				if(this.isNeighbor(g))
				{
					if(this._x - g._x == 0)
					{
						if(this._y - g._y > 0)
						{
							_topNeighbor = g;
						}
						else if(this._y - g._y < 0)
						{
							_bottomNeighbor = g;
						}
					}
					else
					{
						if(this._x - g._x > 0)
						{
							_leftNeighbor = g;
						}
						else if(this._x - g._x < 0)
						{
							_rightNeighbor = g;
						}
					}
				}
			}
		}
	}
	
	public void drawNeighbors(int color)
	{
		_game.getGraphics().drawRect(topNeighbor()._x, topNeighbor()._y, _width, _height, color);
		_game.getGraphics().drawRect(bottomNeighbor()._x, bottomNeighbor()._y, _width, _height, color);
		_game.getGraphics().drawRect(rightNeighbor()._x, rightNeighbor()._y, _width, _height, color);
		_game.getGraphics().drawRect(leftNeighbor()._x, leftNeighbor()._y, _width, _height, color);
	}
	
	public boolean isNeighbor(Gem g)
	{
		if(	Math.abs(g._y - _y) <= _height && 
			Math.abs(g._x - _x) <= _width && 
			(g._y == _y || g._x == _x))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public Gem topNeighbor()
	{
		return _topNeighbor;
	}
	
	public Gem bottomNeighbor()
	{
		return _bottomNeighbor;
	}
	
	public Gem leftNeighbor()
	{
		return _leftNeighbor;
	}
	
	public Gem rightNeighbor()
	{
		return _rightNeighbor;
	}
}
