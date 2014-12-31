package com.recreo.games.adapter;

import java.util.ArrayList;

import com.cuubonandroid.sugaredlistanimations.GPlusListAdapter;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.recreo.games.tutorial.R;
import com.recreo.games.clases.Datos_Spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Adaptador_GPlus extends GPlusListAdapter{

	private Activity activity;
	Context context;
	ArrayList<Datos_Spinner> listaDatos;
	private static LayoutInflater inflater = null;
	int posicion = 0;
	ViewHolder holder;

	String tag, ESTADO;

	ImageView imageView_leido;

	public Adaptador_GPlus(Activity a, Context c, ArrayList<Datos_Spinner> lista, SpeedScrollListener scrollListener) {
		super(c, scrollListener, null);
		activity = a;
		this.context = c;
		listaDatos = lista;
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
	public View getRowView(int position, View convertView, ViewGroup parent) {
		holder = null;

		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.item_list, null);
			holder.TextView_nombre = (TextView) convertView.findViewById(R.id.textView_nombre);
			holder.Textview_descripcion = (TextView) convertView.findViewById(R.id.textView_autor);
		//	holder.Textview_fecha = (TextView) convertView.findViewById(R.id.textView_fecha);
			holder.imageView_disponibilidad = (ImageView) convertView.findViewById(R.id.imageView_disponibilidad);
			holder.imageView_favorito = (ImageView) convertView.findViewById(R.id.imageView_item_list);
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
			holder.imageView_bandera = (ImageView) convertView.findViewById(R.id.imageView_bandera);
			holder.textView_subtitulo = (TextView) convertView.findViewById(R.id.textView_subtitulo);
			holder.textView_letra = (TextView) convertView.findViewById(R.id.textView_letra);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (listaDatos.get(position).id != 0) {
			holder.layout.setVisibility(View.VISIBLE);
			holder.imageView_favorito.setVisibility(View.VISIBLE);
			holder.textView_letra.setVisibility(View.GONE);

			holder.TextView_nombre.setText(listaDatos.get(position).nombre);
			if (listaDatos.get(position).sub_titulo.length() == 0)
				holder.textView_subtitulo.setVisibility(View.GONE);
			else
			{
				holder.textView_subtitulo.setVisibility(View.VISIBLE);
				holder.textView_subtitulo.setText(listaDatos.get(position).sub_titulo);
			}
			holder.Textview_descripcion.setText(listaDatos.get(position).autor);
			//holder.Textview_fecha.setVisibility(View.VISIBLE);
			//holder.Textview_fecha.setText(activity.getString(R.string.agregado_el) + ": " + listaDatos.get(position).fecha);

			holder.imageView_disponibilidad.setVisibility(View.VISIBLE);

			if (listaDatos.get(position).idioma.equals("Español")) {
				holder.imageView_bandera.setImageResource(R.drawable.bandera_es);
			} else if (listaDatos.get(position).idioma.equals("English"))
				holder.imageView_bandera.setImageResource(R.drawable.bandera_us);
			else
				holder.imageView_bandera.setVisibility(View.GONE);

			if (listaDatos.get(position).disponiblidad == 1)
				holder.imageView_disponibilidad.setImageResource(R.drawable.icon_smartphone);
			else if (listaDatos.get(position).disponiblidad == 2) // web
				holder.imageView_disponibilidad.setImageResource(R.drawable.web);
			else if (listaDatos.get(position).disponiblidad == 3) // pdf
				holder.imageView_disponibilidad.setImageResource(R.drawable.pdf);
			else if (listaDatos.get(position).disponiblidad == 4) // pdf descargado
				holder.imageView_disponibilidad.setImageResource(R.drawable.pdf_smartphone);
			else if (listaDatos.get(position).disponiblidad == 5) // pdf descargado
				holder.imageView_disponibilidad.setImageResource(R.drawable.youtube_icon);
			else
				holder.imageView_disponibilidad.setVisibility(View.GONE);

			if (listaDatos.get(position).favorito == 0)
				holder.imageView_favorito.setVisibility(View.GONE);
			else if (listaDatos.get(position).favorito == 1)
				holder.imageView_favorito.setImageResource(R.drawable.star_selected);

		/*	if (listaDatos.get(groupPosition).get(childPosition).leido == 0)
			{
				holder.imageView_favorito.setImageResource(R.drawable.circulo_rojo);
				holder.imageView_favorito.setVisibility(View.VISIBLE);
			}
			else if (listaDatos.get(groupPosition).get(childPosition).leido == 1)
			{
				holder.imageView_favorito.setImageResource(R.drawable.circulo_amarillo);
				holder.imageView_favorito.setVisibility(View.VISIBLE);
			}
			else if (listaDatos.get(groupPosition).get(childPosition).leido == 2)
			{
				holder.imageView_favorito.setImageResource(R.drawable.circulo_verde);
				holder.imageView_favorito.setVisibility(View.VISIBLE);
			}*/

		} else {
			holder.imageView_favorito.setVisibility(View.GONE);
			holder.imageView_disponibilidad.setVisibility(View.GONE);
			holder.layout.setVisibility(View.GONE);
			holder.textView_letra.setVisibility(View.VISIBLE);
			holder.textView_letra.setText(listaDatos.get(position).nombre);
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView TextView_nombre, Textview_descripcion, textView_subtitulo;
		private TextView textView_letra;
		private ImageView imageView_favorito,imageView_disponibilidad,imageView_bandera;
		private RelativeLayout layout;
		
	}
}