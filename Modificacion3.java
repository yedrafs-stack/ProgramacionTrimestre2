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

public class Modificacion3 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Modificación");
	Choice choAsignatura = new Choice();
	Button btnEditar = new Button("Editar");
	Dialog dlgEdicion = new Dialog(ventana, "Editando...", true);
	Label lblAsignatura = new Label("# # # Editando la asignatura X # # #");
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);
	Label lblDescripcion = new Label("Descripción:");
	TextField txtDescripcion = new TextField(10);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Baja");
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	String usuario = "programacion";
	String password = "Studium2025#";
	String sentenciaSQLAsignatura = "SELECT * FROM asignaturas";
	String sentenciaSQL = "";
	String idAsignatura = "";
	
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null; // Para los SELECT
	
	public Modificacion3()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250, 100);
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		
		// Rellenar el Choice
		rellenarAsignatura();

		ventana.add(choAsignatura);
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
		dlgEdicion.setSize(250, 250);
		dlgEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		dlgEdicion.add(lblAsignatura);
		dlgEdicion.add(lblNombre);
		dlgEdicion.add(txtNombre);
		dlgEdicion.add(lblDescripcion);
		dlgEdicion.add(txtDescripcion);
		

		dlgEdicion.add(btnAceptar);
		dlgEdicion.add(btnLimpiar);
		dlgEdicion.setResizable(false);
		dlgEdicion.setLocationRelativeTo(null);
		ventana.setVisible(true);

	}
	private void rellenarAsignatura()
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
			rs = statement.executeQuery(sentenciaSQLAsignatura);
			// Sacar información, meter datos, borrar datos, actualizar
			choAsignatura.add("Seleccionar una Asignatura...");
			while (rs.next())
			{
				choAsignatura.add(rs.getInt("idAsignatura") +
						" " + rs.getString("nombreAsignatura") +
						" " + rs.getString("descripcionAsignatura"));
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
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
			new Modificacion3();
		}
		else if (dlgEdicion.isActive())
		{
			dlgEdicion.setVisible(false);
			new Modificacion3();
		}
		else
		{
			// Salir
			ventana.setVisible(false);
		}
	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource().equals(btnEditar))
		{
			if (choAsignatura.getSelectedIndex() != 0)
			{
				idAsignatura = choAsignatura.getSelectedItem().split(" ")[0];
				sentenciaSQL = "SELECT * FROM asignaturas WHERE idAsignatura = " + idAsignatura;
				lblAsignatura.setText("# # # Editando la Asignatura " + idAsignatura + " # # #");
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
					txtNombre.setText(rs.getString("nombreAsignatura"));
					txtDescripcion.setText(rs.getString("descripcionAsignatura"));
					
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
			}
			else
			{
				choAsignatura.requestFocus();
			}
		}
		else if (evento.getSource().equals(btnAceptar))
		{
			sentenciaSQL = "UPDATE asignaturas SET nombreAsignatura = '" + txtNombre.getText() + "', descripcionAsignatura = '"
			+ txtDescripcion.getText() + "' WHERE idAsignatura = " + idAsignatura;
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
				FicheroLog.Log("Modificación realizada: " + sentenciaSQL);
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
			txtDescripcion.setText("");
			txtNombre.requestFocus();
		}
	}
	
}
	
	
	
