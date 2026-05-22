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
import java.text.SimpleDateFormat;

import java.text.ParseException;

public class Modificacion4 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Modificación");
	Button btnEditar = new Button("Editar");
	Dialog dlgEdicion = new Dialog(ventana, "Editando...", true);
	Label lblMatricula = new Label("# # # Editando la Matricula X # # #");
	Label lblFecha = new Label("Fecha (dd/MM/yyyy):"); 
	TextField txtFecha = new TextField(10);
	Label lblNota = new Label("Nota:");
	TextField txtNota = new TextField(10);
	Choice choMatricula = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Baja");
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	String usuario = "programacion";
	String password = "Studium2025#";
	String sentenciaSQLMatricula = "SELECT * FROM matricular";
	String sentenciaSQL = "";
	String idMatricula = "";
	
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null; // Para los SELECT
	
	public Modificacion4()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250, 100);
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		
		// Rellenar el Choice
		rellenarMatricula();

		ventana.add(choMatricula);
		ventana.add(btnEditar);
		ventana.setResizable(true);
		ventana.setLocationRelativeTo(null);
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(50, 80);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setResizable(false);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);
		dlgEdicion.setLayout(new FlowLayout());
		dlgEdicion.setSize(350, 200);
		dlgEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		dlgEdicion.add(lblMatricula);
		dlgEdicion.add(lblFecha);
		dlgEdicion.add(txtFecha);
		dlgEdicion.add(lblNota);
		dlgEdicion.add(txtNota);
		

		dlgEdicion.add(btnAceptar);
		dlgEdicion.add(btnLimpiar);
		dlgEdicion.setResizable(true);
		dlgEdicion.setLocationRelativeTo(null);
		ventana.setVisible(true);

	}
	private void rellenarMatricula()
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
			rs = statement.executeQuery(sentenciaSQLMatricula);
			// Sacar información, meter datos, borrar datos, actualizar
			choMatricula.add("Seleccionar una matricula...");
			while (rs.next())
			{
				choMatricula.add(rs.getInt("idMatricula")+ 
						"-"+rs.getString("fechaMatricula")+
						"-" +rs.getString("notaMatricula")+
						"-" +rs.getString("idAsignaturaFK")+
						"-" +rs.getString("idAlumnoFK"));
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
			new Modificacion4();
		}
		else if (dlgEdicion.isActive())
		{
			dlgEdicion.setVisible(false);
			new Modificacion4();
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
			if (choMatricula.getSelectedIndex() != 0)
			{
				idMatricula = choMatricula.getSelectedItem().split("-")[0];
				sentenciaSQL = "SELECT * FROM matricular WHERE idMatricula = " + idMatricula;
				System.out.println(sentenciaSQL);
				lblMatricula.setText("# # # Editando la matricula " + idMatricula + " # # #");
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
					String fechaBD = rs.getString("fechaMatricula");
					try {
						SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date fechaAux = sdfEntrada.parse(fechaBD);
						
						SimpleDateFormat sdfSalida = new SimpleDateFormat("dd/MM/yyyy");
						txtFecha.setText(sdfSalida.format(fechaAux)); // Se muestra formateada al usuario
					} catch (ParseException pe) {
						txtFecha.setText(fechaBD);
				}
					txtNota.setText(rs.getString("notaMatricula"));
				}
				catch (ClassNotFoundException cnfe)
				{
					System.err.println("Error de driver");
				}
				catch (SQLException se)
				{
					System.err.println("Error de conexión: url, usuario o clave" + se.getMessage());
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
				choMatricula.requestFocus();
			}
		}
		else if (evento.getSource().equals(btnAceptar))
		{
			String fechaParaBD = "";
			try {
				SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date fechaAux = sdfEntrada.parse(txtFecha.getText());
				
				SimpleDateFormat sdfSalida = new SimpleDateFormat("yyyy-MM-dd");
				fechaParaBD = sdfSalida.format(fechaAux);
			} catch (ParseException pe) {
				System.err.println("Error: Formato de fecha introducido inválido.");
				fechaParaBD = txtFecha.getText();
			}
			sentenciaSQL = "UPDATE matricular SET fechaMatricula = '" + fechaParaBD + "', notaMatricula = '"
			+ txtNota.getText() + "' WHERE idMatricula = " + idMatricula;
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
				System.err.println("Error de conexión: url, usuario o clave" + se.getMessage());
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
			txtFecha.setText("");
			txtNota.setText("");
			txtFecha.requestFocus();
		}
	}
	
}
