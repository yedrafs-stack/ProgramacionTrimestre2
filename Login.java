package es.studium;

import java.awt.Button;
import java.awt.Color;
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


public class Login extends WindowAdapter implements ActionListener
{
	//admin:5678
	//basico.1234
	Frame ventana = new Frame ("Login");
	Label lblUsuario= new Label ("Usuario");
	Label lblClave= new Label ("Clave");
	TextField txtUsuario= new TextField(20);
	TextField txtClave= new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	Dialog dlgDialogo = new Dialog (ventana, "Error", true);
	Label lblMensaje = new Label("Credenciales Incorrectas");
	
	public Login()
	{
		ventana.setLayout(new FlowLayout());
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		ventana.setSize(250,150);
		ventana.add(lblUsuario);
		ventana.add(txtUsuario);
		ventana.add(lblClave);
		txtClave.setEchoChar('*');
		ventana.add(txtClave);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		ventana.setBackground(Color.pink);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
		
		dlgDialogo.setLayout(new FlowLayout());
		dlgDialogo.setSize(200,150);
		dlgDialogo.add(lblMensaje);
		dlgDialogo.setResizable(false);		
		dlgDialogo.setLocationRelativeTo(null);
		dlgDialogo.addWindowListener(this);
	}

	public static void main(String[] args)
	{
		 new Login();
	}
	
	@Override
	 public void windowClosing(WindowEvent e)
	 {
		System.exit(0);
	 }

	@Override
	public void actionPerformed(ActionEvent evento)
	{
	    if (evento.getSource().equals(btnLimpiar))
		{
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		}
	    else if (evento.getSource().equals(btnAceptar))
	    {
	    	String usuarioDado = txtUsuario.getText();
			String claveDada = txtClave.getText();
			// CONECTAR A LA BD GESTION
			Programacion2 gestionBD = new Programacion2();
			Connection conexion = gestionBD.conectar();
			// HACER CONSULTA
			int respuesta = gestionBD.comprobarCredenciales(usuarioDado, claveDada);
			gestionBD.desconectar(conexion);
			if (respuesta == 0 || respuesta == 1 || respuesta == 2)
			{
				System.out.println("Tipo" + respuesta + ":Administrador");
				ventana.dispose();
				new StudiumMenu(respuesta);
			}
			else
			{
				dlgDialogo.setVisible(true);
			}
			FicheroLog.usuario =txtUsuario.getText(); 
			FicheroLog.Log("[Acesso al Sistema]");


			// SELECT * FROM USUARIO WHERE NOMBREUSUARIO = NOMBREDADO AND CLAVEUSUARIO = CLAVEDADA
			// OBTENGA 0 RESULTADOS ==> CREDENCIALES INCORRECTAS
			// OBTENGA 1 RESULTADO ==> CREDENCIALES CORRECTAS
			// REDIRIGIR A PRINCIPAL
	    }
	}

}