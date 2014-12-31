package com.recreo.games.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.recreo.games.tutorial.R;
import com.recreo.games.clases.Datos_Spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Adaptador_lectura extends BaseAdapter{

	private Activity activity;
	Context context;
	ArrayList<Datos_Spinner> listaDatos;
	private static LayoutInflater inflater = null;
	//int posicion = 0;
	ViewHolder holder;

	String tag, ESTADO;

	ImageView imageView_leido;

	public Adaptador_lectura(Activity a, Context c, ArrayList<Datos_Spinner> lista) {
		activity = a;
		this.context = c;
		listaDatos = lista;
		// data=d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return listaDatos.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public void addItem(Datos_Spinner item) {
		listaDatos.add(item);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
		//posicion = position;

		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.item_list_main_activity, null);
			holder.TextView_nombre = (TextView) convertView.findViewById(R.id.textView_nombre);
			holder.Textview_descripcion = (TextView) convertView.findViewById(R.id.textView_autor);
			//holder.Textview_fecha = (TextView) convertView.findViewById(R.id.textView_fecha);
			holder.imageView_favorito = (ImageView) convertView.findViewById(R.id.imageView_item_list);
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
			holder.textView_subtitulo = (TextView) convertView.findViewById(R.id.textView_subtitulo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (listaDatos.get(position).id != 0) {
			holder.layout.setVisibility(View.VISIBLE);
			holder.imageView_favorito.setVisibility(View.VISIBLE);

			holder.TextView_nombre.setText(listaDatos.get(position).nombre);
			if (listaDatos.get(position).sub_titulo.length() == 0)
				holder.textView_subtitulo.setVisibility(View.GONE);
			else
			{
				holder.textView_subtitulo.setVisibility(View.VISIBLE);
				holder.textView_subtitulo.setText(listaDatos.get(position).sub_titulo);
			}
			holder.Textview_descripcion.setText(listaDatos.get(position).autor);
			
			if (listaDatos.get(position).favorito == 0)
				holder.imageView_favorito.setVisibility(View.GONE);
			else if (listaDatos.get(position).favorito == 1)
				holder.imageView_favorito.setImageResource(R.drawable.star_selected);
					
			if (listaDatos.get(position).leido == 0)
				holder.imageView_favorito.setVisibility(View.GONE);
			else if (listaDatos.get(position).leido == 1 || listaDatos.get(position).leido == 2)
			{
				holder.imageView_favorito.setImageResource(android.R.drawable.checkbox_on_background);
				holder.imageView_favorito.setVisibility(View.VISIBLE);
			}
		} else {
			holder.imageView_favorito.setVisibility(View.GONE);
			holder.layout.setVisibility(View.GONE);
		}

		return convertView;
	}

	public static class ViewHolder {
		public TextView TextView_nombre, Textview_descripcion, textView_subtitulo;
		public ImageView imageView_favorito;
		public RelativeLayout layout;
	}
}