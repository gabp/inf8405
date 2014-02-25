package com.tp1.game;

public class ScreenManager
{
	private static ScreenManager _instance;
	LoadingScreen _loadingScreen;
	GameOverScreen _gameOverScreen;
	GameScreen _gameScreen;
	ScoreScreen _scoreScreen;
	
	private ScreenManager()
	{
		_loadingScreen = new LoadingScreen(Bejewello.getGame());
		_gameOverScreen = new GameOverScreen(Bejewello.getGame());
		_gameScreen = new GameScreen(Bejewello.getGame());
		_scoreScreen = new ScoreScreen(Bejewello.getGame());
	}
	
	static public ScreenManager getInstance()
	{
		_instance = (_instance == null) ? new ScreenManager() : _instance;
		return _instance;
	}
	
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
	
	public ScoreScreen getScoreScreen()
	{
		return _scoreScreen;
	}
}
