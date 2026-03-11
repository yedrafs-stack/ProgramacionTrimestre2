package es.studium;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consulta3 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("ConsultaAsignatura");
	TextArea txaConsulta = new TextArea(7,24);
	Button btnActualizar = new Button("Actualizar");

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	String login = "programacion";
	String password = "Studium2025#";
	String sentenciaSQL = "SELECT * FROM asignaturas";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null; //PARA LOS SELECT

	public Consulta3()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300,240);
		ventana.addWindowListener(this);
		btnActualizar.addActionListener(this);
		ventana.add(txaConsulta);
		ventana.add(btnActualizar);
		ventana.setResizable(true);		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	public static void main(String[] args)
	{
		new Consulta3();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			//CARGAR LOS DRIVERS
			Class.forName(driver);
			//ESTABLECER LA CONEXION
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
			//CREAR LA SENTENCIA DE CONSULTA O DE ALTA O DE BAJA O DE ACTU...
			statement = connection.createStatement();
			//EJECUTAR LA INSTRUCCION SQL
			rs = statement.executeQuery(sentenciaSQL);//SELECT * FROM -;
			//SACAR INFORMACIÓN, METER DATOS, BORRAR DATOS, ACTUALIZAR
			//MOSTRAR EN LA CONSOLA
			while(rs.next())
			{
				txaConsulta.append(rs.getInt("idAsignatura")+ 
						"-"+rs.getString("nombreAsignatura")+
						"-" +rs.getString("descripcionAsignatura")+
						"-" +rs.getString("idCicloFK")+"\n");
			}
			
		}
		catch(ClassNotFoundException  cnfe)
		{
			System.err.println("Error de driver"+cnfe.getMessage());
		}
		catch(SQLException se)
		{
			System.err.println("Error de conexión: url, usuaro o clave");
		}
		finally 
		{
			try
			{
				//DESCONECTAR DE LA BD
				if(connection!=null)
				{
					connection.close();
				}
			}
			catch(SQLException evento)
			{
				System.out.println("Error al cerrar conexión");
			}
			System.out.println("Fin del programa");
		}
		try
		{
			//CARGAR LOS DRIVERS
			Class.forName(driver);
			//ESTABLECER LA CONEXION
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
			//CREAR LA SENTENCIA DE CONSULTA O DE ALTA O DE BAJA O DE ACTU...
			statement = connection.createStatement();
			//EJECUTAR LA INSTRUCCION SQL
			rs = statement.executeQuery(sentenciaSQL);//SELECT * FROM -;
			//SACAR INFORMACIÓN, METER DATOS, BORRAR DATOS, ACTUALIZAR
			//MOSTRAR EN LA CONSOLA
			while(rs.next())
			{
				txaConsulta.append(rs.getInt("idAsignatura")+ 
						"-"+rs.getString("nombreAsignatura")+
						"-" +rs.getString("descripcionAsignatura")+
						"-" +rs.getString("idCicloFK")+"\n");
			}
			
		}
		catch(ClassNotFoundException  cnfe)
		{
			System.err.println("Error de driver");
		}
		catch(SQLException se)
		{
			System.err.println("Error de conexión: url, usuaro o clave");
		}
		finally 
		{
			try
			{
				//DESCONECTAR DE LA BD
				if(connection!=null)
				{
					connection.close();
				}
			}
			catch(SQLException evento)
			{
				System.out.println("Error al cerrar conexión");
			}
			System.out.println("Fin del programa");
		}		
	}
	 @Override
		public void windowClosing(WindowEvent e)
		 {
			 // Salir
			 System.exit(0);


		 }
	

}

