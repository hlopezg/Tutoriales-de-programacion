package com.recreo.games.tutorial.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.recreo.games.tutorial.R;

public class Fragment_TabsAutor  extends SherlockFragment {
	public static final String EXTRA_TITLE = "title";
	 private TextView textView_texto;
	 
	int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView;

		myFragmentView = inflater.inflate(R.layout.fragment_autor, container, false);
		textView_texto = (TextView) myFragmentView.findViewById(R.id.textView_texto);

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
		
		try {
			/*RelativeLayout layout = (RelativeLayout)getActivity().findViewById(R.id.relative_layout3);
			RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
				    RelativeLayout.LayoutParams.WRAP_CONTENT, 
				    RelativeLayout.LayoutParams.WRAP_CONTENT);

				lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				
			AdView adView = new AdView(getActivity(), AdSize.BANNER, "ca-app-pub-2160329755414556/5031531628");
			
		    AdRequest adRequest = new AdRequest();
		    adRequest.addTestDevice("C5CFBB618F8E113922D43476C8465CCE");  
		    
		    adView.loadAd(adRequest);
		    layout.addView(adView, lay);*/
		    
			textView_texto.setText(Html.fromHtml(getString(R.string.sobre_el_autor)));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
