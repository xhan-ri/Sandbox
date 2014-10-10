package org.xhan.sandbox;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class MyDrawService extends Service {

	WindowManager windowManager;
	ImageView myHead;
	WindowManager.LayoutParams params;
	private static final String UPDATE_IMAGE_ACTION="MYDRAWSERVICE_UPDATE_IMAGE";
	private static final String IMAGE_RESOURCE_ID_NAME = "image_id";
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int result = super.onStartCommand(intent, flags, startId);
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		myHead = new ImageView(this);
		myHead.setImageResource(R.drawable.face);
		
		params = new WindowManager.LayoutParams(
		        WindowManager.LayoutParams.WRAP_CONTENT,
		        WindowManager.LayoutParams.WRAP_CONTENT,
		        WindowManager.LayoutParams.TYPE_PHONE,
		        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
		        PixelFormat.TRANSLUCENT);
		
		params.gravity = Gravity.TOP | Gravity.LEFT;
	    params.x = 0;
	    params.y = 100;
	    
	    myHead.setOnTouchListener(new OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
			        initialX = params.x;
			        initialY = params.y;
			        initialTouchX = event.getRawX();
			        initialTouchY = event.getRawY();
			        return true;
			      case MotionEvent.ACTION_UP:
			        return true;
			      case MotionEvent.ACTION_MOVE:
			        params.x = initialX + (int) (event.getRawX() - initialTouchX);
			        params.y = initialY + (int) (event.getRawY() - initialTouchY);
			        windowManager.updateViewLayout(myHead, params);
			        return true;
				}
				return false;
			}
		});
	    
	    windowManager.addView(myHead, params);
	    BroadcastReceiver receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				myHead.setImageResource(getImageResourceIdFromIntent(intent));
			}
			
			private int getImageResourceIdFromIntent(Intent intent) {
				return intent.getIntExtra(IMAGE_RESOURCE_ID_NAME, 0);
			}
		};
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, MyDrawService.getUpdateImageIntentFilter());
		return result;
	}



	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (myHead != null) {
			windowManager.removeView(myHead);
			myHead = null;
		}
		windowManager = null;
	}
	
	private static IntentFilter getUpdateImageIntentFilter() {
		IntentFilter filter = new IntentFilter(UPDATE_IMAGE_ACTION);
		return filter;
	}
	

	public static Intent getUpdateImageIntent(int resId) {
		Intent updateIntent = new Intent(UPDATE_IMAGE_ACTION);
		updateIntent.putExtra(IMAGE_RESOURCE_ID_NAME, resId);
		return updateIntent;
	}
}
