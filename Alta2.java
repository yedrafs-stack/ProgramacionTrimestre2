package es.studium;

import java.awt.Button;
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
import java.sql.SQLException;
import java.sql.Statement;

public class Alta2 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("AltaAlumno");
	Label lblNom = new Label("Nombre");
	TextField txtNom = new TextField(10);
	Label lblApellido= new Label("Apellidos");
	TextField txtApellido = new TextField(10);
	Label lblDni= new Label("Dni");
	TextField txtDni = new TextField(10);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	//CONECTAR BD gestion DE 192.168.0.35
	String driver="com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	//CUIDADIN CON EL USUARIO
	String usuario = "programacion";
	String password = "Studium2025#";
	//HACER UN ALTA
	String sentenciaSQL = "SELECT * FROM alumnos";
			
	Connection connection = null;
	Statement statement = null;
	
	
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Alta");
	
	public Alta2()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(200,200);
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		
		ventana.add(lblNom);
		ventana.add(txtNom);
		ventana.add(lblApellido);
		ventana.add(txtApellido);
		ventana.add(lblDni);
		ventana.add(txtDni);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		
		ventana.setResizable(false);		
		ventana.setLocationRelativeTo(null);
		
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(50,80);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setResizable(false);		
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);
		
		ventana.setVisible(true);

	}
	public static void main(String[] args)
	{
		new Alta2();

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
		
				sentenciaSQL = "INSERT INTO alumnos VALUES(null, '"+txtNom.getText()+"','"+txtApellido.getText()+"','"+txtDni.getText()+"')";
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
			txtApellido.setText("");
			txtDni.setText("");
			txtNom.requestFocus();

			}
	
	}
}


