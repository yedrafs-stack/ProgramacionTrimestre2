package es.studium;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FicheroLog
{
	public static String usuario = "";
	public static void Log(String movimiento)
	{
		try
		{
			FileWriter fw = new FileWriter("movimientos.log", true);
			PrintWriter pw = new PrintWriter(fw);

			LocalDateTime ahora = LocalDateTime.now();
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String fechaFormateada = ahora.format(formato);

			pw.println("[" + fechaFormateada + "] [" + usuario + "] - " +  movimiento);

			pw.close();
			fw.close();
		}
		catch (IOException e)
		{
			System.err.println("Error al escribir en el Log: " + e.getMessage());
		}
	}
}