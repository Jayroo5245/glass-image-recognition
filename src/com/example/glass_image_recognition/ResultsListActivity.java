package com.example.glass_image_recognition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsListActivity extends Activity {
	private static final String LOG_TAG = ResultsListActivity.class
			.getSimpleName();

	public static final String RESPONSE_EXTRA = "responseExtra";
	public static
	ListView mListView;
	ArrayList<String> mArrayList;
	String mResponse;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_list);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mResponse = extras.getString(RESPONSE_EXTRA);
		}
		mListView = (ListView) findViewById(R.id.resultsList);
		mArrayList = populateArrayList(mResponse);
		ArrayAdapter<String> arrayAdapter = 
		         new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mArrayList);
		mListView.setAdapter(arrayAdapter);
	}
	
	private ArrayList<String> populateArrayList(String response){
		ArrayList<String> array = new ArrayList<String>();
		if(response == null || response.isEmpty()){
			array.add("No Match");
			return array;
		} else {
			try {
				JSONArray jsonArray = new JSONArray(response);
				if(jsonArray.length() < 1){
					array.add("No Match");
					return array;
				}
				array.add("Results:");
				//Score
				JSONObject obj = (JSONObject)jsonArray.get(0);
				array.add("Score:" + obj.get("score"));
				obj = (JSONObject) obj.get("metadata");
				array.add("Sprite: " + "Sprite");
				obj = (JSONObject) obj.get("Sprite");
				Iterator<String> keys = obj.keys();
				while(keys.hasNext()){
					String key = keys.next();
					array.add(key +": " + obj.get(key));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array;
	}
	
}