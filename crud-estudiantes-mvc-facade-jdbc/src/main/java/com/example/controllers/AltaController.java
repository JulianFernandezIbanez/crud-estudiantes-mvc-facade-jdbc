package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.models.Estudiante;
import com.example.models.Genero;
import com.example.services.EstudianteService;
import com.example.services.EstudianteServiceImpl;

/**
 * Servlet implementation class AltaController
 */
@WebServlet("/AltaController")
public class AltaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
		String nombre = request.getParameter("nombre");
		String primerApellido = request.getParameter("primerApellido");
		String segundoApellido = request.getParameter("segundoApellido") == null ?
				"": request.getParameter("segundoApellido");
		LocalDate FechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));
		Genero genero = Genero.valueOf(request.getParameter("genero"));
		BigDecimal Beca = BigDecimal.valueOf(Double.valueOf(request.getParameter("beca")));
		int Asignatura = Integer.parseInt(request.getParameter("asignatura"));
		int facultad_id = Integer.parseInt(request.getParameter("facultad"));
		List<String> dirCorreo = null;
		List<String> numTelefono = null;
		
		if(request.getParameter("correos") != null) {
			String correos = request.getParameter("correos");
			String[] direccionesDeCorreoRecibidos = correos.split(";");
			
			dirCorreo = Arrays.asList(direccionesDeCorreoRecibidos);
		}
		if(request.getParameter("telefonos") != null) {
			String telefonos = request.getParameter("telefonos");
			String[] numerosDeTelefonoRecibidos = telefonos.split(";");
			
			numTelefono = Arrays.asList(numerosDeTelefonoRecibidos);
		}
		
		Estudiante estudiante = Estudiante.builder()
				.nombre(nombre)
				.PrimerApellido(primerApellido)
				.SegundoApellido(segundoApellido)
				.FechaNacimiento(FechaNacimiento)
				.Genero(genero)
				.Beca(Beca)
				.TotalAsignaturas(Asignatura)
				.Facultades_id(facultad_id)
				.build();
		
		EstudianteService estudianteService = new EstudianteServiceImpl();
		
		if (idEstudiante == 0) {
			try {
				estudianteService.altaEstudiante(estudiante, dirCorreo, numTelefono);
				//estudiante = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			estudianteService.updateEstudiante(estudiante, dirCorreo, numTelefono);
		}
		
		
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
