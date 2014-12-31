package com.example.tutorial;

import java.io.File;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_Introduccion extends Fragment {
	Button button_fragment_introduccion_codigo;
    public static final String EXTRA_TITLE = "title";
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View myFragmentView = inflater.inflate(R.layout.fragment_introduccion_codigo, container,false);
    	
    	button_fragment_introduccion_codigo = (Button) myFragmentView.findViewById(R.id.button_fragment_introduccion_codigo);
 
     
        return myFragmentView;
    }
 
    public static Bundle createBundle( String title ) {
        Bundle bundle = new Bundle();
        bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		button_fragment_introduccion_codigo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		        Uri uri;
				try {
					uri = Uri.fromFile(new File(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).applicationInfo.dataDir));
					sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
					startActivity(sharingIntent);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}