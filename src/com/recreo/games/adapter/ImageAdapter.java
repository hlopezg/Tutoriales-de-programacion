package com.recreo.games.adapter;

import java.util.ArrayList;

import com.recreo.games.adapter.Adaptador.ViewHolder;
import com.recreo.games.clases.ImagenVisible;
import com.recreo.games.tutorial.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
	
	ViewHolder holder;
	private static LayoutInflater inflater = null;
//	Integer[] imagenes;
	ArrayList<ImagenVisible> lenguajes_visible;
	ArrayList<ImageView> list_check;
	public ImageAdapter(Activity activity, ArrayList<ImagenVisible> lenguajes_visible) {
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lenguajes_visible = lenguajes_visible;
		list_check = new ArrayList<ImageView>();
	}

	public int getCount() {// devuelve el número de elementos que se introducen
		// en el adapter
		return lenguajes_visible.size();
	}

	public Object getItem(int position) {
		// este método debería devolver el objeto que esta en esa posición del
		// adapter. No es necesario en este caso más que devolver un objeto
		// null.
		return null;
	}

	public long getItemId(int position) {
		// este método debería devolver el id de fila del item que esta en esa
		// posición del adapter. No es necesario en este caso más que devolver
		// 0.
		return 0;
	}

	// crear un nuevo ImageView para cada item referenciado por el Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
 		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.layout_gridview_item, null);
			holder.imageView_1 = (ImageView) convertView.findViewById(R.id.imageView01);
			holder.imageView_check = (ImageView) convertView.findViewById(R.id.imageView_check);
			holder.linearLayout_grid_item = (LinearLayout) convertView.findViewById(R.id.linearLayout_grid_item);

			//list_check.add(holder.imageView_check);

			//holder.imageView_1.setScaleType(ImageView.ScaleType.CENTER_CROP);
			
	 		
	 		if (holder.imageView_1.getTag() == null)
	 			holder.imageView_1.setTag("lenguaje");
	 		
	 		if (holder.imageView_check.getTag() == null)
	 		{
	 			holder.imageView_check.setTag("check");
	 		}
	 		
	 		 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

 		holder.imageView_1.setImageResource(lenguajes_visible.get(position).drawable);
 		
 		holder.linearLayout_grid_item.setTag(String.valueOf(position));
 		
 		if(lenguajes_visible.get(position).visible == 1)
 		{
 			holder.linearLayout_grid_item.setBackgroundColor(Color.parseColor("#A2FF96"));
 			holder.imageView_check.setImageResource(R.drawable.check);
 		}
 		else
 		{
 			holder.linearLayout_grid_item.setBackgroundColor(Color.parseColor("#C1C1C1"));
 			holder.imageView_check.setImageResource(R.drawable.uncheck);
 		}
 		
 		
		return convertView;
	}
	
	public static class ViewHolder {
		private ImageView imageView_1;
		private ImageView imageView_check;
		private LinearLayout linearLayout_grid_item;
	}
}