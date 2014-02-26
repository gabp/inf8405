package com.tp1.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BejewelloMenu extends Activity
{	
	public enum Mode {CHRONO, LIMITE, MODE_NUMBER}
	static Mode _mode;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.menu_principal);
        
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
    
    public void chronoButtonPressed(View v)
	{
    	_mode = Mode.CHRONO;
    	Intent intent = new Intent(this, Bejewello.class);
        startActivity(intent);
        finish();
	}
    
    public void limiteButtonPressed(View v)
	{
    	_mode = Mode.LIMITE;
    	Intent intent = new Intent(this, Bejewello.class);
        startActivity(intent);
        finish();
	}
    
    public void scoresButtonPressed(View v)
	{
    	Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
        finish();
	}
    
    public void quit(View v)
    {
    	finish();
    	System.exit(0);
    }
}
