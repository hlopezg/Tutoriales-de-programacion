package com.recreo.games.controlador;

import java.util.ArrayList;

import android.app.Activity;

import com.recreo.games.tutorial.R;
import com.recreo.games.adapter.Drawer_adapter;
import com.recreo.games.clases.Datos_Drawer_adapter;

public class Crear_datos_tablas {
	Activity activity;

	public Crear_datos_tablas(Activity activity)
	{
		this.activity = activity;
	}

	public Drawer_adapter tabla_filtro()
	{
		ArrayList<Datos_Drawer_adapter> lista_datos = new ArrayList<Datos_Drawer_adapter>();
		Datos_Drawer_adapter tabla = new Datos_Drawer_adapter(0, activity.getString(R.string.filtrar_por));
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(1, activity.getResources().getStringArray(R.array.filtros)[0]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(2, activity.getResources().getStringArray(R.array.filtros)[1]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(3, activity.getResources().getStringArray(R.array.filtros)[2]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(4, activity.getResources().getStringArray(R.array.filtros)[3]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(5, activity.getResources().getStringArray(R.array.filtros)[4]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(6, activity.getResources().getStringArray(R.array.filtros)[5]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(7, activity.getResources().getStringArray(R.array.filtros)[6]);
		lista_datos.add(tabla);
		tabla = new Datos_Drawer_adapter(8, activity.getResources().getStringArray(R.array.filtros)[7]);
		lista_datos.add(tabla);

		// tabla = new Datos_Drawer_adapter(0, "Favoritos:");
		// lista_datos.add(tabla);

		Drawer_adapter especial_adapter = new Drawer_adapter(activity, lista_datos);
		return especial_adapter;
	}

}
