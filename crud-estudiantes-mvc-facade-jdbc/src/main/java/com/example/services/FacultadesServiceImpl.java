package com.example.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Facultad;

public class FacultadesServiceImpl implements FacultadesService {

	private static final Logger LOG = Logger.getLogger("FacultadesServiceImpl");

	
	@Override
	public List<Facultad> getFacultades() {

		List<Facultad> facultades = new ArrayList<Facultad>();
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConnection();) {
			
			ResultSet rs = dbConexion.getFacultades(connection);
			
			while(rs.next()) {
				facultades.add(
					Facultad.builder()
					.id(rs.getInt("id"))
					.nombre(rs.getString("nombre"))
					.build());
			}
			
		} catch (Exception e) {
			LOG.severe("Error al recuperar las facultades" + e.getMessage());
			e.printStackTrace();
		}
		
		
		return facultades;
	}

}
