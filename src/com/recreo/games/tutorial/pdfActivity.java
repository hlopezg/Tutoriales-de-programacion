package com.recreo.games.tutorial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.danielme.android.webviewdemo.WebViewDemoActivity;
import com.recreo.games.tutorial.R;
import com.recreo.games.clases.globales;
import com.recreo.games.database.CrearBaseDeDatos;

public class pdfActivity extends SherlockActivity{

	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;
	
	String url;
	
	Menu menu;
	
	TextView textView_nombre_pdf, textView_autor_pdf;
	Button button_abrir_pdf;
	String directorio;
	
	boolean favorito_clickleado = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.pdfactivity);
			
		setSupportProgressBarIndeterminateVisibility(true);
	
		crearBaseDeDatos = new CrearBaseDeDatos(this, "basededatos_tutorial", null, globales.DB_VERSION);
		db = crearBaseDeDatos.getWritableDatabase();
		
		textView_nombre_pdf = (TextView)findViewById(R.id.textView_nombre_pdf);
		textView_autor_pdf = (TextView)findViewById(R.id.textView_autor_pdf);
		button_abrir_pdf = (Button)findViewById(R.id.button_abrir_pdf);
		
		directorio = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdf";
		
		button_abrir_pdf.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				File file = new File(directorio, url.substring(url.lastIndexOf("/")));
				if (file.exists()) {
					String[] args = new String[] { String.valueOf(getIntent().getExtras().getInt("id_articulo")) };
					ContentValues valores = new ContentValues();
					valores.put("disponiblidad_Tutorial", 4);
					db.update("Tutorial", valores, "id_Tutorial=?", args);

					abrir_pdf(file);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(pdfActivity.this);

					builder.setTitle(getString(R.string.download));
					builder.setMessage(getString(R.string.question));
					builder.setCancelable(false).setPositiveButton((R.string.ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									new DownloadAsyncTask().execute(url);
								}

							})
						.setNegativeButton((R.string.cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
					builder.create().show();
				}
			}
		});
		
		try {
			url = getIntent().getExtras().getString("url_Tutorial");
			textView_nombre_pdf.setText(getIntent().getExtras().getString("titulo"));
			textView_autor_pdf.setText(getString(R.string.autor) + ": " + getIntent().getExtras().getString("autor_Tutorial"));
			File file = new File(directorio, url.substring(url.lastIndexOf("/")));
			if (file.exists()) {
				button_abrir_pdf.setText(getString(R.string.pdf_abrir));
				setSupportProgressBarIndeterminateVisibility(false);
			}else
			{
				button_abrir_pdf.setText(getString(R.string.pdf_descargar));
				new pesoPDF().execute(url);
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

		class DownloadAsyncTask extends AsyncTask<String, Float, String> {
			File file;
			ProgressDialog dialog = new ProgressDialog(pdfActivity.this);
			
			
			 protected void onPreExecute() {
			        this.dialog.setMessage(getString(R.string.descargando_archivo));
			        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			        dialog.setProgress(0);
			        dialog.setMax(100);
			        this.dialog.show();
			    }

			@Override
			protected String doInBackground(String... arg0) {
				String result = "";
				String url = arg0[0];

				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					InputStream inputStream = null;
					try {
						HttpResponse httpResponse = httpClient.execute(httpGet);

						BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpResponse.getEntity());

						inputStream = bufferedHttpEntity.getContent();

						String fileName = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdf";
						File directory = new File(fileName);
						file = new File(directory, url.substring(url.lastIndexOf("/")));
						directory.mkdirs();

						// commons-io, I miss you :(
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						byte[] buffer = new byte[1024];
						int len = 0;
						int total = inputStream.available();
						while (inputStream.available() > 0 && (len = inputStream.read(buffer)) != -1) {
							publishProgress((100*(total-inputStream.available()))/(float)total);
							byteArrayOutputStream.write(buffer, 0, len);
							
						}

						fileOutputStream.write(byteArrayOutputStream.toByteArray());
						fileOutputStream.flush();
						fileOutputStream.close();

						result = getString(R.string.result) + file.getAbsolutePath();
					} catch (Exception ex) {
						Log.e(pdfActivity.class.toString(),ex.getMessage(), ex);
						result = ex.getClass().getSimpleName() + " " + ex.getMessage();
					} finally {
						if (inputStream != null) {
							try {
								inputStream.close();
							} catch (IOException ex) {
								Log.e(WebViewDemoActivity.class.toString(),ex.getMessage(), ex);
								result = ex.getClass().getSimpleName() + " " + ex.getMessage();
							}
						}
					}
				} else {
					result = getString(R.string.nosd);
				}

				return result;
			}
			
			protected void onProgressUpdate (Float... valores) {
	              int p = Math.round(valores[0]);
	              dialog.setProgress(p);
	          }

			@Override
			protected void onPostExecute(String result) {
				if (dialog.isShowing()) {
		            dialog.dismiss();
		        }
				
				String[] args = new String[] { String.valueOf(getIntent()
						.getExtras().getInt("id_articulo")) };
				ContentValues valores = new ContentValues();
				valores.put("disponiblidad_Tutorial", 4);
				db.update("Tutorial", valores, "id_Tutorial=?", args);

				abrir_pdf(file);
			}
		}
		
		 class pesoPDF extends AsyncTask<String, Void, String> {
		        protected String doInBackground(String... urls) {
		        	 String contentLengthStr;
		            try {
						final URL uri=new URL(url);
						URLConnection ucon;
					
						  ucon=uri.openConnection();
						  ucon.connect();
						  contentLengthStr=ucon.getHeaderField("content-length");
						  
		            } catch (Exception e) {
		                return null;
		            }
		            return contentLengthStr;
		        }

		        protected void onPostExecute(String result) {
		        	try
		        	{
		        		Float peso = Float.parseFloat(result);
			        	peso = peso/(1024*1024);
			            DecimalFormat form = new DecimalFormat("0.00"); 
			        	button_abrir_pdf.setText(button_abrir_pdf.getText().toString() + " (" +form.format(peso) + "MB )");
		        	}catch(Exception e)
		        	{
		        		
		        	}finally{
		        		setSupportProgressBarIndeterminateVisibility(false);
		        	}
		        }
		     }

		private void abrir_pdf(File file)
		{
			try
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
			}catch(ActivityNotFoundException ex)
			{
				final Dialog dialog = new Dialog(pdfActivity.this);
				dialog.setContentView(R.layout.pdf_recomendar);
				dialog.setTitle(getString(R.string.necesita_lector_pdf));
	 
				Button button_pdf_buscar = (Button) dialog.findViewById(R.id.button_pdf_buscar);
				button_pdf_buscar.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://search?q=<pdf>"));
						goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
						startActivity(goToMarket);					
					}
				});
				
				Button button_pdf_adobe = (Button) dialog.findViewById(R.id.button_pdf_adobe);
				button_pdf_adobe.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try
						{
							Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader"));
							marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(marketIntent);	
						}catch(Exception e)	//en caso de que la aplicación ya no exista o cambio de nombre
						{
							Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://search?q=<Adobe Reader>"));
							goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(goToMarket);	
						}
					}
				});
				
				Button button_pdf_viewer_for_mobile = (Button) dialog.findViewById(R.id.button_pdf_viewer_for_mobile);
				button_pdf_viewer_for_mobile.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try
						{
							Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.allThatViewer"));
							marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(marketIntent);
						}catch(Exception e)
						{
							Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://search?q=<Pdf viewer for mobile>"));
							goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(goToMarket);	
						}
					}
				});
	 
				dialog.show();
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}		
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			menu.add(1, 2, 0, getString(R.string.contactar_con_autor)).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			menu.add(1, 3, 0, getString(R.string.acerca_de)).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			String[] args = new String[] { String.valueOf(getIntent().getExtras().getInt("id_articulo")) };
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			try {
				Cursor c = db.rawQuery("SELECT DISTINCT es_favorito_Tutorial "
						+ " FROM favorito_Tutorial " + " WHERE id_Tutorial_FK=?", args);

				int favorito;
				if (c.moveToFirst()) {
					favorito = c.getInt(c.getColumnIndex("es_favorito_Tutorial"));
					if (favorito == 0)
						menu.add(1, 1, 0, getString(R.string.favorito)).setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
					else
						menu.add(1, 1, 0, getString(R.string.favorito)).setIcon(R.drawable.star_selected).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				} else
					menu.add(1, 1, 0, getString(R.string.favorito)).setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

			} catch (Exception ex) {
				ex.printStackTrace();
				db.execSQL("INSERT INTO favorito_Tutorial(id_Tutorial_FK) values(" + getIntent().getExtras().getInt("id_articulo") + ")");
				menu.add(1, 1, 0, getString(R.string.favorito)).setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
					favorito_clickleado= true;
					String[] args = new String[] { String.valueOf(getIntent().getExtras().getInt("id_articulo")) };

					Cursor c;
					c = db.rawQuery("SELECT DISTINCT es_favorito_Tutorial "
							+ " FROM favorito_Tutorial "
							+ " WHERE id_Tutorial_FK=?", args);

					int favorito;
					if (c.moveToFirst()) {
						favorito = c.getInt(c.getColumnIndex("es_favorito_Tutorial"));
						ContentValues valores = new ContentValues();
						if (favorito == 0) {
							valores.put("es_favorito_Tutorial", 1);
							db.update("favorito_Tutorial", valores,"id_Tutorial_FK=?", args);

							Toast.makeText(getApplicationContext(),getString(R.string.ahora_es_favorito), Toast.LENGTH_LONG).show();
							menu.findItem(1).setIcon(R.drawable.star_selected);
						} else {
							valores.put("es_favorito_Tutorial", 0);
							db.update("favorito_Tutorial", valores,"id_Tutorial_FK=?", args);

							Toast.makeText(getApplicationContext(),getString(R.string.ya_no_es_favorito), Toast.LENGTH_LONG).show();
							menu.findItem(1).setIcon(R.drawable.star_empty);
						}
					} else
					{
						db.execSQL("INSERT INTO favorito_Tutorial(id_Tutorial_FK,es_favorito_Tutorial) values("
								+ getIntent().getExtras().getInt("id_articulo")
								+ ", " + 1 + ")");
						Toast.makeText(getApplicationContext(),getString(R.string.ahora_es_favorito), Toast.LENGTH_LONG).show();
						menu.findItem(1).setIcon(R.drawable.star_selected);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							getString(R.string.error_al_agregar_favorito),
							Toast.LENGTH_LONG).show();
				}
				return true;
			case 2:
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("message/rfc822");
				sharingIntent.putExtra(Intent.EXTRA_EMAIL,new String[] { "hector.lopez.gatica@gmail.com" });
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
				sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(sharingIntent,
								getString(R.string.seleccione_gestor)));
				return true;
			case 3:
				Intent intent = new Intent(this, TabsAbout.class);
				startActivity(intent);
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
		@Override
		public void onBackPressed() {
			if(favorito_clickleado)
			{
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
			}
			finish();
		}
}
