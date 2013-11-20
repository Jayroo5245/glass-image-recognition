package com.example.glass_image_recognition;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
