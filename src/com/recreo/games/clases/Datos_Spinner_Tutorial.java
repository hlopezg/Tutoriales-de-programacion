package com.recreo.games.clases;

public class Datos_Spinner_Tutorial {
	public int id;
	public String nombre;
	public String sub_titulo;
	public String fecha;
	public int leido;	//0 = no leido, 1 = semi leido, 2 = leido
	public int disponiblidad;	// 0 = no mostrar (letra), 1 = local, 2 = online
	
	public Datos_Spinner_Tutorial(int id, String nombre,String sub_titulo,String fecha, int leido,int disponiblidad)
	{
		this.id = id;
		this.nombre = nombre;
		this.sub_titulo = sub_titulo;
		this.fecha = fecha;
		this.leido = leido;
		this.disponiblidad = disponiblidad;
	}
}
