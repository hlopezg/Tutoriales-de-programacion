package com.recreo.games.tutorial.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.recreo.games.tutorial.R;

public class Fragment_TabsActivity extends SherlockFragment {
	public static final String EXTRA_TITLE = "title";
	public static WebView mWebView;

	int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView;

		myFragmentView = inflater.inflate(R.layout.fragment_tabsactivity, container, false);
		mWebView = (WebView) myFragmentView.findViewById(R.id.webview);

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
		
		RelativeLayout layout = (RelativeLayout)getActivity().findViewById(R.id.relative_layout4);
		RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
			    RelativeLayout.LayoutParams.WRAP_CONTENT, 
			    RelativeLayout.LayoutParams.WRAP_CONTENT);

			lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			
		AdView adView = new AdView(getActivity(), AdSize.BANNER, "ca-app-pub-2160329755414556/5031531628");
		
	    AdRequest adRequest = new AdRequest();
	    adRequest.addTestDevice("C5CFBB618F8E113922D43476C8465CCE");  
	    
	    adView.loadAd(adRequest);
	    layout.addView(adView, lay);

		id = getActivity().getIntent().getExtras().getInt("id");

		if (id == 1)
			mWebView.loadUrl("http://chuwiki.chuidiang.org/index.php?title=Hola_mundo_en_Android");
		else if (id == 2)
			mWebView.loadUrl("file:///android_asset/versiones_de_android.html");
		else if (id == 3)
			mWebView.loadUrl("file:///android_asset/componentes_aplicacion_android.html");
		else if (id == 4)
			mWebView.loadUrl("file:///android_asset/creando_un_proyecto_en_android.html");
		else if (id == 5)
			mWebView.loadUrl("file:///android_asset/empezando_nuestro_proyecto.html");
		else
			mWebView.loadUrl("file:///android_asset/Layouts.html");

	}
}
