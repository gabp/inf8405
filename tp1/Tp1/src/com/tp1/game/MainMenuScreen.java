package com.tp1.game;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Screen;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGraphics;

public class MainMenuScreen extends Screen{

	public MainMenuScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) 
            {
                if (inBounds(event, 0, 0, 250, 125)) 
                {
					//START GAME
                	Assets.click.play(10);
					game.setScreen(new GameScreen(game));               
                }
            }
        }		
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) 
	{
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

	@Override
	public void paint(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 250, 125, Color.GREEN);
		Paint p = new Paint();
		p.setTextSize(30);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setColor(Color.BLACK);
        
        ((AndroidGraphics) g).drawScaledImage(Assets.menu, 500, 100, 250, 700, 0, 0, 985, 1452);
        
		g.drawString("Play", 125, 62, p);
        //g.drawImage(Assets.menu, 0, 0);	
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
	}
	
}
