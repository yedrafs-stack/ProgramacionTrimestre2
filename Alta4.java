package es.studium;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Alta4 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("AltaMatricula");
	Choice choAsignaturas = new Choice();
	Choice choAlumnos = new Choice();
	Label lblFecha = new Label("Fecha (dd/MM/yyyy)");
	String fechaParaBD = "";	
	TextField txtFecha = new TextField(10);
	Label lblNota= new Label("Nota");
	TextField txtNota = new TextField(10);
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	//CONECTAR BD gestion DE 192.168.0.35
	String driver="com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	//CUIDADIN CON EL USUARIO
	String usuario = "programacion";
	String password = "Studium2025#";
	//HACER UN ALTA
	String sentenciaSQL = "SELECT * FROM matricular";
		
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
		
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Alta");
	
	public Alta4()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(275,210);
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		
		ventana.add(lblFecha);
		ventana.add(txtFecha);
		ventana.add(lblNota);
		ventana.add(txtNota);
		
		rellenarAsignaturas();
		rellenarAlumnos();
		ventana.add(choAsignaturas);
		ventana.add(choAlumnos);
		
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		
		ventana.setResizable(true);		
		ventana.setLocationRelativeTo(null);
		
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(50,80);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setResizable(false);		
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);
		
		ventana.setVisible(true);
	}
	
	private void convertirFecha()
	{
		
		java.util.Date utilDate;
		try
		{
	        SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yyyy");
	        utilDate = sdfEntrada.parse(txtFecha.getText());

	        SimpleDateFormat sdfSalida = new SimpleDateFormat("yyyy-MM-dd");
	        fechaParaBD = sdfSalida.format(utilDate);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}  

	private void rellenarAsignaturas()
	{
		// Conectar a una BD

				try

				{

					// Cargar los drivers

					Class.forName(driver);

					// Establecer la conexión

					connection = DriverManager.getConnection(url, usuario, password);

					System.out.println("Conexión establecida");

					// Crear la sentencia de consulta o de alta o de baja o de actu...

					statement = connection.createStatement();
					sentenciaSQL="SELECT * FROM asignaturas";

					// Ejecutar la instrucción SQL

					rs = statement.executeQuery(sentenciaSQL);

					// Sacar información, meter datos, borrar datos, actualizar

					choAsignaturas.add("Seleccionar una asignatura...");

					while (rs.next())

					{

						choAsignaturas.add(rs.getInt("idAsignatura") +

								" " + rs.getString("nombreAsignatura") +

								" " + rs.getString("descripcionAsignatura")+
								
								" " + rs.getString("idCicloFK"));

					}

				}

				catch (ClassNotFoundException cnfe)

				{

					System.err.println("Error de driver");

				}

				catch (SQLException se)

				{

					System.err.println("Error de conexión: url, usuario o clave");

				}

				finally

				{

					try

					{

						// Desconectar de la BD

						if (connection != null)

						{

							connection.close();

						}

					}

					catch (SQLException e)

					{

						System.err.println("Error al cerrar conexión");

					}

					System.out.println("Fin del programa");

				}
	}
	private void rellenarAlumnos()
	{
		// Conectar a una BD

				try

				{

					// Cargar los drivers

					Class.forName(driver);

					// Establecer la conexión

					connection = DriverManager.getConnection(url, usuario, password);

					System.out.println("Conexión establecida");

					// Crear la sentencia de consulta o de alta o de baja o de actu...

					statement = connection.createStatement();
					sentenciaSQL="SELECT * FROM alumnos";

					// Ejecutar la instrucción SQL

					rs = statement.executeQuery(sentenciaSQL);

					// Sacar información, meter datos, borrar datos, actualizar

					choAlumnos.add("Seleccionar un alumno...");

					while (rs.next())

					{

						choAlumnos.add(rs.getInt("idAlumno") +

								" " + rs.getString("nombreAlumno") +

								" " + rs.getString("apellidosAlumno")+
								
								" " + rs.getString("dniAlumno"));

					}

				}

				catch (ClassNotFoundException cnfe)

				{

					System.err.println("Error de driver");

				}

				catch (SQLException se)

				{

					System.err.println("Error de conexión: url, usuario o clave");

				}

				finally

				{

					try

					{

						// Desconectar de la BD

						if (connection != null)

						{

							connection.close();

						}

					}

					catch (SQLException e)

					{

						System.err.println("Error al cerrar conexión");

					}

					System.out.println("Fin del programa");

				}
	}
	@Override
	public void windowClosing(WindowEvent e)
	 {
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		else
		{
			// SALIR
			ventana.setVisible(false);
		}
	 }
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAceptar))
		{
			try
			{
				//CARGAR LOS DRIVERS
				Class.forName(driver);
				//ESTABLECER LA CONEXION
				connection = DriverManager.getConnection(url, usuario, password);
				System.out.println("Conexión establecida");
				//CREAR LA SENTENCIA DE CONSULTA  O DE ALTA O DE BAJA O DE ACTU...
				statement=connection.createStatement();
				convertirFecha();
				sentenciaSQL = "INSERT INTO matricular VALUES(null, '"+fechaParaBD+"','"+txtNota.getText()+"','"+choAsignaturas.getSelectedItem().split(" ")[0]+"','"+choAlumnos.getSelectedItem().split(" ")[0]+"' )";
				System.out.println(sentenciaSQL);
				statement.executeUpdate(sentenciaSQL);
				
				FicheroLog.Log("Alta realizada: " + sentenciaSQL);
				lblMensaje.setText("Alta correcta");
				dlgMensaje.setVisible(true);
				
			}
			catch(ClassNotFoundException  cnfe)
			{
				System.err.println("Error de driver"+cnfe.getMessage());
			}
			catch(SQLException se)
			{
				lblMensaje.setText("Error en Alta");
				dlgMensaje.setVisible(true);		
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
			
				catch(SQLException e)
				{
					System.out.println("Error al cerrar conexión");
				}
					System.out.println("Fin del programa");
				}
			}
			else if(evento.getSource().equals(btnLimpiar))
			{
				txtFecha.setText("");
				txtNota.setText("");
				choAsignaturas.select(0);
				choAlumnos.select(0);
				txtFecha.requestFocus();

			}
		
		
	}

}
	