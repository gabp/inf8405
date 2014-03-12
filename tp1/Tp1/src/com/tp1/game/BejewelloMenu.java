package com.tp1.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class BejewelloMenu extends Activity
{	
	public enum Mode {CHRONO, LIMITE, MODE_NUMBER}
	static Mode _mode;
	String _playerName;
	
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
    
    public void showDialog()
    {
    	// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(this);

		View promptView = layoutInflater.inflate(R.layout.dialog_name, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set prompts.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);

		final EditText input = (EditText) promptView.findViewById(R.id.NameInput);

		// setup a dialog window
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// get user input and set it to result
								_playerName = input.getText().toString();
								if (_playerName.equals(""))
									_playerName = "Player";
								Intent intent = new Intent(BejewelloMenu.this, Bejewello.class);
						        intent.putExtra("PLAYER", _playerName);
								startActivity(intent);
						        
						        finish();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();
    }
    public String getName()
	{
    	return (_playerName);
	}
    
    public void chronoButtonPressed(View v)
	{
    	_mode = Mode.CHRONO;
    	showDialog();    	
	}
    
    public void limiteButtonPressed(View v)
	{
    	_mode = Mode.LIMITE;
    	showDialog();    	
	}
    
    public void scoresButtonPressed(View v)
	{
    	Intent intent = new Intent(BejewelloMenu.this, Scores.class);
        startActivity(intent);
        finish();
	}
    
    public static Mode getMode()
    {
    	return (_mode);
    }
    
    public void quit(View v)
    {
    	showConfirmDialog();
    }
    
    public void showConfirmDialog()
    {
    	// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set prompts.xml to be the layout file of the alertdialog builder

		// setup a dialog window
		alertDialogBuilder
				.setTitle("Are you sure you want to leave the application?")
				.setPositiveButton("Yes, Leave!", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
						    	finish();
						    	System.exit(0);
							}
						})
				.setNegativeButton("No, Stay!",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								dialog.cancel();
							}
						});
		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
    }
}
