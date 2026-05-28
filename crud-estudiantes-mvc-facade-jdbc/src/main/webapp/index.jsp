<%@page import="com.example.services.EstudianteServiceImpl"%>
<%@page import="com.example.services.EstudianteService"%>
<%@page import="com.example.models.Estudiante"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Universidad</title>
<style>
	table, th, td {
				border: 1px solid black;
				border-collapse: collapse;
			}
			th, td {
				padding: 10px;
			}
</style>
</head>
<body>
	<%
	EstudianteService service = new EstudianteServiceImpl();
	List<Estudiante> estudiantes = service.getEstudiantes();
	%>
	<h1>Bienvenido a nuestra universidad</h1>
	<br>
	<h1>Estos son nuestros alumnos matriculados</h1>
	<table>
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Primer Apellido</th>
				<th>Segundo Apellido</th>
				<th>Fecha de Nacimiento</th>
				<th>Genero</th>
				<th>Asignaturas Matriculadas</th>
				<th>Beca</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Estudiante estudiante : estudiantes) {
			%>
			<tr>
				<td><%=estudiante.nombre()%></td>
				<td><%=estudiante.PrimerApellido()%></td>
				<td><%=estudiante.SegundoApellido()%></td>
				<td><%=estudiante.FechaNacimiento()%></td>
				<td><%=estudiante.Genero()%></td>
				<td><%=estudiante.TotalAsignaturas()%></td>
				<td><%=estudiante.Beca()%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>