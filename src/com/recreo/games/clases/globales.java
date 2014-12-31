package com.recreo.games.clases;

import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class globales {
	public static String DB_NAME = "basededatos_tutorial.db";
	public static int DB_VERSION = 11;	//11
	public static String DB_PATH = "/data/data/com.example.tutorial/databases/";
	public static String FILES_PATH = "/data/data/com.example.tutorial/files/";
	

	public static String fecha_actualizacion(Context context)
	{
		String s = "";
		try{
		     ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
		     ZipFile zf = new ZipFile(ai.sourceDir);
		     ZipEntry ze = zf.getEntry("classes.dex");
		     long time = ze.getTime();
		     s = SimpleDateFormat.getInstance().format(new java.util.Date(time));
		     
		  }catch(Exception e){
		  }
		return s;
	}
}
