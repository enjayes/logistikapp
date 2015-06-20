package com.tum.identapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import android.widget.Toast;



import com.tum.ident.IdentificationListener;
import com.tum.ident.IdentificationService;
import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;



public class MainActivity extends ActionBarActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks,IdentificationListener ,SurfaceHolder.Callback  {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private final static String collectingDataString = "Collecting data...";

	private static TextView textViewMain,textViewID;
	private static ImageView imageViewSteps,imageViewSpectrum,imageViewOrientation;

	private static String accountString = collectingDataString;


	private static String simString = collectingDataString;
	private static String callLogString = collectingDataString;
	private static String contactString = collectingDataString;
	private static String bluetoothString = collectingDataString;
	private static String wlanString = collectingDataString;
	private static String packageString = collectingDataString;
	private static String locationString = collectingDataString;
	private static String fileString = collectingDataString;
	private static String musicString = collectingDataString;
	private static String stepCounterString = collectingDataString;
	private static String batteryString = collectingDataString;

	private static String idString = collectingDataString;

	private static Bitmap stepClusterBitmap;

	private static Bitmap spectrumListBitmap;

	private static Bitmap orientationBitmap;

	private static int sectionNumber = 0;

	private static Context context;
	
	private static int stepClusterIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));


		context= getApplicationContext();
		
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);//new SurfaceView(getApplicationContext());//
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);

		String serverURL = "https://192.168.0.102:8443/Android/";//"https://ident.selfhost.eu:8443/Android/";//"https://192.168.0.106:8443/Android/";//"https://ident.selfhost.eu:8443/Android/";
	
		IdentificationService.setServerURL(serverURL);
		IdentificationService.setSurfaceHolder(surfaceHolder);
		IdentificationService.setListener(this);
		IdentificationService.bind(context);

	
		updateLayout();


	}

	public void onReceiveDataItem(DataItem item) {
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("activity", "onPause");
		//IdentificationBinder.unbindService(getApplicationContext());

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
		.beginTransaction()
		.replace(R.id.container,
				PlaceholderFragment.newInstance(position + 1)).commitAllowingStateLoss();
	}

	public void updateLayout(){

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						PlaceholderFragment.updateFragementContent();
					}
				});
			}
		};
		thread.start();


	}

	public void onReceiveIds(String deviceID,String userID){

		idString   = "DeviceID:\n\n"+deviceID+"\n\n\n\n"+"UserID:\n\n"+userID;
		Log.v("MainActivity", "idString: "+idString);
		MainActivity.this.runOnUiThread(new Runnable() { 
			@Override
			public void run() {
				PlaceholderFragment.updateFragementContent();
			}
		});


	}

	public void onReceiveDataSubmitted(boolean result){
		if(result){

			Toast.makeText(getApplicationContext(), "The data has been successfully submitted",
					Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(getApplicationContext(), "An error has occurred while sending data",
					Toast.LENGTH_LONG).show();
		}


		MainActivity.this.runOnUiThread(new Runnable() { 
			@Override
			public void run() {
				PlaceholderFragment.dataSubmitted();

			}
		});
	}


	public void onReceiveUpdate(){
		
		DataController dataController = IdentificationService.getDataContoller();
		if(dataController!=null){
			accountString = dataController.getAccountListString();
			simString       = dataController.getSIMListString()+"\n\n"+dataController.getCarrierListString();
			callLogString   = dataController.getCallLogString();
			contactString   = dataController.getContactString();
			bluetoothString = dataController.getBluetoothString();
			wlanString      = dataController.getWLANString();
			packageString   = dataController.getPackageString();
			locationString  = dataController.getLocationString();
	
			stepCounterString = dataController.getStepCounterString();
	
			batteryString  = dataController.getBatteryString();
			musicString    =  dataController.getMusicString();
			fileString     =  dataController.getFileString();
	
			if(stepClusterBitmap==null){
				stepClusterBitmap = dataController.getAccelerationImage(stepClusterIndex);
			}
			if(spectrumListBitmap==null){
				spectrumListBitmap = dataController.getSpectrumImage(0);
			}
			if(orientationBitmap==null){
				orientationBitmap = dataController.getOrientationImage();
			}
	
	
			MainActivity.this.runOnUiThread(new Runnable() { 
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					PlaceholderFragment.updateFragementContent();
				}
			});
		}

	}

	//if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT){
  
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
    public static boolean copyToClipboard(String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Identification Data", text);
                clipboard.setPrimaryClip(clip);
            }
        	Toast.makeText(context,"Copied to Clipboard",Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
        	Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            return false;
        }
        
    }
	
	
	
	public void onSectionAttached(int number) {
		sectionNumber = number;
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			break;
		case 7:
			mTitle = getString(R.string.title_section7);
			break;
		case 8:
			mTitle = getString(R.string.title_section8);
			break;
		case 9:
			mTitle = getString(R.string.title_section9);
			break;
		case 10:
			mTitle = getString(R.string.title_section10);
			break;
		case 11:
			mTitle = getString(R.string.title_section11);
			break;
		case 12:
			mTitle = getString(R.string.title_section12);
			break;
		case 13:
			mTitle = getString(R.string.title_section13);
			break;
		case 14:
			mTitle = getString(R.string.title_section14);
			break;
		case 15:
			mTitle = getString(R.string.title_section15);
			break;
		case 16:
			mTitle = getString(R.string.title_section16);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";


		private static boolean sendingData = false;

		private static boolean fragmentSet = false;
		
		private static View fragmentView = null;
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			int number = getArguments().getInt(ARG_SECTION_NUMBER);
			View rootView = null;
			switch (number) {
			case 1:
				rootView = inflater.inflate(R.layout.fragment_id, container,false);
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 11:
			case 13:
			case 14:
			case 16:
				rootView = inflater.inflate(R.layout.fragment_main, container,false);
				break;

			case 10:
				rootView = inflater.inflate(R.layout.fragment_steps, container,false);
				break;
			case 12:
				rootView = inflater.inflate(R.layout.fragment_orientation, container,false);
				break;
			case 15:
				rootView = inflater.inflate(R.layout.fragment_spectrum, container,false);
				break;

			default:
				rootView = inflater.inflate(R.layout.fragment_2, container,false);
				break;
			}
			fragmentView = rootView;
			ProgressBar progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar1);
			if(progressBar!=null){
				progressBar.setVisibility(View.VISIBLE);
			}
			fragmentSet = true;
			return rootView;
		}
		

		public static void dataSubmitted(){
			if(sectionNumber==1){
				Object view= fragmentView.findViewById(R.id.progressBarSubmit);
				if(view!=null){
					if(view instanceof ProgressBar){
						ProgressBar progressBar = (ProgressBar)view ;
						if(progressBar!=null){
							progressBar.setVisibility(View.INVISIBLE);
						}
						Button btn = (Button) fragmentView.findViewById(R.id.buttonSubmit);
						if(btn!=null){
							btn.setVisibility(View.VISIBLE);
						}
					}
				}
			}
			sendingData = false;
		}


		public static void updateFragementContent(){
			
			
			
			while(fragmentSet==false){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e){}
			ProgressBar progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar1);
			if(fragmentView!=null){
				DataController dataController = IdentificationService.getDataContoller();
				switch (sectionNumber) {
				case 1:
					if(sendingData==false){
						dataSubmitted();
					}
					textViewID = (TextView) fragmentView.findViewById(R.id.textViewID);
					if(textViewID!=null){
						textViewID.setMovementMethod(new ScrollingMovementMethod());
						textViewID.setText(idString);
					}
					Button btn = (Button) fragmentView.findViewById(R.id.buttonSubmit);
					if(btn!=null){
						btn.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								sendingData = true;
								v.setVisibility(View.INVISIBLE);
								ProgressBar progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBarSubmit);
								if(progressBar!=null){
									progressBar.setVisibility(View.VISIBLE);
								}
								IdentificationService.sendData(true);
							}
						});	  
					}
					
					btn = (Button) fragmentView.findViewById(R.id.buttonCopy);
					if(btn!=null){
						btn.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								MainActivity.copyToClipboard(idString);
							}
						});	  
					}
					break;
				case 2:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(simString);
					}
					break;
				case 3:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(callLogString);
					}
					break;
				case 4:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(contactString);
					}
					break;
				case 5:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(accountString);
					}
					break;
				case 6:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(bluetoothString);
					}
					break;
				case 7:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(wlanString);
					}
					break;
				case 8:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(packageString);
					}
					break;
				case 9:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText("");
					}
					break;
				case 10:
					imageViewSteps = (ImageView)fragmentView.findViewById(R.id.imageViewSteps);
					if(imageViewSteps!=null){
						imageViewSteps.setClickable(true); 
						imageViewSteps.setOnClickListener(new View.OnClickListener() {
						    @Override
						    public void onClick(View v) {
						    	DataController dataController = IdentificationService.getDataContoller();
						    	if(dataController!=null){
							    	stepClusterIndex = dataController.nextClusterIndex(stepClusterIndex);
							    	
									stepClusterBitmap = dataController.getAccelerationImage(stepClusterIndex);
									if(stepClusterBitmap!=null){
										imageViewSteps.setImageBitmap(stepClusterBitmap);
									}
						    	}
						    }
						});
						if(dataController!=null){
							stepClusterBitmap = dataController.getAccelerationImage(stepClusterIndex);
							if(stepClusterBitmap!=null){
								imageViewSteps.setImageBitmap(stepClusterBitmap);
							}
						}
					}
					if(dataController!=null){
						stepCounterString = dataController.getStepCounterString();
						textViewMain = (TextView) fragmentView.findViewById(R.id.textViewStepCounter);
						if(textViewMain!=null){
							textViewMain.setMovementMethod(new ScrollingMovementMethod());
							textViewMain.setText(stepCounterString);
						}
					}
					break;
				case 11:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(locationString);
					}
					break;
				case 12:
					imageViewOrientation = (ImageView)fragmentView.findViewById(R.id.imageViewOrientation);
					if(imageViewOrientation!=null){
						if(dataController!=null){
							orientationBitmap = dataController.getOrientationImage();
							if(orientationBitmap!=null){
								imageViewOrientation.setImageBitmap(orientationBitmap);
							}
						}
					}
					break;
				case 13:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(fileString);
					}
					break;
				case 14:
					textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
					if(textViewMain!=null){
						textViewMain.setMovementMethod(new ScrollingMovementMethod());
						textViewMain.setText(musicString);
					}
					break;
				case 15:
					imageViewSpectrum = (ImageView)fragmentView.findViewById(R.id.imageViewSpectrum);
					if(imageViewSpectrum!=null){
						if(dataController!=null){
							spectrumListBitmap = dataController.getSpectrumImage(0);
							if(spectrumListBitmap!=null){
									imageViewSpectrum.setImageBitmap(spectrumListBitmap);
							}
						}
					}
					break;
				case 16:
					if(dataController!=null){
						batteryString  = dataController.getBatteryString();
						textViewMain = (TextView) fragmentView.findViewById(R.id.textViewMain);
						if(textViewMain!=null){
							textViewMain.setMovementMethod(new ScrollingMovementMethod());
							textViewMain.setText(batteryString);
						}
					}
					break;	

				}

			}
			if(progressBar!=null){
				progressBar.setVisibility(View.INVISIBLE);
			}



		}


		@Override
		public void onAttach(Activity activity) {
			fragmentSet = false;
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
			((MainActivity) activity).updateLayout();



		}
	}



	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}


	@Override
	protected void onResume() {


		super.onResume();
	}

	@Override
	protected void onPause() {
		//FIXME put back
		super.onPause();

	}
	
/*
	private void startIdentificationService(Context context, String serverURL,SurfaceHolder surfaceHolder,MyIdentificationListener identificationListener ){
		IdentificationService.setServerURL(serverURL);
		IdentificationService.setSurfaceHolder(surfaceHolder);
		IdentificationService.setListener(identificationListener);
		IdentificationService.bind(context);
	}
*/
	
}







