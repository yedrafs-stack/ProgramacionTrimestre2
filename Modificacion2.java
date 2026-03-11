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

public class Modificacion2 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Modificación");
	Choice choAlumno = new Choice();
	Button btnEditar = new Button("Editar");
	Dialog dlgEdicion = new Dialog(ventana, "Editando...", true);
	Label lblAlumno = new Label("# # # Editando el Alumno X # # #");
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);
	Label lblApellidos = new Label("Apellidos:");
	TextField txtApellidos = new TextField(10);
	Label lblDni= new Label("Apellidos:");
	TextField txtDni = new TextField(10);
	Choice choDepartamentos = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Baja");
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	String usuario = "programacion";
	String password = "Studium2025#";
	String sentenciaSQLAlumno = "SELECT * FROM alumnos";
	String sentenciaSQLDepartamentos = "SELECT * FROM departamentos";
	String sentenciaSQL = "";
	String idAlumno = "";
	
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null; // Para los SELECT

	public Modificacion2()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250, 100);
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		
		// Rellenar el Choice
		rellenarChoiceAlumno();

		ventana.add(choAlumno);
		ventana.add(btnEditar);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(50, 80);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setResizable(false);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);
		dlgEdicion.setLayout(new FlowLayout());
		dlgEdicion.setSize(230, 250);
		dlgEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		dlgEdicion.add(lblAlumno);
		dlgEdicion.add(lblNombre);
		dlgEdicion.add(txtNombre);
		dlgEdicion.add(lblApellidos);
		dlgEdicion.add(txtApellidos);
		dlgEdicion.add(lblDni);
		dlgEdicion.add(txtDni);
		

		rellenarChoiceDepartamentos();
		dlgEdicion.add(choDepartamentos);
		dlgEdicion.add(btnAceptar);
		dlgEdicion.add(btnLimpiar);
		dlgEdicion.setResizable(false);
		dlgEdicion.setLocationRelativeTo(null);
		ventana.setVisible(true);

	}
	private void rellenarChoiceDepartamentos()
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
			// Ejecutar la instrucción SQL
			rs = statement.executeQuery(sentenciaSQLDepartamentos);
			// Sacar información, meter datos, borrar datos, actualizar
			choDepartamentos.add("Seleccionar un departamento...");
			while (rs.next())
			{
				choDepartamentos.add(rs.getInt("idAlumno") +
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
	private void rellenarChoiceAlumno()
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
			// Ejecutar la instrucción SQL
			rs = statement.executeQuery(sentenciaSQLAlumno);
			// Sacar información, meter datos, borrar datos, actualizar
			choAlumno.add("Seleccionar un alumno...");
			while (rs.next())
			{
				choAlumno.add(rs.getInt("idAlumno") +
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
	public static void main(String[] args)
	{
		new Modificacion2();
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
			new Modificacion2();
		}
		else if (dlgEdicion.isActive())
		{
			dlgEdicion.setVisible(false);
			new Modificacion2();
		}
		else
		{
			// Salir
			System.exit(0);
		}
	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource().equals(btnEditar))
		{
			if (choAlumno.getSelectedIndex() != 0)
			{
				idAlumno = choAlumno.getSelectedItem().split(" ")[0];
				sentenciaSQL = "SELECT * FROM alumnos WHERE idAlumno = " + idAlumno;
				lblAlumno.setText("# # # Editando el Alumno " + idAlumno + " # # #");
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
					// Ejecutar la instrucción SQL
					rs = statement.executeQuery(sentenciaSQL);
					// Sacar información, meter datos, borrar datos, actualizar
					rs.next();
					txtNombre.setText(rs.getString("nombreAlumno"));
					txtApellidos.setText(rs.getString("apellidosAlumno"));
					txtDni.setText(rs.getString("dniAlumno"));
					
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
				dlgEdicion.setVisible(true);
				ventana.dispose();
			}
			else
			{
				choAlumno.requestFocus();
			}
		}
		else if (evento.getSource().equals(btnAceptar))
		{
			sentenciaSQL = "UPDATE alumnos SET nombreAlumno = '" + txtNombre.getText() + "', apellidosAlumno = '"
			+ txtApellidos.getText() + "', dniAlumno = '"
			+ txtDni.getText() + "' WHERE idAlumno = " + idAlumno;
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
				// Ejecutar la instrucción SQL
				statement.executeUpdate(sentenciaSQL);
				lblMensaje.setText("Modificación correcta");
				dlgMensaje.setVisible(true);
			}
			catch (ClassNotFoundException cnfe)
			{
				System.err.println("Error de driver");
			}
			catch (SQLException se)
			{
				System.err.println("Error de conexión: url, usuario o clave");
				lblMensaje.setText("Error en Modificación");
				dlgMensaje.setVisible(true);
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
			dlgEdicion.setVisible(false);
		}
		else if (evento.getSource().equals(btnLimpiar))
		{
			txtNombre.setText("");
			txtApellidos.setText("");
			txtDni.setText("");
			txtNombre.requestFocus();
		}
	}
	
}

