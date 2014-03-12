package com.tp1.game;

import android.graphics.Color;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Image;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGraphics;

public class Gem
{
	static int _width = 95, _height = 135;
	int _x, _y;
	public enum GemType {HOMER, BART, DUFF, DONUT, MAGGIE, MARGE, GRANDPA, GEM_NUMBER}
	GemType _gemType;
	boolean _selected;
	Gem _topNeighbor, _bottomNeighbor, _leftNeighbor, _rightNeighbor;
	Game _game;
	boolean _moving = false;
	int _targetX, _targetY, _movementSpeed = 10;
	boolean _highlight = false, _disappearing = false, _disappeared = false, _disappearedChanged = false;
	int _disappearingRate = 40, _disappearAlpha = 0;
	boolean _selectedChanged = false;
	boolean _firstTime = true;
	boolean _movingChanged = false;
	boolean _marked = false;
	
	//generate a new gem with a random type
	public Gem()
	{
		_game = Bejewello.getGame();
		_gemType = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		_selected = false;
	}
	
	public void initNeighbors()
	{
		_topNeighbor = new Gem();
		_bottomNeighbor = new Gem();
		_leftNeighbor = new Gem();
		_rightNeighbor = new Gem();
	}
	
	//make a gem disappear
	public void disappear()
	{
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                _disappearing = true;
		                this.cancel();
		            }
		        }, 
		        500 
		);
	}
	
	//make a gem reappear
	public void reappear()
	{
		_disappearAlpha = 0;
		_disappearedChanged = _disappeared;
		_disappeared = false;
	}
	
	//highlight a gem (when selectionned)
	public void highlight()
	{
		_highlight = true;
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                _highlight = false;
		                this.cancel();
		            }
		        }, 
		        500 
		);
	}
	
	//change the type of the gem
	public void changeGemType()
	{
		GemType g = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		while(g == _gemType)
		{
			g = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		}
		
		_gemType = g;
	}
	
	//main graphic loop
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
		
		
		//check if we need to update what is shown
		if(_selected || _highlight || moving() || _selectedChanged || _firstTime || _disappearedChanged || _disappearing || _disappeared || _movingChanged)
		{
			_firstTime = false;
			_disappearedChanged = false;
			_selectedChanged = false;
			_movingChanged = false;
			
			_game.getGraphics().drawRect(_x, _y, _width+1, _height+1, Color.rgb(0x0c, 0xc3, 0xcc));
			
			if(_selected)
			{
				_game.getGraphics().drawRect(_x, _y, _width, _height, Color.rgb(0xff, 0x7b, 0x09));
			}
			else
			{
				_game.getGraphics().drawRect(_x, _y, _width+1, _height+1, Color.rgb(0x0c, 0xc3, 0xcc));
			}
			
			if(_highlight)
			{
				_game.getGraphics().drawRect(_x, _y, _width, _height, Color.WHITE);
			}
			
			animMovement();		
			
			((AndroidGraphics) g).drawScaledImage(img, _x, _y, _width, _height, 0, 0, img.getWidth(), img.getHeight());		
		}
		
		//this makes the gem disappear
		if(_disappearing || _disappeared)
		{
			if(_disappearAlpha >= 255)
			{
				_disappearing = false;
				_disappearAlpha = 255;
			}
			_game.getGraphics().drawRect(_x, _y, _width, _height, Color.argb(_disappearAlpha, 0x0c, 0xc3, 0xcc));
			
			if(!_disappeared && !_disappearing)
			{
				_disappearedChanged = true;
				_disappeared = true;
			}
			
			_disappearAlpha += _disappearingRate;
		}
	}
	
	//annimates the movement when a gem moves
	public void animMovement()
	{
		if(_moving)
		{
			if(_x < _targetX)
				_x += _movementSpeed;
			else if (_x > _targetX)
				_x -= _movementSpeed;
			if(_y < _targetY)
				_y += _movementSpeed;
			else if(_y > _targetY)
				_y -= _movementSpeed;
			
			if(Math.abs(_x - _targetX) <= _movementSpeed && Math.abs(_y - _targetY) <= _movementSpeed)
			{
				_x = _targetX;
				_y = _targetY;
				_moving = false;
				_movingChanged = true;			
				this.updateNeighbors();
				this.updateNeighborsOfNeighbors();
			}
		}
	}
	
	//change gem position
	public void moveTo(final int x, final int y)
	{		
		this._targetX = x;
		this._targetY = y;
		_movingChanged = !_moving;
		this._moving = true;
	}
	
	public boolean moving()
	{
		return _moving;
	}
	
	//marque le gem pour qu'il soit identifier plus tard
	public void mark()
	{
		_marked = true;
	}
	
	//demarque le gem pour qu'il soit identifier plus tard
	public void unMark()
	{
		_marked = false;
	}
	
	//regarde si le gem est marque
	public boolean isMarked()
	{
		return _marked;
	}
	
	//mets a jour les voisins
	public void updateNeighborsOfNeighbors()
	{
		if(leftNeighbor() != null) {leftNeighbor().updateNeighbors();}
		if(rightNeighbor() != null) {rightNeighbor().updateNeighbors();}
		if(bottomNeighbor() != null) {bottomNeighbor().updateNeighbors();}
		if(topNeighbor() != null) {topNeighbor().updateNeighbors();}
	}
	
	//traite les input du joueur
	public void update(TouchEvent event)
	{
		if(inBounds(event, _x, _y, _width, _height))
		{
			if(!isSelected())
			{
				if(!moving())
					select();
			}
			else
			{
				deselect();
			}
		}
	}
	
	//verifie si le gem est egal a un autre
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
	
	//assigne une position au gem
	public void assignPosition(int gridX, int gridY, int x, int y)
	{
		_x = gridX + x * _width;
		_y = gridY + y * _height;
	}
	
	//regarde si l'event est a l'interieur de la grille
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
		_selectedChanged = !_selected;
		_selected = true;
	}
	
	public void deselect()
	{
		_selectedChanged = _selected;
		_selected = false;
	}
	
	public boolean isSelected()
	{
		return _selected;
	}
	
	//met a jour les voisins
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
	
	//dessine les voisins (pour de debug)
	public void drawNeighbors(int color)
	{
		_game.getGraphics().drawRect(topNeighbor()._x, topNeighbor()._y, _width, _height, color);
		_game.getGraphics().drawRect(bottomNeighbor()._x, bottomNeighbor()._y, _width, _height, color);
		_game.getGraphics().drawRect(rightNeighbor()._x, rightNeighbor()._y, _width, _height, color);
		_game.getGraphics().drawRect(leftNeighbor()._x, leftNeighbor()._y, _width, _height, color);
	}
	
	//retourne vrai si le gem est voisin a un autre
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

	//retourne le voisin specifie
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
