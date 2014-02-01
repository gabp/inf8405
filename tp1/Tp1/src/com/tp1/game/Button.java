package com.tp1.game;

import java.io.Console;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.print.PrintAttributes;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidGraphics;

public class Button 
{
	int _x, _y, _width, _height;
	Screen _nextScreen;
	String _buttonText;
	

	public Button(int x, int y, int width, int height, Screen nextScreen, String buttonText)
	{
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_nextScreen = nextScreen;
		_buttonText = buttonText;
	}
	
	public void paint(Game game)
	{
		Graphics g = game.getGraphics();
		((AndroidGraphics) g).drawScaledImage(Assets.menuButton, _x, _y, _width, _height, 0, 0, Assets.menuButton.getWidth(), Assets.menuButton.getHeight());
		
		Paint p = new Paint();
		p.setTextSize(50);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        
		g.drawString(_buttonText, _x + _width/2, _y + _height*2/3, p);
	}
	
	public void update(Game game, TouchEvent event)
	{
        if (inBounds(event, _x, _y, _width, _height)) 
        {
        	Assets.click.play(10);
        	game.setScreen(_nextScreen);            
        }
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) 
	{
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
	
}
