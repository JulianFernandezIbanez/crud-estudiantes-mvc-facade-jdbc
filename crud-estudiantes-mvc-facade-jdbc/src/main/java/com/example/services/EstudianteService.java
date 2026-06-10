package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.models.Estudiante;
import com.example.models.EstudianteUpdate;

public interface EstudianteService {
	public abstract boolean isConnectionOK() throws SQLException, Exception;
	public abstract List<Estudiante> getEstudiantes();
	public abstract void altaEstudiante(Estudiante estudiante, List<String> emails, List<String> telefonos) throws SQLException;
	public abstract EstudianteUpdate getEstudianteById(int idEstudiante);
	public abstract void updateEstudiante(Estudiante estudiante, List<String> emails, List<String> numTlf);

}
