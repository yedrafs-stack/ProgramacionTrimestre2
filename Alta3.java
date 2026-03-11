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

public class Alta3 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("AltaAsignatura");
	Choice choDepartamentos = new Choice();
	Label lblNom = new Label("Nombre");
	TextField txtNom = new TextField(10);
	Label lblDescripcion= new Label("Descripción");
	TextField txtDescripcion = new TextField(10);
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	//CONECTAR BD gestion DE 192.168.0.35
	String driver="com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	//CUIDADIN CON EL USUARIO
	String usuario = "programacion";
	String password = "Studium2025#";
	//HACER UN ALTA
	String sentenciaSQL = "SELECT * FROM asignaturas";
		
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
		
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Alta");
	
	public Alta3()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250,210);
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		
		ventana.add(lblNom);
		ventana.add(txtNom);
		ventana.add(lblDescripcion);
		ventana.add(txtDescripcion);
		
		rellenarChoice();
		ventana.add(choDepartamentos);

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

	private void rellenarChoice()
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
					sentenciaSQL="SELECT * FROM ciclos";

					// Ejecutar la instrucción SQL

					rs = statement.executeQuery(sentenciaSQL);

					// Sacar información, meter datos, borrar datos, actualizar

					choDepartamentos.add("Seleccionar un ciclo...");

					while (rs.next())

					{

						choDepartamentos.add(rs.getInt("idCiclo") +

								" " + rs.getString("nombreCiclo") +

								" " + rs.getString("descripcionCiclo"));

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
		new Alta3();
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
		 System.exit(0);
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
		
				sentenciaSQL = "INSERT INTO asignaturas VALUES(null, '"+txtNom.getText()+"','"+txtDescripcion.getText()+"','"+choDepartamentos.getSelectedItem().split(" ")[0]+"' )";
				statement.executeUpdate(sentenciaSQL);
				lblMensaje.setText("Alta correcta");
				dlgMensaje.setVisible(true);
				
			}
			catch(ClassNotFoundException  cnfe)
			{
				System.err.println("Error de driver"+cnfe.getMessage());
			}
			catch(SQLException se)
			{
				lblMensaje.setText("Alta correcta");
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
				txtNom.setText("");
				txtDescripcion.setText("");
				txtNom.requestFocus();

			}
		
		
	}

}

