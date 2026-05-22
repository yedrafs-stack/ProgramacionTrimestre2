package es.studium;

import java.io.IOException;

public class Indice
{
	public Indice()
	{
		try
		{
			ProcessBuilder pb = new ProcessBuilder("hh.exe", "Primero.chm");
			pb.start();
			System.out.println("Abriendo el archivo CHM...");
		}
		catch (IOException e)
		{
			System.err.println("Error al intentar abrir el archivo CHM: " + e.getMessage());
		}
	}
}
