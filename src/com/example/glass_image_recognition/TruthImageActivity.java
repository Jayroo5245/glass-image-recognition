package com.example.glass_image_recognition;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class TruthImageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//mImageView = (ImageView) findViewById(R.id.imageView1);
		startImageViewActivity("/mnt/sdcard/DCIM/Camera/default.jpeg");
	}
	private void startImageViewActivity(String filePath){
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.example.glass_image_recognition", "com.example.glass_image_recognition.ImageViewActivity"));
		intent.putExtra(ImageViewActivity.IMAGE_EXTRA, filePath);
		startActivity(intent);
		finish();
	}

}
