package com.recreo.games.database;

import com.recreo.games.clases.Disponibilidad_tutorial;
import com.recreo.games.clases.Idioma_tutorial;
import com.recreo.games.clases.Nombre_Tutoriales;
import com.recreo.games.clases.globales;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CrearBaseDeDatos  extends SQLiteOpenHelper {	
	Context contexto;
    String sqlCreate = "CREATE TABLE IF NOT EXISTS  Articulo (id_Articulo INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		"titulo_Articulo nvarchar(40), " +
    		"subtitulo_Articulo nvarchar(60)," +
    		"leido_Articulo integer, " +
    		"fecha_Actualizacion_Articulo TIMESTAMP DEFAULT (datetime('now','localtime')), " +
    		"posicion_lectura_Articulo FLOAT, " +
    		"favorito_Articulo integer DEFAULT 0)";
    
    String sqlCreate2 = "CREATE TABLE IF NOT EXISTS Tutorial (id_Tutorial INTEGER PRIMARY KEY, " +
    		"titulo_Turorial nvarchar(40), " +
    		"autor_Tutorial nvarchar(60)," +
    		"subtitulo_Tutorial nvarchar(100) DEFAULT ''," +
    		"disponiblidad_Tutorial integer, " +
    		"fecha_Actualizacion_Tutorial nvarchar(10), " +
    		"idioma_Tutorial nvarchar(15), " +
    		"visible integer DEFAULT 1, " +
    		"url_Tutorial nvarchar(200))";
    
    String sqlCreate3 = "CREATE TABLE IF NOT EXISTS favorito_Tutorial (id_favorito_Tutorial INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		" id_Tutorial_FK INTEGER, " +
    		" es_favorito_Tutorial INTEGER DEFAULT 0)";
    
    String sqlCreate4 = "CREATE TABLE IF NOT EXISTS lenguajes_visibles (" +
    		"id_lenguaje INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		"nombre_lenguaje nvarchar(30), " +
    		" visible DEFAULT 1)";
    
    String idioma_espanol = "Español";
    String idioma_ingles = "English";
 
    public CrearBaseDeDatos(Context contexto, String nombre,CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
        this.contexto = contexto;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
    	db.execSQL(" CREATE TABLE IF NOT EXISTS android_metadata(locale TEXT DEFAULT 'es_ES');");
    	
        db.execSQL(sqlCreate);

        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        db.execSQL(sqlCreate4);
        cargarDatosPrueba(db);
        insertarNombreLenguajes(db);
    }
    
	public void cargarDatosPrueba(SQLiteDatabase db){
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Introducción','Vamos a ver',0,0,'05/11/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Versiones de Android (API)','Para saber en que versión programar.',0,0,'05/11/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Componentes de una aplicación en Android','Definición de: Activity, Services, Intent, etc.',0,0,'05/11/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Creación de un proyecto en Android','Como crear un proyecto y su estructura.',0,0,'05/11/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Empezando con nuestro proyecto','Un ejemplo sencillo.',0,0,'05/11/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Layouts','Tipos de layout, ancho, alto, alinamiento.',0,0,'05/11/2013')");

    	db.execSQL("DELETE FROM Tutorial");	
    	//Español, English
    	
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(1,'" + Nombre_Tutoriales.Android + "','Héctor López Gatica',1,'05/11/2013','Español','')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(2,'" + Nombre_Tutoriales.Windows_Phone + "','Rodrigo Díaz Concha',2,'05/11/2013','Español','http://blogs.msdn.com/b/warnov/archive/2011/06/22/tutoriales-de-desarrollo-para-windows-phone-totalmente-en-espa-241-ol-y-gratuitos.aspx')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(3,'" + Nombre_Tutoriales.Android + "','Salvador Gómez',2,'05/11/2013','Español','http://www.sgoliver.net/blog/?page_id=3011')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(4,'" + Nombre_Tutoriales.Perl + "','ZenTut.com',2,'05/11/2013','English','http://www.zentut.com/perl-tutorial/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(5,'" + Nombre_Tutoriales.Ruby_on_Rails + "','xenodesystems.blogspot.com',2,'05/11/2013','Español','http://xenodesystems.blogspot.com/2012/06/curso-gratuito-de-ruby-on-rails.html')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(6,'" + Nombre_Tutoriales.PHP + "','php.net',2,'05/11/2013','Español','http://php.net/manual/es/index.php')");
                    
        /*db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(7,'iOS 6','manzanamagica.com',2,'05/11/2013','Español','http://www.manzanamagica.com/ios/desarrollo/')");
        */
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(8,'" + Nombre_Tutoriales.ASP_NET + "','maestrosdelweb.com',2,'05/11/2013','Español','http://www.maestrosdelweb.com/editorial/tutoria-desarrolloweb-asp-net/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(9,'" + Nombre_Tutoriales.Django + "','django-book.blogspot.com',2,'05/11/2013','Español','http://django-book.blogspot.com/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(10,'" + Nombre_Tutoriales.SQL + "','Claudio Caseres',3,'05/11/2013','Español','http://www.unalmed.edu.co/~mstabare/Sql.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(11,'" + Nombre_Tutoriales.SQL + "','desarrolloweb.com',2,'05/11/2013','Español','http://www.desarrolloweb.com/manuales/9/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(12,'" + Nombre_Tutoriales.C + "','elrincondelc.com',2,'05/11/2013','Español','http://www.elrincondelc.com/cursoc/cursoc.html')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(13,'" + Nombre_Tutoriales.C + "','cprogramming.com',2,'05/11/2013','English',' http://www.cprogramming.com/tutorial.html')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(14,'" + Nombre_Tutoriales.C + "','programatium.com',2,'05/11/2013','English','http://www.programatium.com/c.htm')");
       
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(15,'" + Nombre_Tutoriales.Unity + "','Brackeys',5,'05/11/2013','English','PLPV2KyIb3jR7F_B4p8X3YwHPaExh0R9Kk')");	//youtubeplaylist
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(16,'" + Nombre_Tutoriales.JQuery_Mobile + "','lawebera.es',2,'05/11/2013','Español','http://www.lawebera.es/jquery-mobile')");	
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(18,'" + Nombre_Tutoriales.JAVA + "','Fundamentos de Programación en JAVA','Jorge Martínez Ladrón de Guevara',3,'05/11/2013','Español','http://pendientedemigracion.ucm.es/info/tecnomovil/documentos/fjava.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(19,'" + Nombre_Tutoriales.C_plusplus + "','Manual básico de Programación en C++','Servicios Informáticos Universidad Complutense de Madrid',3,'05/11/2013','Español','http://www.sisoft.ucm.es/Manuales/C++.pdf')");
        
        //db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        	//	" values(20,'" + Nombre_Tutoriales.HTML5 + "','El presente de la web. HTML5, css3 y javascript','John Freddy Vega, Christian Van Der Henst',3,'05/11/2013','Español','https://mejorando.la/documentos/mejorandolaweb-guia-html5.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(21,'" + Nombre_Tutoriales.JAVA + "','','Studios CAT y Otakus TV',5,'05/11/2013','Español','PL3CF8EB568255B619')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(22,'" + Nombre_Tutoriales.DFD + "','Diagramas de Flujo, Algoritmos y Programación','Juan Manuel',5,'05/11/2013','Español','PLA608712FF24643A3')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(23,'" + Nombre_Tutoriales.Android + "','Tutoriales Android - Programar para Android desde cero','Jorge Villalobos',5,'05/11/2013','Español','PL0A103A65064BC6E2')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(24,'" + Nombre_Tutoriales.PHP + "','','VideoTutoriales.com',5,'05/11/2013','Español','PLF2E7FC0407FF3398')");
        
       db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(25,'" + Nombre_Tutoriales.C_sharp + "','Aprende C# (Guía rápida)','Félix Manuel Brito Amarante',5,'05/11/2013','Español','PL1CFD6C344CEE058D')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(26,'" + Nombre_Tutoriales.iOS + "','Tutorial de iPhone SDK','Félix Manuel Brito Amarante',5,'05/11/2013','Español','PL9AE1A840A5B7BD43')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(27,'" + Nombre_Tutoriales.Django + "','Tutorial Django 1.4 + Ubuntu','Alex Dzul',5,'05/11/2013','Español','PL2670DAAFCFECA138')");
        
        //05/12/2013
        update1(db);
        //09/12/2013
        update2(db);
        //24-03-2014
        update3(db);
        //28-06-2014
        update6(db);

    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
 
    	/*if(versionNueva == 3)	//obsoleta, esta actualización no se envio nunca a Google Play
    	{
	        //Se elimina la versión anterior de la tabla
	        db.execSQL("DROP TABLE IF EXISTS Articulo");
	        db.execSQL("DROP TABLE IF EXISTS Tutorial");	
	 
	        //Se crea la nueva versión de la tabla
	        db.execSQL(sqlCreate);
	
	        db.execSQL(sqlCreate2);
	        db.execSQL(sqlCreate3);
	        cargarDatosPrueba(db);
    	}
    	else if(versionAnterior == 3 && versionNueva == 4)	//versionNueva = 4
        {
    		update1(db);
        }
    	else if(versionAnterior == 3 && versionNueva == 5)
    	{
    		update1(db);
    		update2(db);
    	}
    	else if(versionAnterior == 4 && versionNueva == 5)		//versionNueva = 5
    	{
    		update2(db);
    	}
    	else if(versionAnterior == 4 && versionNueva == 6)
    	{
    		update2(db);
    		update3(db);
    	}
    	else if(versionAnterior == 3 && versionNueva == 6)
    	{
    		update1(db);
    		update2(db);
    		update3(db);
    	}
    	else if(versionAnterior == 5 && versionNueva == 6)
    	{
    		update3(db);
    	}else if(versionAnterior == 6 && versionNueva == 7)
    	{
    		update_tables1(db);
    	}
    	else if(versionAnterior == 5 && versionNueva == 7)
    	{
    		update_tables1(db);
    		update3(db);
    	}
    	else if(versionAnterior == 4 && versionNueva == 7)
    	{
    		update_tables1(db);
    		update3(db);
    		update2(db);
    	}
    	else if(versionAnterior == 3 && versionNueva == 7)
    	{
    		update_tables1(db);
    		update3(db);
    		update2(db);
    		update1(db);
    	}
    	else if(versionAnterior == 3 && versionNueva == 8)
    	{
    		update_tables1(db);
    		update1(db);
    		update2(db);
    		update3(db);
    		update4(db);
    	}else if(versionAnterior == 4 && versionNueva == 8)
    	{
    		update_tables1(db);
    		update2(db);
    		update3(db);
    		update4(db);
    	}else if(versionAnterior == 5 && versionNueva == 8)
    	{
    		update_tables1(db);
    		update3(db);
    		update4(db);
    	}
    	else if(versionAnterior == 6 && versionNueva == 8)
    	{
    		update_tables1(db);
    		update4(db);
    	}else if(versionAnterior == 7 && versionNueva == 8)
    	{
    		update4(db);
    	}
    	
    	else if(versionNueva == 9)
    	{
    		update5_borrar(db);
    		if(versionAnterior < 7)
    		{
    			db.execSQL(sqlCreate4);
    	    	 insertarNombreLenguajes(db);
    		}
    	}
    	else if(versionNueva == 10)
    	{
    		if(versionAnterior < 9)
    		{
    			update5_borrar(db);
        		if(versionAnterior < 7)
        		{
        			db.execSQL(sqlCreate4);
        	    	 insertarNombreLenguajes(db);
        		}
    		}
    		update6(db);
    	}*/
    	if(versionAnterior<11)
    		cargarDatosPrueba(db);
    }
    
    
    void update1(SQLiteDatabase db)
    {
    	 db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(28,'" + Nombre_Tutoriales.Unity + "','Serie de videos y sus códigos','unity3dstudent',2,'05/12/2013','English','http://www.unity3dstudent.com/category/modules/')");
	        
	        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(29,'" + Nombre_Tutoriales.Unity + "','Serie de videos y sus códigos','unity3d.com',2,'05/12/2013','English','http://unity3d.com/learn/tutorials/modules/beginner/scripting')");
	        
	        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(30,'" + Nombre_Tutoriales.JQuery_Mobile + "','Introducción a jQuery Mobile','C. Enrique Ortiz',2,'05/12/2013','Español','http://www.ibm.com/developerworks/ssa/library/wa-jquerymobileupdate/')");
	        
	        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(31,'" + Nombre_Tutoriales.JQuery_Mobile + "','Manual de jQuery Mobile','Miguel Angel Alvarez y Dairo Galeano',3,'05/12/2013','Español','https://dl.dropboxusercontent.com/u/79105561/manual-jquery-mobile.pdf')");
	        
	        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(32,'" + Nombre_Tutoriales.Windows_Phone + "','Diplomado Windows Phone 8','Microsoft Virtual Academy',2,'05/12/2013','Español','http://www.microsoftvirtualacademy.com/training-courses/desarrollo-en-windows-phone-8')");
	        
	        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(33,'" + Nombre_Tutoriales.Python + "','El tutorial de Python','Guido van Rossum',3,'05/12/2013','Español','http://docs.python.org.ar/tutorial/pdfs/TutorialPython3.pdf')");
	        
	        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(34,'" + Nombre_Tutoriales.Python + "','Python para todos','Raúl González Duque',3,'05/12/2013','Español','http://www.ceibal.edu.uy/contenidos/areas_conocimiento/aportes/python_para_todos.pdf')");
    }
    void update2(SQLiteDatabase db)
    {
    	try
    	{
	    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	    		" values(35,'" + Nombre_Tutoriales.JavaScript + "','Introducción a JavaScript','Javier Eguiluz',2,'09/12/2013','Español','http://librosweb.es/javascript/index.html')");
	    	
	    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
	        		" values(36,'" + Nombre_Tutoriales.Corona + "','Learning Corona','http://learningcorona.com/',2,'09/12/2013','English','http://learningcorona.com/')");
    	}catch(Exception ex)
    	{
    		ex.toString();
    	}
    }
    
    void update3(SQLiteDatabase db)
    {
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
    		" values(37,'" + Nombre_Tutoriales.Android + "','Tutorials about development for Android. ','Lars Vogel',2,'24/03/2014','English','http://www.vogella.com/tutorials/android.html')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(38,'" + Nombre_Tutoriales.Android + "','6 modules Android Development course','http://www.edureka.in/',5,'24/03/2014','English','PL9ooVrP1hQOFmgefDKSqC9ZZn5lXtlNiR')");
    	
    	//insertContent(db,39,Nombre_Tutoriales.Windows_Phone ,"Free Windows Phone 8 book","Paul Thurrott",2,"24/03/2014","English","https://dl.dropboxusercontent.com/u/57018156/Paul%20Thurrott's%20Windows%20Phone%208%201.05.pdf");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(40,'" + Nombre_Tutoriales.C_sharp + "','Course of 24 episodes','Bob Tabor',2,'24/03/2014','English','http://channel9.msdn.com/Series/C-Sharp-Fundamentals-Development-for-Absolute-Beginners')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(41,'" + Nombre_Tutoriales.Ruby_on_Rails + "','Free online tutorial','Tutorialspoint',2,'24/03/2014','English','http://www.tutorialspoint.com/ruby-on-rails/index.htm')");

    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(42,'" + Nombre_Tutoriales.Python + "','Free online tutorial','www.afterhoursprogramming.com/',2,'24/03/2014','English','http://www.afterhoursprogramming.com/tutorial/Python/Overview/')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(43,'" + Nombre_Tutoriales.Python + "','Python 3 tutorial','www.afterhoursprogramming.com',2,'24/03/2014','English','http://www.afterhoursprogramming.com/tutorial/Python/Overview/')");
    		
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(44,'" + Nombre_Tutoriales.Python + "','Python 3 tutorial','www.python-course.eu',2,'24/03/2014','English','http://www.python-course.eu/python3_course.php')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(45,'" + Nombre_Tutoriales.PHP + "','PHP 5 tutorial','www.w3schools.com/PHP',2,'24/03/2014','English','http://www.w3schools.com/PHP/')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(46,'" + Nombre_Tutoriales.JQuery_Mobile + "','Getting started with jQuery Mobile','Matthew David',2,'24/03/2014','English','http://www.adobe.com/devnet/dreamweaver/articles/getting-started-with-jquery-mobile.html')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(47,'" + Nombre_Tutoriales.Visual_Basic_Net + "','Visual Basic.Net training 2013','stuffucanuse.com',3,'24/03/2014','English','http://stuffucanuse.com/Intro-to-VB-7-March-2013.pdf')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(48,'" + Nombre_Tutoriales.Visual_Studio + "','Introduction to Visual Studio Technologies','Apress Media',3,'24/03/2014','English','http://download.microsoft.com/download/C/D/3/CD39BB69-35CC-458A-B4EB-2F928B58FB4B/Introduction-to-Visual-Studio-Technologies.pdf')");
    	
    }
    
    
    
    void update_tables1(SQLiteDatabase db)
    {
    	db.execSQL(sqlCreate4);
    	
    	insertarNombreLenguajes(db);
    	
    	db.execSQL("ALTER TABLE Tutorial ADD COLUMN visible integer DEFAULT 1");
    	
    	SharedPreferences prefs;
    	SharedPreferences.Editor editor;
    	prefs = contexto.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
		editor = prefs.edit();
		editor.putBoolean("saltar_intro", false);
		editor.commit();
    }
    
    void insertarNombreLenguajes(SQLiteDatabase db)
    {
    	insertNombreLenguje(db,Nombre_Tutoriales.Android,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.ASP_NET,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.C,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.C_sharp,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.C_plusplus,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Corona,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.DFD,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Django,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.HTML5,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.iOS,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.JAVA,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.JavaScript,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.JQuery_Mobile,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Perl,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.PHP,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Python,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Ruby_on_Rails,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.SQL,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Unity,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Visual_Basic_Net,1);
    	insertNombreLenguje(db,Nombre_Tutoriales.Windows_Phone,1);
    }
    
    void update4(SQLiteDatabase db)
    {
    	//http://www.lsi.us.es/docencia/get.php?id=7313
    	ContentValues content = new ContentValues();
    	content.put("titulo_Turorial", "Windows Phone");
    	db.update("Tutorial", content, "id_Tutorial=2",null);
    	
    	db.delete("Tutorial", "id_Tutorial=7",null);
    	
    	content.put("titulo_Turorial", "Unity");
    	db.update("Tutorial", content, "id_Tutorial=15",null);
    	
    	content.put("titulo_Turorial", "PHP");
    	db.update("Tutorial", content, "id_Tutorial=24",null);
    	
    	content.put("titulo_Turorial", "Windows Phone");
    	db.update("Tutorial", content, "id_Tutorial=39",null);
    	
    	content.put("titulo_Turorial", "Windows Phone");
    	db.update("Tutorial", content, "id_Tutorial=32",null);
    	
    	content.put("titulo_Turorial", "PHP");
    	db.update("Tutorial", content, "id_Tutorial=45",null);
    	
    	content.put("titulo_Turorial", "Visual Studio");
    	db.update("Tutorial", content, "id_Tutorial=48",null);

    }
        
    void update5_borrar(SQLiteDatabase db)
    {
    	db.delete("Tutorial", null, null);
    	db.delete("Tutorial", null, null);
    	 db.execSQL(sqlCreate2);
    	 cargarDatosPrueba(db);
    }
    
    void update6(SQLiteDatabase db)
    {
    	db.delete("Tutorial", "id_Tutorial = 20", null);
    	db.delete("Tutorial", "id_Tutorial = 39", null);
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(49,'" + Nombre_Tutoriales.HTML5 + "','Curso de HTML5','codigofacilito',"+Disponibilidad_tutorial.YOUTUBE+",'28/06/2014','"+Idioma_tutorial.Espanol+"','PL652DDE687FC4453C')");
    	
    	db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(50,'" + Nombre_Tutoriales.HTML5 + "','Simply Easy Learning','tutorialspoint.com',"+Disponibilidad_tutorial.PDF+",'28/06/2014','"+Idioma_tutorial.Ingles+"','http://www.tutorialspoint.com/html5/html5_tutorial.pdf')");
    	    	
    	insertContent(db,51,Nombre_Tutoriales.Windows_Phone ,"Building Apps for Windows Phone 8.1","microsoftvirtualacademy.com",Disponibilidad_tutorial.WEB,"28/06/2014",Idioma_tutorial.Ingles,"http://www.microsoftvirtualacademy.com/training-courses/building-apps-for-windows-phone-8-1-jump-start");
    	
    }
    
    private void insertContent(
    		SQLiteDatabase db,
    		int id_Tutorial,
    		String titulo_Turorial,
    		String subtitulo_Tutorial,
    		String autor_Tutorial,
    		String disponiblidad_Tutorial,
    		String fecha_Actualizacion_Tutorial,
    		String idioma_Tutorial,
    		String url_Tutorial
    		)
    {
    	ContentValues content = new ContentValues();
    	content.put("id_Tutorial", id_Tutorial);
    	content.put("titulo_Turorial", titulo_Turorial);
    	content.put("subtitulo_Tutorial", subtitulo_Tutorial);
    	content.put("autor_Tutorial", autor_Tutorial);
    	content.put("disponiblidad_Tutorial", disponiblidad_Tutorial);
    	content.put("fecha_Actualizacion_Tutorial", fecha_Actualizacion_Tutorial);
    	content.put("idioma_Tutorial", idioma_Tutorial);
    	content.put("url_Tutorial", url_Tutorial);
    	
    	db.insertOrThrow("Tutorial", null, content);
    }
 
    private void insertNombreLenguje(
    		SQLiteDatabase db,
    		String nombreLenguaje,
    		int visible
    		)
    {
    	ContentValues content = new ContentValues();
    	content.put("nombre_lenguaje", nombreLenguaje);
    	content.put("visible", visible);
    	
    	db.insertOrThrow("lenguajes_visibles", null, content);
    }
    public boolean comprobarBaseDatos() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = globales.DB_PATH + globales.DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (SQLiteException e) {
			// No existe
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}
}
