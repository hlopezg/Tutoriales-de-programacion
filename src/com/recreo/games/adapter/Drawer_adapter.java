package com.recreo.games.adapter;

import java.util.ArrayList;

import com.recreo.games.tutorial.R;
import com.recreo.games.clases.Datos_Drawer_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Drawer_adapter extends BaseAdapter {

	private Activity activity;
	ArrayList<Datos_Drawer_adapter> listaDatos;
	private static LayoutInflater inflater = null;
	int posicion = 0;
	ViewHolder holder;

	public Drawer_adapter(Activity a, ArrayList<Datos_Drawer_adapter> lista) {
		activity = a;
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

	public void addItem(Datos_Drawer_adapter item) {
		listaDatos.add(item);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
		posicion = position;
		if (convertView == null) {
			try
			{
				convertView = inflater.inflate(R.layout.drawer_list_item, null);
			}catch(Exception ex)
			{
				convertView = inflater.inflate(R.layout.drawer_list_item_support, null);
			}
			holder = new ViewHolder();

			holder.text1 = (TextView) convertView.findViewById(R.id.text1);
			holder.image1 = (ImageView) convertView.findViewById(R.id.image1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		holder.text1.setText(listaDatos.get(position).nombre);

		if (listaDatos.get(position).disponibilidad == 1) {
			holder.image1.setImageResource(R.drawable.icon_smartphone);
		} else if (listaDatos.get(position).disponibilidad == 2) {// web
			holder.image1.setImageResource(R.drawable.web);
		} else if (listaDatos.get(position).disponibilidad == 3) {// pdf
			holder.image1.setImageResource(R.drawable.pdf);
		} else if (listaDatos.get(position).disponibilidad == 4) {// pdf descargado
			holder.image1.setImageResource(R.drawable.pdf_smartphone);
		}
		else if (listaDatos.get(position).disponibilidad == 5) {// youtube
			holder.image1.setImageResource(R.drawable.youtube_icon);
		}
		else if (listaDatos.get(position).disponibilidad == 6) {// todos
			holder.image1.setImageResource(R.drawable.todos);
		}
		else if (listaDatos.get(position).disponibilidad == 7) {// favoritos
			holder.image1.setImageResource(R.drawable.star_selected);
		}
		else if (listaDatos.get(position).disponibilidad == 8) {// lenguaje
			holder.image1.setImageResource(R.drawable.icono_tutorial_android);
		}
		else if (listaDatos.get(position).disponibilidad == 0) {// solo texto
			holder.image1.setVisibility(View.GONE);
			holder.text1.setTextAppearance(activity, android.R.style.TextAppearance_Medium);
			holder.text1.setTextColor(activity.getResources().getColor(android.R.color.white));
		}
		return convertView;
	}

	public static class ViewHolder {
		public LinearLayout tableRow1;
		TextView text1;
		private ImageView image1;
	}

}
