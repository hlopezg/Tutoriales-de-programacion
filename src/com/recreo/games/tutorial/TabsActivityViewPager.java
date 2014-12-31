package com.recreo.games.tutorial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.webkit.WebView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.recreo.games.adapter.TabsAdapterPager;
import com.recreo.games.clases.globales;
import com.recreo.games.database.CrearBaseDeDatos;
import com.recreo.games.tutorial.fragments.Fragment_Introduccion_codigo;
import com.recreo.games.tutorial.fragments.Fragment_TabsActivity;

public class TabsActivityViewPager extends SherlockFragmentActivity{

	ViewPager mViewPager;
	TabsAdapterPager mTabsAdapter;
	
	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;
	
	WebView mWebView;
	
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		mViewPager = new ViewPager(this);
		mViewPager.setId(0x7F04FFF0);

		setContentView(mViewPager);
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);
		
		mWebView = (WebView) findViewById(R.id.webview);
		
		Intent intentRecibe = getIntent();
		Bundle datos = intentRecibe.getExtras();
		
		getSupportActionBar().setTitle(datos.getString("title"));
        getSupportActionBar().setSubtitle(datos.getString("subtitle"));
        setSupportProgressBarIndeterminateVisibility(false);

		mTabsAdapter = new TabsAdapterPager(this, mViewPager);

		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.explicacion)),Fragment_TabsActivity.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.codigo)),Fragment_Introduccion_codigo.class, null);
		
		crearBaseDeDatos = new CrearBaseDeDatos(this, "basededatos_tutorial", null, globales.DB_VERSION);
		db = crearBaseDeDatos.getWritableDatabase();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
    	
        menu.add(1, 1, 0, "Marcar").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }
    
    
    
	 @Override
	    public void onBackPressed() {
		 cerrar();
	    }   

	 public void cerrar()
	{
		String[] args3 = new String[] { String.valueOf(getIntent().getExtras().getInt("id")) };
		Cursor c;
		try
		{
			c = db.rawQuery(
					" SELECT DISTINCT ar.id_Articulo, "
							+ "ar.leido_Articulo, "
							+ "ar.fecha_actualizacion_Articulo "
							+ "FROM Articulo ar " +
							" WHERE ar.id_Articulo=?"
					, args3);

			if (c.moveToFirst())
			{
				ContentValues Articulo = new ContentValues();
				Articulo.put("leido_Articulo", 2);	// 2 = leido

				db.update("Articulo", Articulo,
						"id_Articulo=? ",
						args3);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			finish();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				cerrar();
				return true;
			case 1:
				float posicion = Fragment_TabsActivity.mWebView.getScrollY();

				String[] args3 = new String[] { String.valueOf(getIntent().getExtras().getInt("id")) };
				ContentValues Articulo = new ContentValues();
				Articulo.put("posicion_lectura_Articulo", posicion);

				db.update("Articulo", Articulo,
						"id_Articulo=? ",
						args3);

				Toast.makeText(getApplicationContext(), posicion + "", Toast.LENGTH_LONG).show();
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
   
}
