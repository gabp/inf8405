package com.tp1.game;

import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidGame;

public class Bejewello extends AndroidGame{

	@Override
	public Screen getInitScreen() {
		return new LoadingScreen(this);
	}
	
}
