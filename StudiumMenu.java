package es.studium;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudiumMenu extends WindowAdapter 
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

	 public StudiumMenu()
	 {
	 ventana.setLayout(new FlowLayout());
	 ventana.setMenuBar(barraMenu);
	 
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
	 }
	 public static void main(String[] args)
	 {
	 new StudiumMenu();
	 }
	 @Override
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}}
