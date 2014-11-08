Android-ProgressBarWithListener
===============================

3 progress bar in 3 different threads

![](./ProgressBarWithListener/res/drawable-hdpi/screen_shot_1.png?raw=true "Sreen Shot" )

The thread are constructed by the next class,

```Java
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
```
Note that the interface gives a progress update and when the progress is finished. The handler is used to communicate with the main thread.

```Java
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
```
The View object(button) is passed to identify the corresponding progress bar to be updated. When a new thread is created, a new progressTask is created too.

Is important not to forget the Thread.start(), and remmember that Thread.stop() is deprecated... so remmember to make the Thread null when finished using. This makes it easier for the garbage collector to release the memory.
