/*
 * Copyright (C) 2012 Daniel Medina <http://danielme.com>
 * 
 * This file is part of "Android WebView Demo".
 * 
 * "Android WebView Demo" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * "Android WebView Demo" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 3
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl-3.0.html/>
 */

package com.danielme.android.webviewdemo;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.recreo.games.clases.globales;
import com.recreo.games.database.CrearBaseDeDatos;
import com.recreo.games.tutorial.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater; 
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewDemoActivity extends ActionBarActivity {
	private WebView webview;

	private ProgressBar progressBar;

	private EditText urlEditText;

	private List<Link> historyStack;

	private ArrayAdapter<Link> dialogArrayAdapter;

	private ImageView stopButton;

	private ImageView faviconImageView;

	private static final Pattern urlPattern = Pattern
			.compile("^(https?|ftp|file)://(.*?)");

	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;

	Menu menu;
	
	boolean favorito_clickleado = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_demoactivity);
		
		LinearLayout lineaerLayout_web = (LinearLayout) findViewById(R.id.lineaerLayout_web);
		AdView adView;
	    adView = new AdView(this, AdSize.BANNER, "ca-app-pub-2160329755414556/5031531628");
	    lineaerLayout_web.addView(adView);
	    adView.loadAd(new AdRequest());
	    
		crearBaseDeDatos = new CrearBaseDeDatos(this, "basededatos_tutorial", null, globales.DB_VERSION);
		db = crearBaseDeDatos.getWritableDatabase();

		String url_Tutorial = getIntent().getExtras().getString("url_Tutorial");

		getSupportActionBar().setTitle(getIntent().getExtras().getString("titulo"));
		getSupportActionBar().setSubtitle(getIntent().getExtras().getString("autor_Tutorial"));

		// int id_articulo = getIntent().getExtras().getInt("id_articulo");

		historyStack = new LinkedList<Link>();
		webview = (WebView) findViewById(R.id.webkit);
		faviconImageView = (ImageView) findViewById(R.id.favicon);

		urlEditText = (EditText) findViewById(R.id.url);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		stopButton = ((ImageView) findViewById(R.id.stopButton));
		// favicon
		WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).getPath());

		// javascript and zoom
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true);

		if (Build.VERSION.SDK_INT >= 8) {
			webview.getSettings().setPluginState(PluginState.ON);
		} else {
			//webview.getSettings().setPluginsEnabled(true);
		}

		// downloads
		//webview.setDownloadListener(new CustomDownloadListener());

		webview.setWebViewClient(new CustomWebViewClient());

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				progressBar.setProgress(0);
				FrameLayout progressBarLayout = (FrameLayout) findViewById(R.id.progressBarLayout);
				progressBarLayout.setVisibility(View.VISIBLE);
				WebViewDemoActivity.this.setProgress(progress * 1000);

				TextView progressStatus = (TextView) findViewById(R.id.progressStatus);
				progressStatus.setText(progress + " %");
				progressBar.incrementProgressBy(progress);

				if (progress == 100) {
					progressBarLayout.setVisibility(View.GONE);
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				WebViewDemoActivity.this.setTitle(getString(R.string.app_name)
						+ " - " + WebViewDemoActivity.this.webview.getTitle());
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				faviconImageView.setImageBitmap(icon);
				view.getUrl();
				boolean b = false;
				ListIterator<Link> listIterator = historyStack.listIterator();
				while (!b && listIterator.hasNext()) {
					Link link = listIterator.next();
					if (link.getUrl().equals(view.getUrl())) {
						link.setFavicon(icon);
						b = true;
					}
				}
			}

		});

		// http://stackoverflow.com/questions/2083909/android-webview-refusing-user-input
		webview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_UP:
						if (!v.hasFocus()) {
							v.requestFocus();
						}
						break;
				}
				return false;
			}

		});

		// Welcome page loaded from assets directory
		/*
		 * if (Locale.getDefault().getLanguage().equals("es")) {
		 * webview.loadUrl(
		 * "http://danielme.com/2012/05/19/android-webview-incrustar-un-navegador-en-nuestras-apps/"
		 * ); } else { webview.loadUrl(
		 * "http://danielme.com/2012/05/19/android-webview-incrustar-un-navegador-en-nuestras-apps/"
		 * ); }
		 */

		webview.loadUrl(url_Tutorial);

		webview.requestFocus();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.history));
		builder.setPositiveButton(getString(R.string.clear),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						historyStack.clear();
					}
				});

		builder.setNegativeButton(R.string.close, null);

		dialogArrayAdapter = new ArrayAdapter<Link>(this, R.layout.history,
				historyStack) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// holder pattern
				LinkHolder holder = null;
				if (convertView == null) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(R.layout.history, null);
					holder = new LinkHolder();
					holder.setUrl((TextView) convertView
							.findViewById(R.id.textView1));
					holder.setImageView((ImageView) convertView
							.findViewById(R.id.favicon));

					convertView.setTag(holder);
				} else {
					holder = (LinkHolder) convertView.getTag();
				}

				holder.getUrl().setText(historyStack.get(position).getUrl());
				Bitmap favicon = historyStack.get(position).getFavicon();
				if (favicon == null) {
					holder.getImageView().setImageDrawable(
							super.getContext().getResources()
									.getDrawable(R.drawable.favicon_default));
				} else {
					holder.getImageView().setImageBitmap(favicon);
				}

				return convertView;
			}
		};

		builder.setAdapter(dialogArrayAdapter,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						webview.loadUrl(historyStack.get(item).getUrl());
						stopButton.setEnabled(true);
						stopButton.setBackgroundResource(R.drawable.stop);
					}

				});

		return builder.create();
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		dialogArrayAdapter.notifyDataSetChanged();
		super.onPrepareDialog(id, dialog);
	}

	// back button
	@Override
	public void onBackPressed() {
		if (webview.canGoBack()) {
			webview.goBack();
		} else {
			if(favorito_clickleado)
			{
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
			}
			finish();
		}
	}

	class CustomWebViewClient extends WebViewClient {
		// the current WebView will handle the url
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.endsWith(".mp3") || url.endsWith(".aac")) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(url), "audio/mpeg");
				startActivity(intent);
				return true;
			}
			return false;
		}

		// history and navigation buttons
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (checkConnectivity()) {
				// resets favicon
				WebViewDemoActivity.this.faviconImageView
						.setImageDrawable(WebViewDemoActivity.this
								.getResources().getDrawable(
										R.drawable.favicon_default));
				// shows the current url
				WebViewDemoActivity.this.urlEditText.setText(url);

				// only one occurrence
				boolean b = false;
				ListIterator<Link> listIterator = historyStack.listIterator();
				while (listIterator.hasNext() && !b) {
					if (listIterator.next().getUrl().equals(url)) {
						b = true;
						listIterator.remove();
					}
				}
				Link link = new Link(url, favicon);
				historyStack.add(0, link);

				stopButton.setEnabled(true);
				stopButton.setBackgroundResource(R.drawable.stop);
				updateButtons();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			stopButton.setEnabled(false);
			stopButton.setBackgroundResource(R.drawable.stop_press);
			updateButtons();
		}

		// handles unrecoverable errors
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					WebViewDemoActivity.this);
			builder.setMessage(description)
					.setPositiveButton((R.string.ok), null)
					.setTitle("onReceivedError");
			builder.show();
		}

	}

	public void go(View view) {
		// hides the keyboard
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(
				urlEditText.getWindowToken(), 0);

		if (checkConnectivity()) {
			stopButton.setEnabled(true);
			stopButton.setBackgroundResource(R.drawable.stop);

			// http protocol by default
			if (!urlPattern.matcher(urlEditText.getText().toString()).matches()) {
				urlEditText.setText("http://"
						+ urlEditText.getText().toString());
			}
			webview.loadUrl(urlEditText.getText().toString());
		}
	}

	public void back(View view) {
		if (checkConnectivity()) {
			webview.goBack();
		}
	}

	public void forward(View view) {
		if (checkConnectivity()) {
			webview.goForward();
		}
	}

	public void stop(View view) {
		webview.stopLoading();
		Toast.makeText(this, getString(R.string.stopping), Toast.LENGTH_LONG)
				.show();
	}

	private void updateButtons() {
		ImageView backButton = (ImageView) WebViewDemoActivity.this
				.findViewById(R.id.backButton);

		if (webview.canGoBack()) {
			backButton.setEnabled(true);
			// backButton.setSelected(true);
			backButton.setBackgroundResource(R.drawable.back);
		} else {
			backButton.setEnabled(false);
			// backButton.setSelected(false);
			backButton.setBackgroundResource(R.drawable.back_press);
		}

		ImageView forwardButton = (ImageView) WebViewDemoActivity.this
				.findViewById(R.id.forwardButton);

		if (webview.canGoForward()) {
			forwardButton.setEnabled(true);
			// forwardButton.setSelected(true);
			forwardButton.setBackgroundResource(R.drawable.forward);
		} else {
			forwardButton.setEnabled(false);
			// forwardButton.setSelected(false);
			forwardButton.setBackgroundResource(R.drawable.forward_press);
		}
	}

	
	/**
	 * Checks networking status.
	 */
	private boolean checkConnectivity() {
		boolean enabled = true;

		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if ((info == null || !info.isConnected() || !info.isAvailable())) {
			enabled = false;
			Builder builder = new Builder(this);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(getString(R.string.noconnection));
			builder.setCancelable(false);
			builder.setNeutralButton(R.string.ok, null);
			builder.setTitle(getString(R.string.error));
			builder.create().show();
		}
		return enabled;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 2, 1, "Historial").setTitle("Historial").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(1, 3, 1, "Navegador").setTitle("Cambiar navegador").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		String[] args = new String[] { String.valueOf(getIntent().getExtras()
				.getInt("id_articulo")) };

		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);

			Cursor c = db.rawQuery("SELECT DISTINCT es_favorito_Tutorial "
					+ " FROM favorito_Tutorial " + " WHERE id_Tutorial_FK=?",
					args);

			int favorito;
			if (c.moveToFirst()) {
				favorito = c.getInt(c.getColumnIndex("es_favorito_Tutorial"));
				if (favorito == 0)
					menu.add(1, 1, 0, "Favorito")
							.setIcon(R.drawable.star_empty)
							.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				else
					menu.add(1, 1, 0, "Favorito")
							.setIcon(R.drawable.star_selected)
							.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			} else
				menu.add(1, 1, 0, "Favorito").setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		} catch (Exception ex) {
			ex.printStackTrace();
			db.execSQL("INSERT INTO favorito_Tutorial(id_Tutorial_FK) values("
					+ getIntent().getExtras().getInt("id_articulo") + ")");
			menu.add(1, 1, 0, "Favorito").setIcon(R.drawable.star_empty)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} finally {
			this.menu = menu;
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				try {

					String[] args = new String[] { String.valueOf(getIntent()
							.getExtras().getInt("id_articulo")) };

					Cursor c;
					c = db.rawQuery("SELECT DISTINCT es_favorito_Tutorial "
							+ " FROM favorito_Tutorial "
							+ " WHERE id_Tutorial_FK=?", args);

					int favorito;
					if (c.moveToFirst()) {
						favorito = c.getInt(c
								.getColumnIndex("es_favorito_Tutorial"));
						ContentValues valores = new ContentValues();
						if (favorito == 0) {
							valores.put("es_favorito_Tutorial", 1);
							db.update("favorito_Tutorial", valores,
									"id_Tutorial_FK=?", args);

							Toast.makeText(getApplicationContext(), "Ahora es favorito", Toast.LENGTH_LONG).show();
							menu.findItem(1).setIcon(R.drawable.star_selected);
						} else {
							valores.put("es_favorito_Tutorial", 0);
							db.update("favorito_Tutorial", valores,
									"id_Tutorial_FK=?", args);

							Toast.makeText(getApplicationContext(),
									"Ya no es favorito", Toast.LENGTH_LONG).show();
							menu.findItem(1).setIcon(R.drawable.star_empty);
						}
					} else
					{
						db.execSQL("INSERT INTO favorito_Tutorial(id_Tutorial_FK,es_favorito_Tutorial) values("
								+ getIntent().getExtras().getInt("id_articulo")
								+ ", " + 1 + ")");
						menu.findItem(1).setIcon(R.drawable.star_selected);
						Toast.makeText(getApplicationContext(), "Ahora es favorito", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							"Ocurrio un error y no se pudo agregar a favorito",
							Toast.LENGTH_LONG).show();
				}
				return true;
			case 2:
				showDialog(0);
				return true;
			case 3:
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(urlEditText.getText().toString()));
				startActivity(i);
				return true;
			case android.R.id.home:
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
