package com.example.glass_image_recognition;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	File mImageFile;
	String mSelectedImagePath;
	int TAKE_PICTURE = 1001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView textView = (TextView)findViewById(R.id.recognition_text_TextView);
		Intent intent = getIntent();
		if(intent != null && intent.getExtras() != null){
			ArrayList<String> voiceResults = intent.getExtras()
			        .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
			if(textView != null && voiceResults != null && voiceResults.size() > 0){
				textView.setText(voiceResults.get(0));
			}
		}
		//imageFromCamera();
	}
	
	public void imageFromCamera() {
	    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	    mImageFile = new File(Environment.getExternalStorageDirectory()+File.separator+"MyApp",  
	            "PIC"+System.currentTimeMillis()+".jpg");
	    mSelectedImagePath = mImageFile.getAbsolutePath();
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
	    startActivityForResult(intent, TAKE_PICTURE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (resultCode == RESULT_OK) {
//	        switch(requestCode) {
//	        case TAKE_PICTURE:
//	                    //Launch ImageEdit Activity
//	            Intent i = new Intent(this, ImageEdit.class);
//	                    i.putString("imgPath", "mSelectedImagePath");
//	                    startActivity(i);
//	            break;
//	        }
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
