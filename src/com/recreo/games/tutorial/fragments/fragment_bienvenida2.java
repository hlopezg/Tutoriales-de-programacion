package com.recreo.games.tutorial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recreo.games.tutorial.R;

public class fragment_bienvenida2 extends Fragment {

	private static String KEY_CONTENT = "myfragment:content";

	public static fragment_bienvenida2 newInstance(String content) {

		fragment_bienvenida2 fragment = new fragment_bienvenida2();
		fragment.mContent = content;

		return fragment;
	}

	private String mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.fragment2_bienvenida_tutorial, null);
		return myView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}