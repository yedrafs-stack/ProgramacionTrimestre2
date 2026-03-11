package es.studium;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudiumMenu extends WindowAdapter implements ActionListener
{
	 Frame ventana = new Frame("Grupo Studium");
	 MenuBar barraMenu = new MenuBar();
	 
	 Menu ciclo = new Menu("Ciclos");
	 Menu alumno = new Menu("Alumnos");
	 Menu asignatura = new Menu("Asignaturas");

	 MenuItem cicloAlta = new MenuItem("Alta");
	 MenuItem cicloBaja = new MenuItem("Baja");
	 MenuItem cicloModificacion = new MenuItem("Modificacion");
	 MenuItem cicloConsulta = new MenuItem("Consulta");
	 MenuItem alumnoAlta = new MenuItem("Alta");
	 MenuItem alumnoBaja = new MenuItem("Baja");
	 MenuItem alumnoModificacion = new MenuItem("Modificacion");
	 MenuItem alumnoConsulta = new MenuItem("Consulta");
	 MenuItem asignaturaAlta = new MenuItem("Alta");
	 MenuItem asignaturaBaja = new MenuItem("Baja");
	 MenuItem asignaturaModificacion = new MenuItem("Modificacion");
	 MenuItem asignaturaConsulta = new MenuItem("Consulta");
	 
	 int tipo;
	 public StudiumMenu(int t)
	 {
		 this.tipo = t;
	 ventana.setLayout(new FlowLayout());
	 ventana.setMenuBar(barraMenu);
	 ventana.addWindowListener(this);
	 
	 cicloAlta.addActionListener(this);
     cicloBaja.addActionListener(this);
     alumnoAlta.addActionListener(this);
     alumnoBaja.addActionListener(this);
     asignaturaAlta.addActionListener(this);
     asignaturaBaja.addActionListener(this);
     cicloModificacion.addActionListener(this);
     cicloConsulta.addActionListener(this);
     alumnoModificacion.addActionListener(this);
     alumnoConsulta.addActionListener(this);
     asignaturaModificacion.addActionListener(this);
     asignaturaConsulta.addActionListener(this);
	 
	 ciclo.add(cicloAlta);
	 ciclo.add(cicloBaja);
	 ciclo.add(cicloModificacion);
	 ciclo.add(cicloConsulta);
	 
	 alumno.add(alumnoAlta);
	 alumno.add(alumnoBaja);
	 alumno.add(alumnoModificacion);
	 alumno.add(alumnoConsulta);

	 asignatura.add(asignaturaAlta);
	 asignatura.add(asignaturaBaja);
	 asignatura.add(asignaturaModificacion);
	 asignatura.add(asignaturaConsulta);
	 
	 barraMenu.add(ciclo);
	 barraMenu.add(alumno);
	 barraMenu.add(asignatura);

	 ventana.setSize(350,200);
	 ventana.setVisible(true);
	 
	 if (tipo == 1) { // Si es usuario básico
         cicloBaja.setEnabled(false);
         alumnoBaja.setEnabled(false);
         asignaturaBaja.setEnabled(false);
         
         cicloModificacion.setEnabled(false);
         alumnoModificacion.setEnabled(false);
         asignaturaModificacion.setEnabled(false);
         
         cicloConsulta.setEnabled(false);
         alumnoConsulta.setEnabled(false);
         asignaturaConsulta.setEnabled(false);
     }
	 
	 }
	 public static void main(String[] args)
	 {
		 new StudiumMenu(0);
		 }
	 @Override
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	 @Override
	 public void actionPerformed(ActionEvent e)
	 {
		 if (e.getSource().equals(cicloAlta)) 
		 {
			    new Alta1();
		 } 
		 else if (e.getSource().equals(alumnoAlta)) 
		 {
			    new Alta2();		
		 }
		 else if (e.getSource().equals(asignaturaAlta)) 
		 {
			    new Alta3();
		 } 
		 else if (e.getSource().equals(cicloBaja)) 
		 {
			    new Baja1();		
		 }
		 else if (e.getSource().equals(alumnoBaja)) 
		 {
			    new Baja2();
		 } 
		 else if (e.getSource().equals(asignaturaBaja)) 
		 {
			    new Baja3();		
		 }
		 else if (e.getSource().equals(cicloModificacion)) 
		 {
			    new Modificacion1();
		 } 
		 else if (e.getSource().equals(alumnoModificacion)) 
		 {
			    new Modificacion2();		
		 }
		 else if (e.getSource().equals(cicloConsulta)) 
		 {
			    new Consulta1();		
		 }
		 else if (e.getSource().equals(alumnoConsulta)) 
		 {
			    new Consulta2();		
		 }
		 else if (e.getSource().equals(asignaturaConsulta)) 
		 {
			    new Consulta3();		
		 }
		 
	 }
}
