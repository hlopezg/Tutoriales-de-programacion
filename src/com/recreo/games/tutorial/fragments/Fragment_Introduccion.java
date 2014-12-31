package com.recreo.games.tutorial.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.recreo.games.tutorial.R;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_Introduccion extends SherlockFragment {
	Button button_fragment_introduccion_codigo;
	public static final String EXTRA_TITLE = "title";
	TextView textView_introduccion;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View myFragmentView = inflater.inflate(R.layout.fragment_introduccion, container,false);
    	textView_introduccion = (TextView)myFragmentView.findViewById(R.id.textView_introduccion);
     
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
		// setRetainInstance(true);

		textView_introduccion.setText(Html.fromHtml(getString(R.string.introduccion)));
	}
}