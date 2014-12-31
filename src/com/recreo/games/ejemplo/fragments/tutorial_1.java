package com.recreo.games.ejemplo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.recreo.games.tutorial.R;

public class tutorial_1  extends SherlockFragment {
	public static final String EXTRA_TITLE = "title";
	EditText editText_tutorial_1;
	Button button_tutorial_1;

	int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView;

		myFragmentView = inflater.inflate(R.layout.tutorial_1, container, false);
		editText_tutorial_1  = (EditText)myFragmentView.findViewById(R.id.editText_tutorial_1);
		button_tutorial_1 = (Button)myFragmentView.findViewById(R.id.button_tutorial_1);
		
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
		setHasOptionsMenu(true); // para utilizar onOptionsItemSelected en un fragment
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true); // para que no se caiga en algunas ocaciones (cuando hago un intent dentro de un buton en un fragment)
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		button_tutorial_1.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				String mensaje_editText;
				mensaje_editText = editText_tutorial_1.getText().toString();
				
				Toast.makeText(arg0.getContext(),mensaje_editText,Toast.LENGTH_LONG).show();
			}
		});
	}


}
