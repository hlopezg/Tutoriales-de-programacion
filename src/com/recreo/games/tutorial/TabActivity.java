package com.recreo.games.tutorial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.recreo.games.clases.globales;
import com.recreo.games.database.CrearBaseDeDatos;
import com.recreo.games.ejemplo.fragments.tutorial_1;
import com.recreo.games.tutorial.fragments.Fragment_Introduccion;
import com.recreo.games.tutorial.fragments.Fragment_TabsActivity;

public class TabActivity extends SherlockFragmentActivity {
	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int id = getIntent().getExtras().getInt("id");

		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab1 = bar.newTab();
		ActionBar.Tab tab2 = bar.newTab();
		tab1.setText(getString(R.string.explicacion));
		tab1.setTabListener(new MyTabListener());
		bar.addTab(tab1);

		if (id == 5) {
			tab2.setText(getString(R.string.ejemplo));
			tab2.setTabListener(new MyTabListener());
			bar.addTab(tab2);
		}

		crearBaseDeDatos = new CrearBaseDeDatos(this, "basededatos_tutorial",null, globales.DB_VERSION);
		db = crearBaseDeDatos.getWritableDatabase();

		String[] args3 = new String[] { String.valueOf(id) };
		Cursor c;
		try {
			c = db.rawQuery(" SELECT DISTINCT ar.id_Articulo, "
					+ "ar.titulo_Articulo, " + "ar.subtitulo_Articulo "
					+ "FROM Articulo ar " + " WHERE ar.id_Articulo=?", args3);

			if (c.moveToFirst()) {
				bar.setTitle(c.getString(c.getColumnIndex("titulo_Articulo")));
				bar.setSubtitle(c.getString(c
						.getColumnIndex("subtitulo_Articulo")));
			}
			c.close();
		} catch (Exception e) {
			Log.e("TabActivity", e.toString());
		}
	}

	@Override
	public void onBackPressed() {
		cerrar();
	}

	public void cerrar() {
		String[] args3 = new String[] { String.valueOf(getIntent().getExtras()
				.getInt("id")) };
		Cursor c;
		try {
			c = db.rawQuery(" SELECT DISTINCT ar.id_Articulo, "
					+ "ar.leido_Articulo, "
					+ "ar.fecha_actualizacion_Articulo " + "FROM Articulo ar "
					+ " WHERE ar.id_Articulo=?", args3);

			if (c.moveToFirst()) {
				ContentValues Articulo = new ContentValues();
				Articulo.put("leido_Articulo", 2); // 2 = leido

				db.update("Articulo", Articulo, "id_Articulo=? ", args3);
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
			Intent mIntent = new Intent();
			setResult(RESULT_OK, mIntent);
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add(1, 1, 0,
		// "Marcar").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			cerrar();
			return true;
		case 1:
			float posicion = Fragment_TabsActivity.mWebView.getScrollY();

			String[] args3 = new String[] { String.valueOf(getIntent()
					.getExtras().getInt("id")) };
			ContentValues Articulo = new ContentValues();
			Articulo.put("posicion_lectura_Articulo", posicion);

			db.update("Articulo", Articulo, "id_Articulo=? ", args3);

			Toast.makeText(getApplicationContext(), posicion + "",
					Toast.LENGTH_LONG).show();

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class MyTabListener implements ActionBar.TabListener {
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (tab.getPosition() == 0) {
				if (getIntent().getExtras().getInt("id") == 1) {
					Fragment_Introduccion frag = new Fragment_Introduccion();
					ft.replace(android.R.id.content, frag);
				}else {
					Fragment_TabsActivity frag = new Fragment_TabsActivity();
					ft.replace(android.R.id.content, frag);
				}
			} else {
				if (getIntent().getExtras().getInt("id") == 5) {
					tutorial_1 frag = new tutorial_1();
					ft.replace(android.R.id.content, frag);
				} 

			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
	}
}
