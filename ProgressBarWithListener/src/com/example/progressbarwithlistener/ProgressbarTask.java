/**
 * 
 */
package com.example.progressbarwithlistener;

import android.os.Handler;
import android.view.View;

/**
 * @author Rafael.Leon
 * 
 */
public class ProgressbarTask implements Runnable {
	private Handler 				mHandler = new Handler();
	private OnProgressBarListener 	mProgressBarListener;
	private View					mView;
	
	interface OnProgressBarListener{
		public void setProgress(int value, View v);
		public void onProgressFinish(View v);
	}
	
	public ProgressbarTask(View view, OnProgressBarListener mProgressBarListener){
		this.mProgressBarListener = mProgressBarListener;
		this.mView = view;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for (int i = 0 ; i <= 1000; i++){
            final int value = i;
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                	mProgressBarListener.setProgress(value, mView);
                }
            });
            
        }
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				mProgressBarListener.onProgressFinish(mView);
			}
		});
	}

}
