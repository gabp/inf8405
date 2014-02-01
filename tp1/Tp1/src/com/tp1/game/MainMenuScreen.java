package com.tp1.game;

import java.util.List;

import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Screen;
import com.tp1.framework.Input.TouchEvent;
import com.tp1.framework.implementation.AndroidGraphics;

public class MainMenuScreen extends Screen
{
	Button _chronoModeButton, _limitedModeButton, _scoresButton, _quitButton;
	
	public MainMenuScreen(Game game) {
		super(game);
		Graphics g = game.getGraphics();
		_chronoModeButton = new Button(200, 50, 350, 160, new GameScreen(game), "Mode chrono");
		_limitedModeButton = new Button(200, 200, 350, 160, new GameScreen(game), "Mode limite");
		_scoresButton = new Button(200, 350, 350, 160, new GameScreen(game), "Scores");
		_quitButton = new Button(200, 500, 350, 160, new QuitScreen(game), "Quitter");
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) 
            {
            	_chronoModeButton.update(game, event);
        		_limitedModeButton.update(game, event); 	
        		_scoresButton.update(game, event);
        		_quitButton.update(game, event);
            }
        }			
	}

	@Override
	public void paint(float deltaTime) 
	{
		game.getGraphics().clearScreen(0);
		_chronoModeButton.paint(game);
		_limitedModeButton.paint(game);
		_scoresButton.paint(game);
		_quitButton.paint(game);
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
