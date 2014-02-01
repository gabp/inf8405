package com.tp1.game;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.implementation.AndroidGraphics;

public class Gem
{
	int _width, _height, _x, _y;
	public enum GemType {RUBY, TOPAZ, ZIRCON, GEM_NUMBER}
	GemType _gemType;
	
	public Gem()
	{
		_gemType = GemType.values()[(int)(Math.random() * (GemType.GEM_NUMBER.ordinal()))];
		_width = 50;
		_height = 50;
	}
	
	public void paint(Game game)
	{
		Graphics g = game.getGraphics();
		
		if(_gemType == GemType.RUBY)
		{		
			((AndroidGraphics) g).drawScaledImage(Assets.gemRuby, _x, _y, _width, _height, 0, 0, Assets.gemRuby.getWidth(), Assets.gemRuby.getHeight());
		}
		else if(_gemType == GemType.TOPAZ)
		{		
			((AndroidGraphics) g).drawScaledImage(Assets.gemTopaz, _x, _y, _width, _height, 0, 0, Assets.gemTopaz.getWidth(), Assets.gemTopaz.getHeight());
		}
		else if(_gemType == GemType.ZIRCON)
		{		
			((AndroidGraphics) g).drawScaledImage(Assets.gemZircon, _x, _y, _width, _height, 0, 0, Assets.gemZircon.getWidth(), Assets.gemZircon.getHeight());
		}
	}
	
	public void assignPosition(int gridX, int gridY, int x, int y)
	{
		_x = gridX + x * _width;
		_y = gridY + y * _height;
	}
}
