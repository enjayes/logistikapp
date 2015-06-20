package com.tum.identapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BitmapSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private TutorialThread _thread;

	Bitmap bitmap = null;
	
	
	
	public BitmapSurfaceView(Context context) {
		super(context);
		Log.v("MainActivity", "BitmapSurfaceView!!!!");
		_thread = new TutorialThread(getHolder(), this);
		getHolder().addCallback(this);
	
	}
	
	public BitmapSurfaceView(Context context, AttributeSet attrs) {
		
        super(context, attrs);
    	_thread = new TutorialThread(getHolder(), this);
        getHolder().addCallback(this);
    }

    public BitmapSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    	_thread = new TutorialThread(getHolder(), this);
        getHolder().addCallback(this);
    }
	
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if(canvas!=null){
			if(bitmap!=null){
				canvas.drawColor(Color.WHITE);
				float xpos = (float)((canvas.getWidth()-bitmap.getWidth())*0.5);
				float ypos = (float)((canvas.getHeight()-bitmap.getHeight())*0.5);
				canvas.drawBitmap(bitmap,xpos,ypos, null);
				Log.v("MainActivity","canvas.drawBitmap(bitmap, 0, 0, null);");
				Log.v("MainActivity","w:"+bitmap.getWidth());
			}
		}

	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) {
	
		_thread.setRunning(true);
		_thread.start();
		setWillNotDraw(false);
		Log.v("MainActivity", "surfaceCreated(SurfaceHolder arg0)");
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.v("MainActivity", "surfaceDestroyed");
		boolean retry = true;
		_thread.setRunning(false);
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	class TutorialThread extends Thread {
		private SurfaceHolder _surfaceHolder;
		private BitmapSurfaceView _panel;
		private boolean _run = false;

		public TutorialThread(SurfaceHolder surfaceHolder, BitmapSurfaceView panel) {
			_surfaceHolder = surfaceHolder;
			_panel = panel;
		}

		public void setRunning(boolean run) {
			_run = run;
		}

		@SuppressLint("WrongCall")
		@Override
		public void run() {
			Canvas c;
			while (_run) {
				c = null;
				try {
					c = _surfaceHolder.lockCanvas(null);
					synchronized (_surfaceHolder) {
						_panel.onDraw(c);
					}
				} finally {
					if (c != null) {
						_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}
}
