package com.recreo.games.tutorial.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.recreo.games.tutorial.R;
import com.recreo.games.clases.globales;

public class Fragment_TabsAbout  extends SherlockFragment {
	public static final String EXTRA_TITLE = "title";
	public static WebView mWebView;
	 private TextView textView_version, textView_fecha,textView_autor;
	 AdView adView;

	 Button button_about_compartir, buttonabout_califica,button_about_enviar_correo;
	 
	int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView;

		myFragmentView = inflater.inflate(R.layout.fragment_about, container, false);
		//textView_titulo = (TextView) myFragmentView.findViewById(R.id.textView_titulo);
		textView_version = (TextView) myFragmentView.findViewById(R.id.textView_version);
		textView_fecha = (TextView) myFragmentView.findViewById(R.id.textView_fecha);
		textView_autor = (TextView) myFragmentView.findViewById(R.id.textView_autor);
		button_about_compartir = (Button) myFragmentView.findViewById(R.id.button_about_compartir);
        buttonabout_califica = (Button) myFragmentView.findViewById(R.id.buttonabout_califica);
        button_about_enviar_correo = (Button) myFragmentView.findViewById(R.id.button_about_enviar_correo);

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
		    
			textView_version.setText(getString(R.string.version) + ": " +  getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName.toString());
			textView_fecha.setText(getString(R.string.fecha_ultima_version) + ": " + globales.fecha_actualizacion(getActivity()));
			
			textView_autor.setText(Html.fromHtml(getString(R.string.about_autor)));
			
			buttonabout_califica.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Uri uri = Uri.parse("market://details?id=" + arg0.getContext().getPackageName());
					Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					try {
					  startActivity(goToMarket);
					} catch (ActivityNotFoundException e) {
					  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + arg0.getContext().getPackageName())));
					}
				}
			});
			
			button_about_compartir.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
 
				    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);  
				      
				    shareIntent.setType("text/plain");  
				    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   "Tutoriales de programación");  
  
				    String shareMessage = getString(R.string.hecha_un_vistazo) + "  https://play.google.com/store/apps/details?id=com.recreo.games.tutorial&hl=es";  
				        
				    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareMessage);  
				        
				    startActivity(Intent.createChooser(shareIntent, "¡Comparte esta aplicación!"));  
				}
			});
			
			button_about_enviar_correo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
					sharingIntent.setType("message/rfc822");
					sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hector.lopez.gatica@gmail.com" });
					sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
					sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
					startActivity(Intent.createChooser(sharingIntent, getString(R.string.seleccione_gestor)));		
				}
			});
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
