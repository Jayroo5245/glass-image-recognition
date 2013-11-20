package com.example.glass_image_recognition;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	private static final int TAKE_PICTURE = 1001;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//mImageView = (ImageView) findViewById(R.id.imageView1);
		imageFromCamera();
	}

	public void imageFromCamera() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, TAKE_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(LOG_TAG, "onActivityResult");
		Log.d(LOG_TAG, "requestCode: " + requestCode);
		Log.d(LOG_TAG, "resultCode: " + resultCode);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				if (data != null && data.getExtras() != null) {
					Bundle extras = data.getExtras();
					final String pathName = (String) extras
							.get("picture_file_path");
					 startImageViewActivity(pathName);
					break;
				}
			}
		}
	}
	private void startImageViewActivity(String filePath){
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.example.glass_image_recognition", "com.example.glass_image_recognition.ImageViewActivity"));
		intent.putExtra(ImageViewActivity.IMAGE_EXTRA, filePath);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
