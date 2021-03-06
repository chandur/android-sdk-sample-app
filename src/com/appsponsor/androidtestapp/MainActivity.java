package com.appsponsor.androidtestapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.appsponsor.appsponsorsdk.AdActivityProperties;
import com.appsponsor.appsponsorsdk.PopupAd;
import com.appsponsor.appsponsorsdk.PopupAd.PopupAdListener;
import com.appsponsor.appsponsorsdk.RewardedAd;

public class MainActivity extends Activity {
	
	private Button btn_show_ad;
	private Button btn_show_ad_dev;
	private Button btn_clear;
	private PopupAd popupAd;
	private RewardedAd popupAd2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
	        TextView txtVersion = (TextView)findViewById(R.id.info);
			txtVersion.setText(info.packageName + " ver. " + info.versionName + " (07/22/2013)");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		popupAd = new PopupAd(MainActivity.this, "oIs29VQKIa2IfaA4FWkEqw");
		popupAd2 = new RewardedAd(MainActivity.this, "82lEvN030_0zL0kShgS_hw", "support@appsponsor.com");
		
		popupAd.setPopupAdListener(new PopupAdListener() {
			
			@Override
			public void willDisappear(DisappearReason reason) {
				Log.d("testApp", "popupAdLog1 willDisappear " + reason.name());
			}
			
			@Override
			public void willAppear() {
				Log.d("testApp", "popupAdLog1 willAppear");
			}
			
			@Override
			public void popoverDidFailToLoadWithError(Exception exception) {
				Log.d("testApp", "popupAdLog1 popoverDidFailToLoadWithError");
			}
			
			@Override
			public void onRewardedAdFinished() {
				Log.d("testApp", "popupAdLog1 onRewardedAdFinished " + popupAd.rewardedAdStatus());
			}
			
			@Override
			public void didCacheInterstitial() {
				Log.d("testApp", "popupAdLog1 didCacheInterstitial");
			}
		});
		
		popupAd2.setPopupAdListener(new PopupAdListener() {
			
			@Override
			public void willDisappear(DisappearReason reason) {
				Log.d("testApp", "popupAdLog2 willDisappear " + reason.name());
			}
			
			@Override
			public void willAppear() {
				Log.d("testApp", "popupAdLog2 willAppear");
			}
			
			@Override
			public void popoverDidFailToLoadWithError(Exception exception) {
				Log.d("testApp", "popupAdLog2 popoverDidFailToLoadWithError");
			}
			
			@Override
			public void onRewardedAdFinished() {
				Log.d("testApp", "popupAdLog2 onRewardedAdFinished " + popupAd2.rewardedAdStatus());
			}
			
			@Override
			public void didCacheInterstitial() {
				Log.d("testApp", "popupAdLog2 didCacheInterstitial");
			}
		});
		
		btn_show_ad = (Button) findViewById(R.id.btn_show_ad);
		btn_show_ad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupAd.load(); 
			}
		});
		
		btn_show_ad_dev = (Button) findViewById(R.id.btn_show_ad_dev);
		btn_show_ad_dev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupAd2.load(); 
			}
		});
		
		((Button) findViewById(R.id.btn_show_video_ad)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupAd2.presentAd();

			}
		});
		
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				popupAd.presentAd();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		popupAd.release();
		popupAd = null;
		popupAd2.release();
		popupAd2 = null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case PopupAd.AD_WILL_DISAPPEAR_CODE:
			if (resultCode == AdActivityProperties.RESULT_AD_FINISHED) {
				System.out.println("Activity Finished");
			}
			break;
		}
	}	
}
