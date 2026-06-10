package com.example.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record EstudianteUpdate(
		int id, String nombreEmpleado, 
		String PrimerApellido, String SegundoApellido, 
		LocalDate FechaNacimiento, Genero Genero, 
		BigDecimal Beca, int Asignaturas,
		int idFacultad,
		String nombreFacultad,
		Set<String> numTlf, Set<String> emails) {

}
