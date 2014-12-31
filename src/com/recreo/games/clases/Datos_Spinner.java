package com.recreo.games.clases;

public class Datos_Spinner {
	public int id;
	public String nombre;
	public String autor;
	public String sub_titulo;
	public String fecha;
	public int leido;	//0 = no leido, 1 = semi leido, 2 = leido
	public int disponiblidad;	// 0 = no mostrar (letra), 1 = local, 2 = online
	public String idioma;
	public String url;
	public int favorito;
	public int visible;
	
	public Datos_Spinner(int id, String nombre,String sub_titulo,String autor,String fecha, int leido,int disponiblidad,String idioma, String url,int favorito, int visible)
	{
		this.id = id;
		this.nombre = nombre;
		this.sub_titulo = sub_titulo;
		this.autor = autor;
		this.fecha = fecha;
		this.leido = leido;
		this.disponiblidad = disponiblidad;
		this.idioma = idioma;
		this.url = url;
		this.favorito = favorito;
		this.visible = visible;
	}
}
