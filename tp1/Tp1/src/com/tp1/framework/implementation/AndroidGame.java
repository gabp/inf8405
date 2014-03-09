package com.tp1.framework.implementation;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
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
import com.tp1.game.BejewelloMenu;
import com.tp1.game.R;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    Button b;
    public static int width, height;
    public static Bitmap frameBuffer;
    public static SurfaceView surface;
    public static SurfaceHolder surfaceHolder;
    public static TextView scoreText;
    public static TextView remainingText;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

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
    
    public void setRemaining(int currentRemainingParam, String modeParam) {
    	final int currentRemaining = currentRemainingParam;
    	final String mode = modeParam;
    	
    	AndroidGame.this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
            	if (mode == "chrono")
            		remainingText.setText("Temps restant: " + currentRemaining);
            	else if (mode == "moves")
            		remainingText.setText("Coups restants: " + currentRemaining);
            }               
    	});
    }
    
    public void goToMenu()
    {
    	//renderView.pause();
    	Intent intent = new Intent(this, BejewelloMenu.class);
        startActivity(intent);
        finish();
    }
}
