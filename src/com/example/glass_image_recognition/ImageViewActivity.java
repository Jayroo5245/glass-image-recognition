package com.example.glass_image_recognition;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {
	private static final String LOG_TAG = ImageViewActivity.class.getSimpleName();

	public static final String IMAGE_EXTRA = "imageExtra";
	private ImageView mImageView;
	private String mFilePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageview);
		mImageView = (ImageView) findViewById(R.id.imageView2);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			mFilePath = extras.getString(IMAGE_EXTRA);
			Log.d(LOG_TAG, "pathName: " + mFilePath);
			Log.d(LOG_TAG, "FileObserver.CREATE: " + FileObserver.CLOSE_WRITE);
			File file = new File(mFilePath);
	        if (file.exists()) {
				Log.d(LOG_TAG, "File exits processing image");
	        	updateImageView(mFilePath);
	        } else {
				FileObserver observer = new FileObserver(file.getParent()) {
				     @Override
				     public void onEvent(int event, String file) {
				    	 Log.d(LOG_TAG, "FileObserver event: " + event);
				         if(event == FileObserver.CLOSE_WRITE){// && !file.equals(".probe")){ // check if its a "create" and not equal to .probe because thats created every time camera is launched
				        	 Log.d(LOG_TAG, "CLOSE_WRITE received, attempting to load. ");
				        	 stopWatching();
				        	 Log.d(LOG_TAG, "File created [" + mFilePath + "]");
				        	 ImageViewActivity.this.runOnUiThread(new Runnable(){
				        		    public void run(){
				        		        Log.d(LOG_TAG, "I am the UI thread");
				        		        updateImageView(mFilePath);
				        		    }
				        		});
				         }
				     }
				 };
				 observer.startWatching();	        	
	        }

		} 
	}
	
	private void updateImageView(String filePath){
		Bitmap thumbnail = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		options.outWidth = 640;
		options.outHeight = 360;
		thumbnail = BitmapFactory.decodeFile(filePath, options);
		Log.d(LOG_TAG, "Pre Setting mImageView with bitmap:");
		Log.d(LOG_TAG, "thumbnail: " + ((thumbnail == null) ? "null":"Not null"));
		Log.d(LOG_TAG, "mImageView: " + ((mImageView == null) ? "null":"Not null"));
		if (mImageView != null && thumbnail != null) {
			mImageView.setImageBitmap(thumbnail);
			Log.d(LOG_TAG, "Setting mImageView with bitmap:");
		}
	}
}
