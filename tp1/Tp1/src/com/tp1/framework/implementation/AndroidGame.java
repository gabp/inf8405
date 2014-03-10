package com.tp1.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tp1.framework.Audio;
import com.tp1.framework.FileIO;
import com.tp1.framework.Game;
import com.tp1.framework.Graphics;
import com.tp1.framework.Input;
import com.tp1.framework.Screen;
import com.tp1.game.Bejewello;
import com.tp1.game.BejewelloMenu;
import com.tp1.game.Grid;
import com.tp1.game.R;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    Button b;
    
    String _currentPlayer = "Player";
    private static Context context;
    public static int width, height;
    public static Bitmap frameBuffer;
    public static SurfaceView surface;
    public static SurfaceHolder surfaceHolder;
    public static TextView scoreText;
    public static TextView remainingText;
    public static TextView movesText;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	AndroidGame.context = getApplicationContext();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        final int frameBufferWidth = isPortrait ? 800: 1280;
        final int frameBufferHeight = isPortrait ? 1280: 800;
        frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        Point p = new Point();      
        getWindowManager().getDefaultDisplay().getSize(p);  //this requires API lvl 13 minimum
        //width = p.x;
        //height = p.y;
        setContentView(R.layout.game);
        surface = (SurfaceView) findViewById(R.id.gameView);
        surface.setZOrderOnTop(true);	//transparence?
        surfaceHolder = surface.getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surface.setBackgroundColor(0xcc3cc);
        
        surface.addOnLayoutChangeListener(new OnLayoutChangeListener()
		{
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
			{
				width = surface.getWidth();
		        height = surface.getHeight();
		        float scaleX = (float) frameBufferWidth / width;
		        float scaleY = (float) frameBufferHeight / height;
				input = (AndroidInput._instance == null) ? new AndroidInput(AndroidGame.this, surface, scaleX, scaleY) : AndroidInput._instance;
				surface.removeOnLayoutChangeListener(this);
			}
		});
        
        boolean firstTime = (AndroidFastRenderView._instance == null) ? true : false;
        renderView = (AndroidFastRenderView._instance == null) ? new AndroidFastRenderView(AndroidGame.this, frameBuffer) : AndroidFastRenderView._instance;
        graphics = (AndroidGraphics._instance == null) ? new AndroidGraphics(getAssets(), frameBuffer) : AndroidGraphics._instance;
        fileIO = (AndroidFileIO._instance == null) ? new AndroidFileIO(AndroidGame.this) : AndroidFileIO._instance;
        audio = (AndroidAudio._instance == null) ? new AndroidAudio(AndroidGame.this) : AndroidAudio._instance;
        
        screen = getInitScreen();
        if(!firstTime)
        	((ViewGroup)renderView.getParent()).removeView(renderView);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        
    	//Text views pour le score/etc HUD top
    	scoreText = (TextView) findViewById(R.id.Score);
    	remainingText = (TextView) findViewById(R.id.Remaining);
    	movesText = (TextView) findViewById(R.id.Moves);
    	
    	//Prendre le nom entré dans le menu avant la partie
    	Bundle extras = getIntent().getExtras();
    	_currentPlayer = extras.getString("PLAYER");
    }
	
	public static Context getAppContext() {
        return AndroidGame.context;
    }
	
	
	public void saveScore()
	{
		String name = Bejewello.getGame().getCurrentPlayer();
		int score = Grid.getInstance().getScore();
		int posTop5 = 0;
		
		//On envoi le score pour voir s'il fait partie du top 5
		posTop5 = checkHighScore(score, name);
		
		//Si oui, on le stock les infos aux valeurs Player# , où # est la position top5
		if (posTop5 != 0)
		{
			putTop5(posTop5, name, score);
		}

	}
	
	//Nous retourne la position dans la liste de high score, s'il est dans le top 5
	public int checkHighScore(int score, String name)
	{
		int tempPos = 0;
		String tempName = "";
		int tempScore = 0;
		
		Context contextGame = AndroidGame.getAppContext();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contextGame);
		
		//On check toute les valeurs présentes dans le save
		for (int i=1;i<=6;i++)
		{
			tempName = sp.getString("Player" + i, "-");
			tempScore = sp.getInt("Score" + i, 0);
			
			if (score >= tempScore)	//si son score est plus grand que celui dans la liste
			{
				return (i);	//positions de 1 à 6, ce qui sera retourné
			}
			//si il a le meme score que son ancien score, ou simplement deja présent plus haute dans la liste
			else if (tempName.equals(name))
			{
				//jouer déja présent plus haut dans la liste (tempscore > score)
				return (0);
			}
		}
		return (0);
	}
	
	//Fonction qui enleve lautre score du même nom de joueur et décale les anciens selon la position
	public void putTop5(int posTop5, String name, int score)
	{
		// ex: si la position est 3, 1 et 2 reste semblable
		// 3 est holdé, remplacé par le nouveau
		// 4 est holdé, remplacé par 3
		// etc.
		int tempPos;	
		String tempName = name;
		int tempScore = score;
		String tempNameAft = "";
		int tempScoreAft = 0;
		boolean descendant = true; //indique si on doit descendre les autres (true) ou monter dans le cas dun meme nom (false)
		
		Context contextGame = AndroidGame.getAppContext();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contextGame);
		Editor editor = sp.edit();
		/////////////////////////
		
		//On check toute les valeurs présentes dans le save, decale au besoin
		for (tempPos = posTop5; tempPos <= 6; tempPos++)	//on commence à sa position, car le reste est bon.
		{
			tempNameAft = sp.getString("Player" + tempPos, "-");	//ceux à descendre
			tempScoreAft = sp.getInt("Score" + tempPos, 0);
			
			if (tempNameAft.equals(name))	// Si c'est le même joueur deja présent
			{
				if (tempPos == posTop5)		//même position que son ancienne
				{
					//On met simplement son nouveau score ici et on sort
					editor.putInt("Score" + tempPos, score);	//on met le score avec l'indicateur Score
					editor.commit();
					return;
				}
				else	// pas la meme position, donc son nom s'est retrouvé plus bas
				{
					//les derniers players montent
					editor.putString("Player" + (tempPos), tempName);	//on met le nom avec l'indicateur Player
					editor.putInt("Score" + (tempPos), tempScore);	//on met le score avec l'indicateur Score
					editor.commit();
					//descendant = false;	// on monte le reste
					//tempPos++;
					//tempNameAft = sp.getString("Player" + tempPos, "-");
					//tempScoreAft = sp.getInt("Score" + tempPos, 0);
					return;
					
				}
			}
			
			if (descendant)	//swap les players suivant
			{
				editor.putString("Player" + tempPos, tempName);	//on met le nom avec l'indicateur Player
				editor.putInt("Score" + tempPos, tempScore);	//on met le score avec l'indicateur Score
				editor.commit();
				tempName = tempNameAft;
				tempScore = tempScoreAft;
			}
			/*if (!descendant)
			{
				editor.putString("Player" + (tempPos -1), tempNameAft);	//on met le nom avec l'indicateur Player
				editor.putInt("Score" + (tempPos -1), tempScoreAft);	//on met le score avec l'indicateur Score
				editor.commit();
			}*/
			
		}
	}
	

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {

        return screen;
    }
    
    public void setScore(int currentScoreParam) {
    	final int currentScore = currentScoreParam;
    	
    	AndroidGame.this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
            	
            	scoreText.setText("Score: " + currentScore);
            }               
    	});
    }
    
    public void setMoves(int movesParam) {
    	final int moves = movesParam;
    	
    	AndroidGame.this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
            	
            	movesText.setText("Moves Done: " + moves);
            }               
    	});
    }
    
    public void setRemaining(int currentRemainingParam, String modeParam) {
    	final int currentRemaining = currentRemainingParam;
    	final String mode = modeParam;
    	
    	AndroidGame.this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
            	if (mode == "chrono")
            		remainingText.setText("Time left: " + currentRemaining);
            	else if (mode == "moves")
            		remainingText.setText("Moves left: " + currentRemaining);
            }               
    	});
    }
    
    public void setCurrentPlayer(String playerName)
    {
    	_currentPlayer = playerName;
    }
    
    public String getCurrentPlayer()
    {
    	return (_currentPlayer);
    }
    
    public void goToMenu()
    {
    	//renderView.pause();
    	Intent intent = new Intent(this, BejewelloMenu.class);
        startActivity(intent);
        finish();
    }
    public void homeButtonPressed(View v)
    {
    	//dialog icit. choix: continuer, retour au menu, recommencer (si !fini)
    	this.goToMenu();
    }
    
}
