package com.tp1.framework.implementation;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.tp1.framework.Input;

public class AndroidInput implements Input {    
    TouchHandler touchHandler;
    static AndroidInput _instance;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
    	AndroidInput._instance = this;
        if(VERSION.SDK_INT < 5) //original : Integer.parseInt(VERSION.SDK)
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);        
    }


    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }



    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    
}
