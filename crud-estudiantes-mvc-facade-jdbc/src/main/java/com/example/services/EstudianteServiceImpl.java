package com.example.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Estudiante;
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
	
	
}
