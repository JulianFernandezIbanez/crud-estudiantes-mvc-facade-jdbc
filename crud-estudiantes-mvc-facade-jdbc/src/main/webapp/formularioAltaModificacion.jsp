<%@page import="java.util.stream.Collectors"%>
<%@page import="com.example.models.Genero"%>
<%@page import="com.example.models.EstudianteUpdate"%>
<%@page import="java.util.List"%>
<%@page import="com.example.services.FacultadesServiceImpl"%>
<%@page import="com.example.services.FacultadesService"%>
<%@page import="com.example.models.Facultad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Formulario de Insercion</title>
		<!--  <script>
			const form = document.getElementbyId("formulario1");
			form.onSubmit(form.reset());
		</script>-->
	</head>
	<body>
		<%
			FacultadesService service = new FacultadesServiceImpl();
			List<Facultad> facultades = service.getFacultades();
			EstudianteUpdate estudianteUpdate = (EstudianteUpdate) request.getAttribute("estudianteUpdate");
		%> 
		<h1>Formulario de Alta/Modificacion de Estudiantes</h1>
		
		<fieldset>
			<legend>Formulario de Gestion de Estudiante</legend>
			<form action="AltaController" method="post" id="formulario1">
				<input type="hidden" id="idEstudiante" name="idEstudiante" value="<%= estudianteUpdate == null ? 0 : estudianteUpdate.id()%>">
				<div>
					<label for="nombre">Nombre: </label>
					<input type="text" id="nombre" name="nombre" value="<%= estudianteUpdate != null ? estudianteUpdate.nombreEmpleado() : " " %>" required placeholder="Introduzca su nombre">
				</div>
				<div>
					<label for="primerApellido">Primer Apellido: </label>
					<input type="text" id="primerApellido" name="primerApellido" value="<%= estudianteUpdate != null ? estudianteUpdate.PrimerApellido() : " " %>" required placeholder="Introduzca su primer apellido">
				</div>
				<div>
					<label for="segundoApellido">Segundo Apellido: </label>
					<input type="text" id="segundoApellido" name="segundoApellido" value="<%= estudianteUpdate != null ? estudianteUpdate.SegundoApellido() : " " %>" placeholder="Introduzca su segundo apellido">
				</div>
				<div>
					<label for="fechaNacimiento">Fecha de Nacimiento: </label>
					<input type="date" name="fechaNacimiento" id="fechaNacimiento" value="<%= estudianteUpdate != null ? estudianteUpdate.FechaNacimiento() : " " %>" required>
				</div>
				<div>
					<fieldset>
						<legend>Genero: </legend>
						<label for="hombre">Hombre</label>
						<input type="radio" id="hombre" name="genero" value="HOMBRE" <%= estudianteUpdate != null && estudianteUpdate.Genero().equals(Genero.HOMBRE) ? "checked" : ' '%> required>
						<br>
						<label for="mujer">Mujer</label>
						<input type="radio" id="mujer" name="genero" value="MUJER" <%= estudianteUpdate != null && estudianteUpdate.Genero().equals(Genero.MUJER) ? "checked" : ' '%> required>
						<br>
						<label for="otro">Otro</label>
						<input type="radio" id="otro" name="genero" value="OTRO" <%= estudianteUpdate != null && estudianteUpdate.Genero().equals(Genero.OTRO) ? "checked" : ' '%> required>
					</fieldset>
				</div>
				<div>
					<label for="beca">Beca: </label>
					<input type="text" id="beca" name="beca" value="<%= estudianteUpdate != null ? estudianteUpdate.Beca() : " " %>" required placeholder="Introduzca su beca">
				</div>
				<div>
					<label for="asignatura">Asignaturas Matriculadas: </label>
					<input type="text" id="asignatura" name="asignatura" value="<%= estudianteUpdate != null ? estudianteUpdate.Asignaturas() : " " %>" required placeholder="Introduzca sus asignaturas">
				</div>
				<div>
					<label for="facultad">Seleccione su facultad</label>
					<select id="facultad" name="facultad" required>
						<option></option>
						<% for (Facultad facultad : facultades) {%>
							<option value=<%= facultad.id() %> <%= estudianteUpdate != null && estudianteUpdate.idFacultad() ==  facultad.id() ? "selected" : ' ' %>><%= facultad.nombre() %></option>
						<%
							}	
						%>
					</select>
				</div>
				<div>
					<label for="correos">Correos: </label>
					<input type="text" id="correos" name="correos" value="<%= estudianteUpdate != null && !estudianteUpdate.emails().contains(null) ? estudianteUpdate.emails().stream().collect(Collectors.joining(";")) : ' ' %>" placeholder="Añada uno o varios separados por ;">
				</div>
				<div>
					<label for="telefonos">Telefonos: </label>
					<input type="text" id="telefonos" name="telefonos" value="<%= estudianteUpdate != null && !estudianteUpdate.numTlf().contains(null) ? estudianteUpdate.numTlf().stream().collect(Collectors.joining(";")) : ' ' %>" placeholder="Añada uno o varios separados por ;">
				</div>
				<br>
				<br>
				<input type="submit" value="Enviar">
			</form>
		</fieldset>
	</body>
</html>