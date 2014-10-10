package com.rhapsody.xhan.sandbox;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class DrawOverOtherAppFragment extends Fragment {

	ComponentName serviceName;
	public DrawOverOtherAppFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		Button dwOverAppBtn = (Button)rootView.findViewById(R.id.draw_over_other_app_frag_start_button);
		dwOverAppBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				serviceName = getActivity().startService(new Intent(getActivity(), MyDrawService.class));
			}
		});
		
		Button stopDwOverAppBtn = (Button)rootView.findViewById(R.id.draw_over_other_app_frag_stop_button);
		stopDwOverAppBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().stopService(new Intent(getActivity(), MyDrawService.class));
				
			}
		});
		
		Button changePicButton = (Button)rootView.findViewById(R.id.draw_over_other_app_frag_change_button);
		changePicButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(MyDrawService.getUpdateImageIntent(R.drawable.consumer));
				
			}
		});
		return rootView;
	}
}