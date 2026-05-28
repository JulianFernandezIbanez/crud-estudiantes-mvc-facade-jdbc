package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

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
	
	@Override
	public void close() throws Exception {
		this.connection.close();
		
	}

}
