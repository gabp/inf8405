package com.tp1.game;

import com.tp1.framework.implementation.AndroidGame;
import com.tp1.game.BejewelloMenu.Mode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Scores extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.menu_scores);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
    }
	
	@Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
    public void homeButtonPressed(View v)
	{
    	Intent intent = new Intent(this, BejewelloMenu.class);
        startActivity(intent);
        
        setContentView(R.layout.menu_scores);
        finish();
	}
    
}
