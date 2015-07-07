/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */


package de.marktlogistikapp.logistikapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;

import org.apache.cordova.*; 

import android.webkit.WebSettings.ZoomDensity;

public class LogistikApp extends CordovaActivity
{

	@SuppressLint("NewApi")
	
     
	private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
	
	@Override  
    public void onCreate(Bundle  savedInstanceState)
    {    
        super.onCreate(savedInstanceState); 
        // Set by <content src="index.html" /> in config.xml
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        loadUrl(launchUrl+"#android"); 

   
        WebSettings settings = appView.getSettings( ); 
     
       
        
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true); 
        settings.setUseWideViewPort(true);  

        settings.setDefaultZoom(ZoomDensity.MEDIUM); 
        settings.setSupportZoom(true );

        
        /*Start Custom Edeka Code
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else{
	        View decorView = getWindow().getDecorView();
		     int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		     decorView.setSystemUiVisibility(uiOptions);
		     ActionBar actionBar = getActionBar();
		     actionBar.hide();
        }
        End Costum Edeka Code*/      
        
        
    } 
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	  super.onWindowFocusChanged(hasFocus);
	  if(!hasFocus) {
	      // Close every kind of system dialog
	    Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
	    sendBroadcast(closeDialog);
	  }
	}
	
	/*
	@Override
	public void onDestroy() {
		super.onDestroy();
		

		Intent intent = null;
		intent = new Intent(this, LogistikApp.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);
		

	}
*/
	
	
	@Override 
	public void onBackPressed(){  

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	  if (blockedKeys.contains(event.getKeyCode())) {
	    return true;
	  } else {
	    return super.dispatchKeyEvent(event);
	  }
	}
	
	/*
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_HOME) {

			return true;
		}
		return super.onKeyDown(keyCode, event);    
	}
	*/

} 
 

