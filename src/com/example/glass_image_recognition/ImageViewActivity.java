package com.example.glass_image_recognition;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kooaba.KooabaApi;

public class ImageViewActivity extends Activity {
	private static final String LOG_TAG = ImageViewActivity.class
			.getSimpleName();

	public static final String IMAGE_EXTRA = "imageExtra";
	private ImageView mImageView;
	private TextView mTextView;
	private ProgressBar mProgressBar;
	private String mFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageview);
		mImageView = (ImageView) findViewById(R.id.imageView2);
		mTextView = (TextView) findViewById(R.id.textView1);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mFilePath = extras.getString(IMAGE_EXTRA);
			Log.d(LOG_TAG, "pathName: " + mFilePath);
			Log.d(LOG_TAG, "FileObserver.CREATE: " + FileObserver.CLOSE_WRITE);
			//mFilePath = "/mnt/sdcard/DCIM/Camera/default.jpeg";
			File file = new File(mFilePath);
			//File file = new File("/mnt/sdcard/DCIM/Camera/default.jpg");
			if (file.exists()) {
				Log.d(LOG_TAG, "File exits processing image");
				ImageViewActivity.this
				.runOnUiThread(new Runnable() {
					public void run() {
						Log.d(LOG_TAG, "I am the UI thread");
						updateImageView(mFilePath);
						new QueryTask(
								ImageViewActivity.this)
								.execute(mFilePath);
					}
				});
			} else {
				FileObserver observer = new FileObserver(file.getParent()) {
					@Override
					public void onEvent(int event, String file) {
						Log.d(LOG_TAG, "FileObserver event: " + event);
						if (event == FileObserver.CLOSE_WRITE) {
							Log.d(LOG_TAG,
									"CLOSE_WRITE received, attempting to load. ");
							stopWatching();
							Log.d(LOG_TAG, "File created [" + mFilePath + "]");
							ImageViewActivity.this
									.runOnUiThread(new Runnable() {
										public void run() {
											Log.d(LOG_TAG, "I am the UI thread");
											updateImageView(mFilePath);
											new QueryTask(
													ImageViewActivity.this)
													.execute(mFilePath);
										}
									});
						}
					}
				};
				observer.startWatching();
			}

		}
	}

	private void updateImageView(String filePath) {
		mTextView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		Bitmap thumbnail = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		thumbnail = BitmapFactory.decodeFile(filePath, options);
		Log.d(LOG_TAG, "Pre Setting mImageView with bitmap:");
		Log.d(LOG_TAG, "thumbnail: "
				+ ((thumbnail == null) ? "null" : "Not null"));
		Log.d(LOG_TAG, "mImageView: "
				+ ((mImageView == null) ? "null" : "Not null"));
		if (mImageView != null && thumbnail != null) {
			mImageView.setImageBitmap(thumbnail);
			Log.d(LOG_TAG, "Setting mImageView with bitmap:");
		} else {
			mTextView.setText("Error, Please try again");
			mTextView.setVisibility(View.VISIBLE);
		}
	}

	class QueryTask extends AsyncTask<String, Integer, Object> {

		private Activity activity;
		private KooabaApi kooaba;
		private TextView tv;

		public QueryTask(Activity activity) {
			this.activity = activity;
			this.kooaba = new KooabaApi("369c5f92-1e24-4af2-bc94-885cacefd91b",
					"KGzBhFug6N55qTb05nw0TO5uTR3xjrZJGlpeZfdw");
			// this.tv = (TextView)this.activity.findViewById(R.id.text);
		}

		protected void onPreExecute() {
			Log.d(LOG_TAG, "onPreExecute()");
			// tv.append("\n\n");
			// tv.append("Recognizing image... ");
		}

		protected Long doInBackground(String... paths) {
			Log.d(LOG_TAG, "doInBackground()");
			String imagePath = paths[0];

			try {
				kooaba.query(imagePath);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Object result) {
			Log.d(LOG_TAG, "onPostExecute()");
			Log.d(LOG_TAG, "HTTP Status: " + kooaba.getResponseStatus() + "\n");
			Log.d(LOG_TAG, "HTTP Response: " + kooaba.getResponseBody() + "\n");
			// tv.append("Done.\n\n");
			// tv.append("HTTP Status: " + kooaba.getResponseStatus() + "\n");
			// tv.append("HTTP Response: " + kooaba.getResponseBody() + "\n");
		}
	}
}
