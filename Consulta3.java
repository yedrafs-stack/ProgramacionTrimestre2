package es.studium;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class Consulta3 extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("ConsultaAsignatura");
	TextArea txaConsulta = new TextArea(7,24);
	Button btnPDf = new Button("Exportar a PDF");
	Dialog dlgMensaje =  new Dialog(ventana,"Aviso", true);
	Label lblMensaje =  new Label ("");

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programacion2";
	String login = "programacion";
	String password = "Studium2025#";
	String sentenciaSQL = "SELECT idAsignatura, nombreAsignatura, descripcionAsignatura, nombreCiclo, descripcionCiclo, idCicloFK FROM asignaturas JOIN ciclos ON idCiclo = idCicloFK";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null; //PARA LOS SELECT
	
	public static final String DEST = "ConsultaAsignatura.pdf";


	public Consulta3()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300,240);
		ventana.addWindowListener(this);
		btnPDf.addActionListener(this);
		ventana.add(txaConsulta);
		ventana.add(btnPDf);
		ventana.setResizable(true);		
		ventana.setLocationRelativeTo(null);
		rellenarTextArea();
		ventana.setVisible(true);
	}
	private void rellenarTextArea()
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentenciaSQL);
			
			txaConsulta.setText(""); 
			while(rs.next())
			{
				txaConsulta.append(rs.getInt("idAsignatura")+ 
						"-"+rs.getString("nombreAsignatura")+
						"-" +rs.getString("descripcionAsignatura")+
						"-" +rs.getInt("idCicloFK")+
						"-" +rs.getString("nombreCiclo")+
						"-" +rs.getString("descripcionCiclo")+"\n");
			}
		}
		catch(ClassNotFoundException cnfe)
		{
			System.err.println("Error de driver: " + cnfe.getMessage());
		}
		catch(SQLException se)
		{
			System.err.println("Error de conexión al cargar inicio: " + se.getMessage());
		}
		finally 
		{
			try
			{
				if(connection!=null)
				{
					connection.close();
				}
			}
			catch(SQLException evento)
			{
				System.out.println("Error al cerrar conexión");
			}
		}
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
			System.out.println(sentenciaSQL);
			//EJECUTAR LA INSTRUCCION SQL
			rs = statement.executeQuery(sentenciaSQL);//SELECT * FROM -;
			//SACAR INFORMACIÓN, METER DATOS, BORRAR DATOS, ACTUALIZAR
			//MOSTRAR EN LA CONSOLA
			while(rs.next())
			{
				txaConsulta.append(rs.getInt("idAsignatura")+ 
						"-"+rs.getString("nombreAsignatura")+
						"-" +rs.getString("descripcionAsignatura")+
						"-" +rs.getInt("idCicloFK")+
						"-" +rs.getString("nombreCiclo")+
						"-" +rs.getString("descripcionCiclo")+"\n");
			}
			generarPDF();
			
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
	}
	 private void generarPDF()
	{
		 String ruta = "PDF/ConsultaAsignatura.pdf";
			try
			{
				PdfWriter writer = new PdfWriter(ruta);
				PdfDocument pdf = new PdfDocument(writer);
				Document document = new Document(pdf, PageSize.A4.rotate());
				
				document.add(new Paragraph("LISTADO DE ASIGNATURAS"));
				
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				rs = statement.executeQuery(sentenciaSQL);

				while(rs.next())
				{
					String fila = rs.getInt("idAsignatura")+ 
							"-"+rs.getString("nombreAsignatura")+
							"-" +rs.getString("descripcionAsignatura")+
							"-" +rs.getInt("idCicloFK")+
							"-" +rs.getString("nombreCiclo")+
							"-" +rs.getString("descripcionCiclo");
					document.add(new Paragraph(fila));
				}
				document.close();
				pdf.close();
				writer.close();
				
				File fichero = new File(ruta);
				Desktop.getDesktop().open(fichero);

				lblMensaje.setText("PDF Generado y Abierto");
				}
				catch (IOException | SQLException ioe)
				{
					lblMensaje.setText("Error al generar PDF");
				}
			lblMensaje.setVisible(true);
		
	}
	 @Override
		public void windowClosing(WindowEvent e)
		 {
			 // Salir
		 	ventana.setVisible(false);


		 }
	

}

