package com.recreo.games.tutorial.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.recreo.games.tutorial.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Introduccion_codigo extends SherlockFragment {
	Button button_fragment_introduccion_codigo;
	public static final String EXTRA_TITLE = "title";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.fragment_introduccion_codigo, container, false);

		button_fragment_introduccion_codigo = (Button) myFragmentView.findViewById(R.id.button_fragment_introduccion_codigo);

		return myFragmentView;
	}

	public static Bundle createBundle(String title) {
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_TITLE, title);
		return bundle;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	 @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        setUserVisibleHint(true);
	    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//setRetainInstance(true);   

		button_fragment_introduccion_codigo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
					Uri uri;
					uri = Uri.parse("file:///android_asset/Fragment_Introduccion.java");
					sharingIntent.setType("message/rfc822");
					sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hector.lopez.gatica@gmail.com" });
					sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Codigo");
					sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
					sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
					getActivity().startActivity(Intent.createChooser(sharingIntent, "Seleccione su gestor de correo favorito para enviar este código."));

					//Intent  enlaceMenu = new Intent (v.getContext(), MainActivity.class);
				    
				   	//getActivity().startActivity(enlaceMenu);
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}