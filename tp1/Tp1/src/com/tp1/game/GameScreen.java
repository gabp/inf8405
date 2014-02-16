package com.tp1.game;

import java.util.List;

import android.graphics.Paint;

import com.tp1.framework.Game;
import com.tp1.framework.Screen;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGame;

public class GameScreen extends Screen 
{
    Grid gemGrid;
    Paint paint;

    public GameScreen(Game game) 
    {
        super(game);
        
        gemGrid = new Grid();
    }

    @Override
    public void update(float deltaTime) 
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) 
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) 
            {
            	gemGrid.update(game, event);
            }
        }
    }

    @Override
    public void paint(float deltaTime) 
    {
        //Graphics g = game.getGraphics();
        
        gemGrid.paint(game);
    }

    @Override
    public void pause() 
    {

    }

    @Override
    public void resume() 
    {

    }

    @Override
    public void dispose() 
    {

    }

	@Override
	public void backButton()
	{
		((AndroidGame)game).goToMenu();
	}
}
