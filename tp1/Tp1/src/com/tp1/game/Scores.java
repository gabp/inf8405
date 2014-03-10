package com.tp1.game;

import com.tp1.framework.implementation.AndroidGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Scores extends Activity
{
    public static TextView position1, position2, position3, position4, position5;
    public static TextView player1, player2, player3, player4, player5;
    public static TextView score1, score2, score3, score4, score5;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.menu_scores);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
    	//Text views pour les top5 player et scores
    	player1 = (TextView) findViewById(R.id.player1);
    	player2 = (TextView) findViewById(R.id.player2);
    	player3 = (TextView) findViewById(R.id.player3);
    	player4 = (TextView) findViewById(R.id.player4);
    	player5 = (TextView) findViewById(R.id.player5);
    	
    	score1 = (TextView) findViewById(R.id.score1);
    	score2 = (TextView) findViewById(R.id.score2);
    	score3 = (TextView) findViewById(R.id.score3);
    	score4 = (TextView) findViewById(R.id.score4);
    	score5 = (TextView) findViewById(R.id.score5);
    	
    	//prise des score du shared
        loadScores();

    }
	
	@Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
	public void onBackPressed()
	{
    	Intent intent = new Intent(this, BejewelloMenu.class);
        startActivity(intent);
        finish();
	}
    
    public void homeButtonPressed(View v)
	{
    	Intent intent = new Intent(this, BejewelloMenu.class);
        startActivity(intent);
        
        setContentView(R.layout.menu_scores);
        finish();
	}
    
	
	public void saveScore()
	{
		String name = Bejewello.getGame().getCurrentPlayer();
		int score = Grid.getInstance()._score;
		
		Context contextGame = AndroidGame.getAppContext();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contextGame);

		Editor editor = sp.edit();
		editor.putString("Player", name);
		editor.putInt("score", score);
		editor.commit(); 
	}
	
	public void loadScores()
	{
		Context contextScore = getApplicationContext();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contextScore);
		for (int i = 1;i<=5;i++)
		{
			String user = sp.getString("Player" +i, "default");
			int userScore = sp.getInt("Score" +i, 0);
			setTopScores(i,user,userScore);
		}

	}
	
	//Fonction qui mettre le score dans l'interface à partir du position/nom/score
    public void setTopScores(int position, String playerName, int bestScore) {
    	final int pos = position;
    	final String player = playerName;
    	final int score = bestScore;
    	
    	Scores.this.runOnUiThread(new Runnable(){

            @Override
            public void run() 
            {
            	
	            switch (pos)	            
	            {
	            	case 1:
	            		player1.setText(player);
	            		score1.setText(Integer.toString(score));
	            		break;
	            	case 2:
	            		player2.setText(player);
	            		score2.setText(Integer.toString(score));
	            		break;
	            	case 3:
	            		player3.setText(player);
	            		score3.setText(Integer.toString(score));
	            		break;
	            	case 4:
	            		player4.setText(player);
	            		score4.setText(Integer.toString(score));
	            		break;
	            	case 5:
	            		player5.setText(player);
	            		score5.setText(Integer.toString(score));
	            		break;
	            	default:
	            		break;
	            }
	            
	 
            }               
    	});
    }
    
}
