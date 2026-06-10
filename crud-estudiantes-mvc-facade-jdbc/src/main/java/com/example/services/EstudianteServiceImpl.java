package com.example.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Estudiante;
import com.example.models.EstudianteUpdate;
import com.example.models.Genero;

public class EstudianteServiceImpl implements EstudianteService {
	private static final Logger LOG = Logger.getLogger("EstudianteServiceImpl");
	
	@Override
	public boolean isConnectionOK() throws Exception {
		boolean connectionOK = false;
		
		try(DBConexion conexion = new DBConexion("root", "Temp2026");
				Connection conn = conexion.getConnection()) {
			if(conn != null) {
				connectionOK = true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return connectionOK;
	}

	@Override
	public List<Estudiante> getEstudiantes() {
		
		List<Estudiante> estudiantes = new ArrayList<Estudiante>();
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConnection()){
			
			ResultSet rs = dbConexion.getEstudiantes(connection);
			
			while(rs.next()) {
				
				estudiantes.add(
						Estudiante.builder()
						.id(rs.getInt("id"))
						.nombre(rs.getString("Nombre"))
						.PrimerApellido(rs.getString("primerApellido"))
						.SegundoApellido(rs.getString("segundoApellido"))
						.FechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate())
						.Genero(Genero.valueOf(rs.getString("genero")))
						.Beca(new BigDecimal(rs.getDouble("beca")))
						.TotalAsignaturas(rs.getInt("totalAsignaturas"))
						.Facultades_id(rs.getInt("Facultades_id"))
						.build()
						);
			}
		} catch (Exception e) {
			LOG.severe("Error al recuperar los estudiantes" + e.getMessage());
			e.printStackTrace();
		}
		
		// Devolver la lista de estudiantes recopilada. Antes devolvíamos null por error,
		// lo que causaba NullPointerException al iterar en la JSP.
		return estudiantes;
	}

	@Override
	public void altaEstudiante(Estudiante estudiante, List<String> emails, List<String> telefonos) throws SQLException {

		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConnection()) {
			dbConexion.altaEstudiantes(estudiante, emails, telefonos, connection);
			
		} catch (Exception e) {
			LOG.severe("Error en la insercion "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public EstudianteUpdate getEstudianteById(int idEstudiante) {
		
		EstudianteUpdate estudianteUpdate = null;
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConnection()) {
			
			ResultSet rs = dbConexion.getInfo(idEstudiante, connection);
			int idEst = 0;
			String nombreEst = null;
			String primerApellido = null;
			String segundoApellido = null;
			LocalDate fechaNacimiento = null;
			Genero genero = null;
			BigDecimal beca = new BigDecimal(0);
			int asignaturas = 0;
			int idFac = 0;
			String nombreFac = null;
			Set<String> numTfl = new HashSet<String>();
			Set<String> emails = new HashSet<String>();
			
			if(rs.next()) {
				idEst = rs.getInt("idEstudiante");
				nombreEst = rs.getString("nombreEstudiante");
				primerApellido = rs.getString("primerApellido");
				segundoApellido = rs.getString("segundoApellido");
				fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();
				genero = Genero.valueOf(rs.getString("genero"));
				beca = new BigDecimal(rs.getDouble("beca"));
				asignaturas = rs.getInt("totalAsignaturas");
				idFac = rs.getInt("idFac");
				nombreFac = rs.getString("nombreFac");
			}
			
			rs.beforeFirst();
			while(rs.next()) {
				emails.add(rs.getString("email"));
			}
			
			rs.beforeFirst();
			while(rs.next()) {
				numTfl.add(rs.getString("numero"));
			}
			
			estudianteUpdate = new EstudianteUpdate(idEst, nombreEst, primerApellido, segundoApellido, fechaNacimiento, genero, beca, asignaturas, idFac, nombreFac, numTfl , emails);
			
			LOG.info("Informacion "+ estudianteUpdate);
			
		} catch (Exception e) {
			LOG.severe("Error a la hora de recuperar los datos "+e.getMessage());
			e.printStackTrace();
		}
		
		return estudianteUpdate;
	}
	

	@Override
	public void updateEstudiante(Estudiante estudiante, List<String> emails, List<String> numTlf) {
		// TODO Auto-generated method stub
		
	}

}
