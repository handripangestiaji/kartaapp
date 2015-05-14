package com.karta;

import com.nineoldandroids.animation.ObjectAnimator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
 
public class Splash extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        
		LinearLayout linLay = (LinearLayout) findViewById(R.id.splash);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(linLay, "alpha", 0f, 1f);
        fadeIn.setDuration(2000);
        fadeIn.start();
        
        ImageView animView = (ImageView) findViewById(R.id.splashLogo);
        ObjectAnimator mover = ObjectAnimator.ofFloat(animView, "translationY", (float) -(height/4), 0f);
        mover.setDuration(2000);
        mover.start();
         
        new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Home.class);
                startActivity(i); 
            }
        }, SPLASH_TIME_OUT);
    }
    
}