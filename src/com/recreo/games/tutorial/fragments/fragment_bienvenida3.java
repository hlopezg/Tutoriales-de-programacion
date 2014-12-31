package com.recreo.games.tutorial.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.recreo.games.tutorial.R;

public class fragment_bienvenida3 extends SherlockFragment {
	AnimationDrawable animacion;
	ImageView vista;

	private static String KEY_CONTENT = "myfragment:content";

	public static fragment_bienvenida3 newInstance(String content) {
		fragment_bienvenida3 fragment = new fragment_bienvenida3();
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		vista.setImageDrawable(animacion);
		animacion.start();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.fragment3_bienvenida_tutorial, null);
		animacion = (AnimationDrawable) getResources().getDrawable(R.drawable.animacion_bienvenida);
		vista = (ImageView) myView.findViewById(R.id.imageView_bienvenida_3);
		return myView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}