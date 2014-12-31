package com.recreo.games.tutorial;

import java.util.ArrayList;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.danielme.android.webviewdemo.WebViewDemoActivity;
import com.recreo.games.tutorial.R;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeIntents;
import com.recreo.games.adapter.Adaptador;
import com.recreo.games.adapter.Adaptador_GPlus;
import com.recreo.games.adapter.ImageAdapter;
import com.recreo.games.clases.Datos_Spinner;
import com.recreo.games.clases.ImagenVisible;
import com.recreo.games.clases.globales;
import com.recreo.games.controlador.Crear_datos_tablas;
import com.recreo.games.database.CrearBaseDeDatos;

public class InicioActivity extends SherlockListActivity implements AdListener {
	 
	ArrayList<Datos_Spinner> listaDatos;
	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;
	ArrayList<Datos_Spinner> items;
	String ultimo_nombreTutorial;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ListView mDrawerList;
	private String[] mPlanetTitles;
	
	private SpeedScrollListener listener;

	Adaptador_GPlus especial_adapter;
	Adaptador especial_adapter_support;
	SharedPreferences prefs;
	InterstitialAd interstitialAd;
	
	private ArrayList<ImagenVisible> lenguajes_visible;
//	private ArrayList<String> nombre_lenguaje;

	private Integer[] mThumbIds = 
		{ 
			R.drawable.android,
			R.drawable.asp_net,
			R.drawable.c, 
			R.drawable.c_shatr,
			R.drawable.c_plusplus,
			R.drawable.corona,
			R.drawable.dfd,
			R.drawable.django, 
			R.drawable.html5, 
			R.drawable.ios,
			R.drawable.java, 
			R.drawable.javascript, 
			R.drawable.jquery_mobile,
			R.drawable.perl, 
			R.drawable.php, 
			R.drawable.python,
			R.drawable.ruby_on_rails, 
			R.drawable.unity, 
			R.drawable.visual_net,
			R.drawable.visual_studio,
			R.drawable.windows_phone_8
			};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try // lo hago en un try por que cuando refresco la pagina se cae al leer esto
		{
			requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			requestWindowFeature(Window.FEATURE_PROGRESS);
		} catch (Exception e)
		{

		}
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			setSupportProgressBarIndeterminateVisibility(true);

			prefs = getSharedPreferences("Preferencias",Context.MODE_PRIVATE);	
			 
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		// FrameLayout layout_filtro = (FrameLayout) findViewById(R.id.layout_filtro);
		try
		{			
			//nombre_lenguaje = new ArrayList<String>();
			lenguajes_visible = new ArrayList<ImagenVisible>();
			
			mTitle = mDrawerTitle = getTitle();
			mPlanetTitles = getResources().getStringArray(R.array.filtros);
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.left_drawer);
			mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

			// creo los datos de la tabla para el filtro
			Crear_datos_tablas crear_datos_tablas = new Crear_datos_tablas(InicioActivity.this);

			mDrawerList.setAdapter(crear_datos_tablas.tabla_filtro());

			mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
			
			
		    
			// iconos por del ejemplo
			mDrawerToggle = new ActionBarDrawerToggle(
					this, /* host Activity */
					mDrawerLayout, /* DrawerLayout object */
					R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
					R.string.drawer_open, /* "open drawer" description for accessibility */
					R.string.drawer_close /* "close drawer" description for accessibility */
					) {
						/** Called when a drawer has settled in a completely closed state. */
						public void onDrawerClosed(View view) {
							getSupportActionBar().setTitle(mTitle);
							// invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
						}

						/** Called when a drawer has settled in a completely open state. */
						public void onDrawerOpened(View drawerView) {
							getSupportActionBar().setTitle(mDrawerTitle);
							// invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
						}
					};

			// Set the drawer toggle as the DrawerListener
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			
			
			interstitialAd = new InterstitialAd(this, "ca-app-pub-6602434756482671/6592610948");
		    AdRequest adRequest = new AdRequest();
		    adRequest.addTestDevice("B05C6CD7C250967CF6B66539E1412E17");

		    interstitialAd.setAdListener(this);

		    interstitialAd.loadAd(adRequest);		
		    
		  //para cargar los lenguajes ocultos y visibles
			crearBaseDeDatos = new CrearBaseDeDatos(InicioActivity.this, "basededatos_tutorial", null, globales.DB_VERSION);
			db = crearBaseDeDatos.getWritableDatabase();
			
			Cursor c;
			c = db.rawQuery(
					" SELECT DISTINCT visible, "
							+ "nombre_lenguaje "
							+ "FROM lenguajes_visibles "
					, null);

		    if(c.moveToFirst())
		    {
		    	int j = 0;
		    	do
		    	{
		    		lenguajes_visible.add(new ImagenVisible(mThumbIds[j],c.getString(c.getColumnIndex("nombre_lenguaje")),c.getInt(c.getColumnIndex("visible"))));
		    		j++;
		    	}while(c.moveToNext());
		    }
		    c.close();
		    
		    if(prefs.getInt("espanol",0) == 0 && prefs.getInt("ingles",0) ==0)
			{
				cambiarIdioma();
				elegir_idioma();
			}else
				new cargarDatos().execute();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}		
	}
	
	public void onDestroy() {
	       interstitialAd.stopLoading();
	       super.onDestroy();
	}
	
	 public void onReceiveAd(Ad ad) {
	       if (ad == interstitialAd) {
	         interstitialAd.show();
	       }
     }

	class cargarDatos extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			try
			{
				items = new ArrayList<Datos_Spinner>();

				String agregar_a_la_query = obtenerQueryIdioma(false);
				
				Cursor c;
				
				c = db.rawQuery(
						" SELECT DISTINCT t.id_Tutorial, "
								+ "t.titulo_Turorial, "
								+ "t.subtitulo_Tutorial, "
								+ "t.autor_Tutorial, "
								+ "t.disponiblidad_Tutorial, "
								+ "t.fecha_Actualizacion_Tutorial, "
								+ "t.idioma_Tutorial, "
								+ "t.visible, "
								+ "ft.es_favorito_Tutorial, "
								+ "t.url_Tutorial "
								+ "FROM Tutorial t"
								+ " LEFT JOIN favorito_Tutorial ft on ft.id_Tutorial_FK = t.id_Tutorial "
								+ agregar_a_la_query
								+ " ORDER BY upper(titulo_Turorial),id_Tutorial "
						, null);

				listener = new SpeedScrollListener();
				getListView().setOnScrollListener(listener);

				do_while_cursor(c);

			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			// getListView().setFastScrollEnabled(true);
			// getListView().setScrollingCacheEnabled(true);
			
			if(Build.VERSION.SDK_INT > 13) 
				setListAdapter(especial_adapter);
			else
				setListAdapter(especial_adapter_support);
			
			setSupportProgressBarIndeterminateVisibility(false);
			// getListView().setTextFilterEnabled(true);

			getListView().setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					try
					{
						Intent intent = null;
						Bundle bundle = new Bundle();
	
						bundle.putInt("id_articulo", items.get(arg2).id);
						bundle.putString("url_Tutorial", items.get(arg2).url);
	
						bundle.putString("titulo", items.get(arg2).nombre);
						bundle.putString("sub_titulo", items.get(arg2).sub_titulo);
						bundle.putString("autor_Tutorial", items.get(arg2).autor);
	
						if (items.get(arg2).url.equals("") && items.get(arg2).id != 0)
						{
							intent = new Intent(arg1.getContext(), MainActivity.class);
						}
	
						else
						{
							if (items.get(arg2).disponiblidad == 5)
							{
								intent = YouTubeIntents.createOpenPlaylistIntent(arg1.getContext(), items.get(arg2).url);
							}
							else if (items.get(arg2).disponiblidad == 3 || items.get(arg2).disponiblidad == 4)
							{
								intent = new Intent(arg1.getContext(), pdfActivity.class);
							}
							else
								intent = new Intent(arg1.getContext(), WebViewDemoActivity.class);
						}
						
	
						try
						{
							if (items.get(arg2).id != 0)
							{
								intent.putExtras(bundle);
								startActivityForResult(intent, 0);
							}
	
						} catch (Exception ex2)
						{
							if (items.get(arg2).disponiblidad == 5) // en algunos dispositivos tiene problemas el YouTubeIntents, en este caso lanzamos un intent implisito
							{
								intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=PLPV2KyIb3jR7F_B4p8X3YwHPaExh0R9Kk&list=playlist-id&feature=plpp_play_all"));
								startActivityForResult(intent, 0);
							}
						}
					
					}catch(Exception ex)
					{
						Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
						ex.toString();
					}
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				// this.onCreate(null);
				new cargarDatos().execute();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, getString(R.string.contactar_con_autor)).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(1, 2, 0, getString(R.string.acerca_de)).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(1, 3, 0, getString(R.string.filtro)).setIcon(R.drawable.filter).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(1, 4, 0, getString(R.string.cambiar_idioma_tutoriales)).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(1, 5, 0, getString(R.string.elegir_lenguajes)).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		// menu.add(1, 3, 0, "Buscar").setActionView(R.layout.layout_search_edittext).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		getSupportMenuInflater().inflate(R.menu.search, menu);

		MenuItem menuSearch = menu.findItem(R.id.menu_search);

		EditText searchText = (EditText) menuSearch.getActionView().findViewById(R.id.search_edittext);

		searchText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filtrar_edittext(s.toString());
			}
		});

		searchText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
					return filtrar_edittext(arg0.getText().toString());
				}
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
		// return true;
	}

	boolean filtrar_edittext(String arg0) {
		try
		{
			items = new ArrayList<Datos_Spinner>();
			String agregar_a_la_query = obtenerQueryIdioma(true);
			Cursor c;
			c = db.rawQuery(
					" SELECT DISTINCT  t.id_Tutorial, "
							+ "t.titulo_Turorial, "
							+ "t.subtitulo_Tutorial, "
							+ "t.autor_Tutorial, "
							+ "t.disponiblidad_Tutorial, "
							+ "t.fecha_Actualizacion_Tutorial, "
							+ "t.idioma_Tutorial, "
							+ "ft.es_favorito_Tutorial, "
							+ "t.visible, "
							+ "t.url_Tutorial "
							+ "FROM Tutorial t"
							+ " LEFT JOIN favorito_Tutorial ft on ft.id_Tutorial_FK = t.id_Tutorial "
							+ " WHERE titulo_Turorial LIKE '%" + arg0
							+ "%'" + " OR autor_Tutorial LIKE '%"
							+ arg0 + "%'"
							+ " and " + agregar_a_la_query
							+ " ORDER BY titulo_Turorial,autor_Tutorial"
					, null);

			do_while_cursor(c);
			
			if(Build.VERSION.SDK_INT > 13) 
				setListAdapter(especial_adapter);
			else
				setListAdapter(especial_adapter_support);
			
			setSupportProgressBarIndeterminateVisibility(false);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mDrawerToggle.onDrawerOpened(mDrawerLayout);
		switch (item.getItemId()) {
			case 1:
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("message/rfc822");
				sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hector.lopez.gatica@gmail.com" });
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
				sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(sharingIntent, getString(R.string.seleccione_gestor)));
				return true;
			case 2:
				Intent intent = new Intent(this, TabsAbout.class);
				startActivity(intent);
				return true;
			case 3:
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
				return true;
			case 4:
				cambiarIdioma();
				return true;
			case 5:
				elegir_idioma();
				return true;
			case android.R.id.home:
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public boolean onQueryTextChange(String newText) {
		// mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// mStatusView.setText("Query = " + query + " : submitted");
		return false;
	}

	public boolean onClose() {
		// mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position != 0)
			{

				mDrawerList.setItemChecked(position, true);
				setTitle(mPlanetTitles[position - 1]); // -1 por que el primer item es un texto
				mDrawerLayout.closeDrawer(mDrawerList);

				try
				{
					setSupportProgressBarIndeterminateVisibility(true);

					items = new ArrayList<Datos_Spinner>();
					
					String query = " SELECT DISTINCT t.id_Tutorial, "
							+ "t.titulo_Turorial, "
							+ "t.subtitulo_Tutorial, "
							+ "t.autor_Tutorial, "
							+ "t.disponiblidad_Tutorial, "
							+ "t.fecha_Actualizacion_Tutorial, "
							+ "t.idioma_Tutorial, "
							+ "t.visible, "
							+ "ft.es_favorito_Tutorial, "
							+ "t.url_Tutorial "
							+ "FROM Tutorial t"
							+ " LEFT JOIN favorito_Tutorial ft on ft.id_Tutorial_FK = t.id_Tutorial ";

					if (position < 6) // si NO seleccione TODOS
						query = query + " WHERE t.disponiblidad_Tutorial = " + position;
					else if (position == 7)
						query = query + " WHERE ft.es_favorito_Tutorial = " + 1;
					else if (position == 8)
					{
						elegir_idioma();
					}
					if (position != 8)
					{
						query = query + " and ";
						String agregar_a_la_query = obtenerQueryIdioma(true);
						query = query + agregar_a_la_query;
						
						Cursor c;
						c = db.rawQuery(
								query + " ORDER BY upper(titulo_Turorial),id_Tutorial "
								, null);
	
						do_while_cursor(c);
						
						if(Build.VERSION.SDK_INT > 13)
							setListAdapter(especial_adapter);
						else
							setListAdapter(especial_adapter_support);
						
					setSupportProgressBarIndeterminateVisibility(false);
					}
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	// When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void do_while_cursor(Cursor c)
	{
		if (c.moveToFirst())
		{
			do
			{
				//if(lenguajes_visible.get(index))
				String nombre_tutorial = c.getString(c.getColumnIndex("titulo_Turorial"));
				final int size = lenguajes_visible.size();
				boolean oculto = false;
				for(int i = 0; i < size; i++)
				{
					if(lenguajes_visible.get(i).nombre_lenguaje.equals(nombre_tutorial))
					{
						if(lenguajes_visible.get(i).visible == 0)
							oculto = true;
						break;
					}
				}
				nombre_tutorial = nombre_tutorial.toUpperCase();
				if(oculto == false)
				{
					if (!nombre_tutorial.equals(ultimo_nombreTutorial))
					{
						ultimo_nombreTutorial = c.getString(c.getColumnIndex("titulo_Turorial")).toUpperCase();
						items.add(new Datos_Spinner(0, ultimo_nombreTutorial + "", "", "", "", 0, 0, "", "", 2,1));
						//nombre_lenguaje.add(ultimo_nombreTutorial);
					}
					if(c.getInt(c.getColumnIndex("visible")) == 1)
					{
						items.add(new Datos_Spinner(c.getInt(c.getColumnIndex("id_Tutorial")),
							c.getString(c.getColumnIndex("titulo_Turorial")),
							c.getString(c.getColumnIndex("subtitulo_Tutorial")),
							c.getString(c.getColumnIndex("autor_Tutorial")),
							c.getString(c.getColumnIndex("fecha_Actualizacion_Tutorial")),
							4,
							c.getInt(c.getColumnIndex("disponiblidad_Tutorial")),
							c.getString(c.getColumnIndex("idioma_Tutorial")),
							c.getString(c.getColumnIndex("url_Tutorial")),
							c.getInt(c.getColumnIndex("es_favorito_Tutorial")),
							c.getInt(c.getColumnIndex("visible"))		
								));
					}
				}
				//+ "t.visible, "
			} while (c.moveToNext());
			//ultima_primeraletra = 'z';
		}
		c.close();
		if(Build.VERSION.SDK_INT > 13) {
			especial_adapter = new Adaptador_GPlus(InicioActivity.this, getApplicationContext(), items, listener);
		}else
			especial_adapter_support = new Adaptador(InicioActivity.this, getApplicationContext(), items);
	}
	
	void cambiarIdioma()
	{
		int espanol = prefs.getInt("espanol",1);
		int ingles = prefs.getInt("ingles",1);
		
		final Dialog dialog = new Dialog(InicioActivity.this);
		dialog.setContentView(R.layout.seleccionar_idiomas);
		dialog.setTitle("Configuración");
		
		final CheckBox checkBox_ingles = (CheckBox) dialog.findViewById(R.id.checkBox_ingles);
		final CheckBox checkBox_espanol = (CheckBox) dialog.findViewById(R.id.checkBox_espanol);
		
		if(espanol == 1)
			checkBox_espanol.setChecked(true);
		else
			checkBox_espanol.setChecked(false);
		if(ingles == 1)
			checkBox_ingles.setChecked(true);
		else
			checkBox_ingles.setChecked(false);
			
		Button button_aceptar = (Button) dialog.findViewById(R.id.button_aceptar);
		button_aceptar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = prefs.edit();
				
				if(!checkBox_espanol.isChecked() && !checkBox_ingles.isChecked())
				{
					Toast.makeText(v.getContext(), getString(R.string.debe_seleccionar_un_idioma), Toast.LENGTH_LONG).show();
				}else
				{
					if(checkBox_espanol.isChecked())
					{
						editor.putInt("espanol", 1);
					}else{
						editor.putInt("espanol", 0);
					}
					if(checkBox_ingles.isChecked())
					{
						editor.putInt("ingles", 1);
					}else{
						editor.putInt("ingles", 0);
					}
					
					editor.commit();
					dialog.dismiss();
					new cargarDatos().execute();
				}
			}
		});
		dialog.show();
	}
	
	String obtenerQueryIdioma(boolean viene_con_where)
	{
		String agregar_a_la_query = "";
		if(prefs.getInt("espanol",0) == 1 || prefs.getInt("ingles",0) == 1)
		{
			
			if(prefs.getInt("espanol",0) == 1)
			{
				agregar_a_la_query = agregar_a_la_query + " idioma_Tutorial = 'Español' ";
			}
			if(prefs.getInt("espanol",0) == 1 && prefs.getInt("ingles",0) ==1)
			{
				agregar_a_la_query = agregar_a_la_query + " or ";
			}
			if(prefs.getInt("ingles",0) == 1)
			{
				agregar_a_la_query = agregar_a_la_query + " idioma_Tutorial = 'English' ";
			}
			
			agregar_a_la_query = "(" + agregar_a_la_query + ")";
			if(!viene_con_where)
				agregar_a_la_query = "WHERE " + agregar_a_la_query;
		}
		return agregar_a_la_query;
	}
	
	void elegir_idioma()
	{
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.layout_gridlayout_lenguaje);
		dialog.setTitle(getString(R.string.elegir_lenguajes));
		dialog.setCancelable(false);

		GridView gridview = (GridView) dialog.findViewById(R.id.gridview);
		Button button_aceptar_lenguajes = (Button) dialog.findViewById(R.id.button_aceptar_lenguajes);
		//Button button_cancelar_lenguajes = (Button) dialog.findViewById(R.id.button_cancelar_lenguajes);
		
		button_aceptar_lenguajes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final int size = lenguajes_visible.size();
				for (int i = 0; i< size; i++)
				{
					if(lenguajes_visible.get(i).visible == 1)
					{
						String[] args = {lenguajes_visible.get(i).nombre_lenguaje};
						ContentValues content = new ContentValues();
				    	content.put("visible", 1);
				    	db.update("lenguajes_visibles", content, "nombre_lenguaje=?",args);
					}else
					{
						String[] args = {lenguajes_visible.get(i).nombre_lenguaje};
						ContentValues content = new ContentValues();
						content.put("visible", 0);
				    	db.update("lenguajes_visibles", content, "nombre_lenguaje=?",args);
					}
				}
				dialog.dismiss();
				new cargarDatos().execute();
			}
		});
		
	/*	button_cancelar_lenguajes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});*/
	 
        gridview.setAdapter(new ImageAdapter(InicioActivity.this,lenguajes_visible));
	 
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
            	try
            	{
	                View child = ((ViewGroup)v).getChildAt(0);		//linearLayout
	                for(int i=0; i<((ViewGroup)child).getChildCount(); ++i) {
					    ImageView nextChild = (ImageView) ((ViewGroup)child).getChildAt(i);
					    if(nextChild.getTag().equals("check"))
					    {
					    	if(lenguajes_visible.get(position).visible == 1)
					    	{
					    		int cantidad_seleccionado = 0;
					    		for(int j = 0; j < lenguajes_visible.size(); j++)
					    		{
					    			if(lenguajes_visible.get(position).visible == 1)
					    				cantidad_seleccionado++;
					    		}
					    		if(cantidad_seleccionado > 1)
					    		{
						    		lenguajes_visible.get(position).visible = 0;
						    		//v.setBackgroundColor(0);
						    		child.setBackgroundColor(Color.parseColor("#C1C1C1"));
						    		nextChild.setImageResource(R.drawable.uncheck);
					    		}
					    	}else
					    	{
						    		lenguajes_visible.get(position).visible = 1;
						    		child.setBackgroundColor(Color.parseColor("#A2FF96"));
						    		nextChild.setImageResource(R.drawable.check);
					    	}
					    }
					}
            	}catch(Exception ex)
            	{
            		Toast.makeText(InicioActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
            		ex.toString();
            	}
            }
        });

		dialog.show();
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
}
