package com.gatimus.bouncingballs;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

public class Settings extends PreferenceActivity {
	
	private static final String TAG = "Settings:";
	private FragmentManager fragMan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Create Activity");
		super.onCreate(savedInstanceState);
		fragMan = this.getFragmentManager();
		fragMan.beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	} //onCreate
	
	public class SettingsFragment extends PreferenceFragment {
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			Log.i(TAG, "Create Fragment");
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings);
		} //onCreate
		
	} //inner class

} //class
