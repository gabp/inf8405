package com.tp1.game;

public class ScreenManager
{
	private static ScreenManager _instance;
	LoadingScreen _loadingScreen;
	GameOverScreen _gameOverScreen;
	GameScreen _gameScreen;
	
	private ScreenManager()
	{
		_loadingScreen = new LoadingScreen(Bejewello.getGame());
		_gameOverScreen = new GameOverScreen(Bejewello.getGame());
		_gameScreen = new GameScreen(Bejewello.getGame());
	}
	
	//retourne l'instance
	static public ScreenManager getInstance()
	{
		_instance = (_instance == null) ? new ScreenManager() : _instance;
		return _instance;
	}
	
	//retourne les différentes vues
	public LoadingScreen getLoadingScreen()
	{
		return _loadingScreen;
	}
	
	public GameOverScreen getGameOverScreen()
	{
		return _gameOverScreen;
	}
	
	public GameScreen getGameScreen()
	{
		return _gameScreen;
	}
	
}
