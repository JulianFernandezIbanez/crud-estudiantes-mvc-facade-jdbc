package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.models.Estudiante;

public interface EstudianteService {
	public abstract boolean isConnectionOK() throws SQLException, Exception;
	public abstract List<Estudiante> getEstudiantes();
}
