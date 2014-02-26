package com.tp1.game;

import java.util.List;

import android.graphics.Paint;

import com.tp1.framework.Game;
import com.tp1.framework.Screen;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGame;
import com.tp1.framework.implementation.AndroidGraphics;
import com.tp1.game.BejewelloMenu.Mode;

public class GameScreen extends Screen 
{
    Grid _grid;
    Paint paint;
    
    public GameScreen(Game game) 
    {
    	
        super(game);
        //_grid = Grid.getInstance();
        _grid = new Grid();
    }

    @Override
    public void update(float deltaTime) 
    {
    	Grid.getInstance().updateUI();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) 
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) 
            {
            	_grid.update(event);
            }
        }
    }

    @Override
    public void paint(float deltaTime) 
    {
        //Graphics g = game.getGraphics();
        _grid.paint();
    }

    @Override
    public void pause() 
    {

    }

    @Override
    public void resume() 
    {
    	System.out.print(true);
    }

    @Override
    public void dispose() 
    {

    }

	@Override
	public void backButton()
	{
		if(BejewelloMenu._mode == Mode.CHRONO)
			_grid._timer.cancel();
		((AndroidGame)game).goToMenu();
		
	}
}
