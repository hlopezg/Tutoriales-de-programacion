package com.recreo.games.tutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.recreo.games.tutorial.R;
import com.recreo.games.adapter.FragmentAdapter_bienvenida;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
/*import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
*/
public class Bienvenida_tutorial extends SherlockFragmentActivity {

	private FragmentAdapter_bienvenida mAdapter;
	private ViewPager mPager;
	//private PageIndicator mIndicator;
	Button button_comenzar_ahora;

	SharedPreferences prefs;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bienvenida_tutorial);

		button_comenzar_ahora = (Button) findViewById(R.id.button_comenzar_ahora);

		prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
		editor = prefs.edit();
		getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
		if (prefs.getBoolean("saltar_intro", false) == true)
		{
			Intent intent = new Intent(Bienvenida_tutorial.this, InicioActivity.class);
			startActivity(intent);
			finish();
		} else
		{
			
		}
		String[] titles = {  getString(R.string.bienvenido), 
				getString(R.string.contenido), 
				getString(R.string.opciones)
				};
		
		mAdapter = new FragmentAdapter_bienvenida(getSupportFragmentManager(), titles);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		PageIndicator mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

		button_comenzar_ahora.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Bienvenida_tutorial.this, InicioActivity.class);
				startActivity(intent);

				editor.putBoolean("saltar_intro", true);
				editor.commit();

				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}