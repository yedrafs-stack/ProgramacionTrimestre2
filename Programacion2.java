package es.studium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Programacion2
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	String login = "programacion";
	String password = "Studium2025#";
	String sentenciaSQL = "SELECT * FROM ciclos";
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null; 
	
	public Connection conectar()
	{
		try
		{
			// Cargar los drivers
			Class.forName(driver);
			// Establecer la conexión
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
			return connection;
		}	
		catch (ClassNotFoundException cnfe)
		{
			System.err.println("Error de driver");
			return null;
		}
		catch (SQLException se)
		{
			System.err.println("Error de conexión: url, usuario o clave");
			return null;
		}
	}
	public boolean desconectar(Connection conexion)
	{
		try
		{
			// Desconectar de la BD
			if (conexion != null)
			{
				conexion.close();
			}
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Error al cerrar conexión");
			return false;
		}
	}
	public int comprobarCredenciales(String nombreUsuario, String claveUsuario)
	{
		sentenciaSQL = "SELECT * FROM usuario WHERE nombreUsuario = ? AND claveUsuario = SHA2(?,256)";
		try
		{
			PreparedStatement ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombreUsuario);
			ps.setString(2, claveUsuario);
			rs = ps.executeQuery();
			if(rs.next())
			{
				return(rs.getInt("tipoUsuario"));
			}
			else
			{
				return -1; //CREDENCIALES INCORRECTAS
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}

