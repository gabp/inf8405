package com.tp1.game;

import com.tp1.framework.Audio;
import com.tp1.framework.FileIO;
import com.tp1.framework.Graphics;
import com.tp1.framework.Input;
import com.tp1.framework.Screen;
import com.tp1.framework.implementation.AndroidAudio;
import com.tp1.framework.implementation.AndroidFastRenderView;
import com.tp1.framework.implementation.AndroidFileIO;
import com.tp1.framework.implementation.AndroidGraphics;
import com.tp1.framework.implementation.AndroidInput;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BejewelloMenu extends Activity
{
	Button b;
	WakeLock wakeLock;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 800: 1280;
        int frameBufferHeight = isPortrait ? 1280: 800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();
        
        setContentView(R.layout.main_menu);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
    }
    
    public void changeActivity(View v)
	{
    	Intent intent = new Intent(this, Bejewello.class);
        startActivity(intent);
        finish();
	}
}
