package com.example.progressbarwithlistener;

import com.example.progressbarwithlistener.ProgressbarTask.OnProgressBarListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	private ProgressBar mProgressBar1, mProgressBar2, mProgressBar3;
	private Thread mThread1, mThread2, mThread3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgressBar1  = (ProgressBar) findViewById(R.id.progressbar_1);
		mProgressBar2  = (ProgressBar) findViewById(R.id.progressbar_2);
		mProgressBar3  = (ProgressBar) findViewById(R.id.progressbar_3);
	}
	
	public void startProgress(View v){
		int viewId = v.getId();
		if (viewId == R.id.button_1){
			if (mThread1 == null){
				mThread1 = new Thread(new ProgressbarTask(v,mOnProgressBarListener));
				mThread1.start();
			}
		} else if (viewId == R.id.button_2){
			if (mThread2 == null){
				mThread2 = new Thread(new ProgressbarTask(v,mOnProgressBarListener));
				mThread2.start();
			}
		}else if (viewId == R.id.button_3){
			if (mThread3 == null){
				mThread3 = new Thread(new ProgressbarTask(v,mOnProgressBarListener));
				mThread3.start();
			}
		}
	}
	
	OnProgressBarListener mOnProgressBarListener = new OnProgressBarListener() {

		@Override
		public void setProgress(int value, View v) {
			int viewId = v.getId();
			if (viewId == R.id.button_1){
				mProgressBar1.setProgress(value);
			} else if (viewId == R.id.button_2){
				mProgressBar2.setProgress(value);
			}else if (viewId == R.id.button_3){
				mProgressBar3.setProgress(value);
			}
		}

		@Override
		public void onProgressFinish(View v) {
			int viewId = v.getId();
			if (viewId == R.id.button_1){
				mThread1 = null;
			} else if (viewId == R.id.button_2){
				mThread2 = null;
			}else if (viewId == R.id.button_3){
				mThread3 = null;
			}
		}
	};
}
