package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.example.models.Estudiante;

public class DBConexion implements AutoCloseable {

	private String username;
	private String passwd;
	private Connection connection;
	private static final Logger LOG = Logger.getLogger("DBConexion");
	
	public DBConexion(String username, String passwd) {
		this.username = username;
		this.passwd = passwd;
	}
	
	public Connection getConnection() throws ClassNotFoundException{
		String urlConnection = "jdbc:mysql://localhost:3306/empresa-crud-empleados";
		Properties info = new Properties();
		
		info.put("user", this.username);
		info.put("password", this.passwd);
		
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(urlConnection, info);
            LOG.info("Conexion Establecida exitosamente");
        } catch (SQLException e) {
            LOG.severe("Error de conexion: " + e.getMessage());
            e.printStackTrace();
        }
		
		return this.connection;
		
	}
	
	public ResultSet getEstudiantes(Connection connection) {
		
		ResultSet rs = null;
		String query = "SELECT * FROM universidad.Estudiantes";
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return(rs);
		
	}
	
	public ResultSet getFacultades(Connection connection) {
		
		ResultSet rs = null;
		String query = "SELECT * FROM universidad.Facultades";
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (Exception e) {
			LOG.severe("Error al recuperar las facultades. Causa "+e.getMessage());
			e.printStackTrace();
		}
		
		return (rs);
		
	}
	
	public void altaEstudiantes(Estudiante estudiante,
			List<String> dirCorreos,
			List<String> numTlf,
			Connection connection) throws SQLException {
		
		String query1 = "INSERT INTO `universidad`.`Estudiantes`  (`Nombre`, "
				+ "`primerApellido`, `segundoApellido`, "
				+ "`fechaNacimiento`, `genero`, "
				+ "`beca`, `totalAsignaturas`, "
				+ "`Facultades_id`) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";
		String query2 = "INSERT INTO `universidad`.`Correo` (`email`, `Estudiantes_id`) "
				+ "VALUES (?, ?)";
		String query3 = "INSERT INTO `universidad`.`Telefonos` (`numero`, `Estudiantes_id`) "
				+ "VALUES (?, ?)";
		
		try {
			connection.setAutoCommit(false);
			
			PreparedStatement stmt1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
			stmt1.setString(1, estudiante.nombre());
			stmt1.setString(2, estudiante.PrimerApellido());
			stmt1.setString(3, estudiante.SegundoApellido());
			stmt1.setDate(4, Date.valueOf(estudiante.FechaNacimiento()));
			stmt1.setString(5, estudiante.Genero().name());
			stmt1.setDouble(6, estudiante.Beca().doubleValue());
			stmt1.setInt(7, estudiante.TotalAsignaturas());
			stmt1.setInt(8, estudiante.Facultades_id());
			
			int filas = stmt1.executeUpdate();
			if(filas != 0) {
				long lastInsertedId = 0L;
				ResultSet rs = stmt1.getGeneratedKeys();
				
				if (rs.next()) {
					lastInsertedId = rs.getLong(1);
				}
				
				if (dirCorreos != null && dirCorreos.size() > 0) {
					PreparedStatement stmt2 = connection.prepareStatement(query2);
					
					stmt2.setInt(2, Math.toIntExact(lastInsertedId));
					
					for (String email : dirCorreos) {
						stmt2.setString(1, email);
						stmt2.addBatch();
					}
					
					stmt2.executeBatch();
				}
				
				if (numTlf != null && numTlf.size() > 0) {
					PreparedStatement stmt3 = connection.prepareStatement(query3);
					
					stmt3.setInt(2, Math.toIntExact(lastInsertedId));
					
					for (String tlf : numTlf) {
						stmt3.setString(1, tlf);
						stmt3.addBatch();
					}
					
					stmt3.executeBatch();
				}
				
				connection.commit();
			}
			
			
		} catch (Exception e) {
			LOG.severe("Error en la insercion. Causa: "+e.getMessage());
			e.printStackTrace();
			connection.rollback();
			LOG.info("Transaccion revertida, insercion cancelada");
		}finally {
			connection.setAutoCommit(true);
		}
		
	}
	
public ResultSet getInfo(int idEstudinte, Connection connection) {
		
		ResultSet rs = null;
		String query = "SELECT "
				+ "`universidad`.`Estudiantes`.id idEstudiante, "
				+ "`universidad`.`Estudiantes`.nombre nombreEstudiante, "
				+ "`universidad`.`Estudiantes`.primerApellido, "
				+ "`universidad`.`Estudiantes`.segundoApellido, "
				+ "`universidad`.`Estudiantes`.fechaNacimiento, "
				+ "`universidad`.`Estudiantes`.genero, "
				+ "`universidad`.`Estudiantes`.beca, "
				+ "`universidad`.`Estudiantes`.totalAsignaturas, "
				+ "`universidad`.`Estudiantes`.facultades_id, "
				+ "fac.id idFac, "
				+ "fac.nombre nombreFac, "
				+ "tel.numero, "
				+ "co.email "
				+ "FROM `universidad`.`Estudiantes`"
				+ "LEFT JOIN `universidad`.`Facultades` fac ON `universidad`.`Estudiantes`.Facultades_id = fac.id "
				+ "LEFT JOIN `universidad`.`Correo` co ON `universidad`.`Estudiantes`.id = co.Estudiantes_id "
				+ "LEFT JOIN `universidad`.`Telefonos` tel ON `universidad`.`Estudiantes`.id = tel.Estudiantes_id "
				+ "WHERE `universidad`.`Estudiantes`.id = ?;";
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, idEstudinte);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			LOG.severe("Error al conseguir la informacion "+ e.getMessage());
			e.printStackTrace();
		}
		
		return rs;
	}
	
	@Override
	public void close() throws Exception {
		this.connection.close();
		
	}

}
