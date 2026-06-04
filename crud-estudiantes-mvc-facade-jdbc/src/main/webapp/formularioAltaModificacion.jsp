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
		%> 
		<h1>Formulario de Alta/Modificacion de Estudiantes</h1>
		
		<fieldset>
			<legend>Formulario de Gestion de Estudiante</legend>
			<form action="AltaController" method="post" id="formulario1">
				<div>
					<label for="nombre">Nombre: </label>
					<input type="text" id="nombre" name="nombre" required placeholder="Introduzca su nombre">
				</div>
				<div>
					<label for="primerApellido">Primer Apellido: </label>
					<input type="text" id="primerApellido" name="primerApellido" required placeholder="Introduzca su primer apellido">
				</div>
				<div>
					<label for="segundoApellido">Segundo Apellido: </label>
					<input type="text" id="segundoApellido" name="segundoApellido" placeholder="Introduzca su segundo apellido">
				</div>
				<div>
					<label for="fechaNacimiento">Fecha de Nacimiento: </label>
					<input type="date" name="fechaNacimiento" id="fechaNacimiento" required>
				</div>
				<div>
					<fieldset>
						<legend>Genero: </legend>
						<label for="hombre">Hombre</label>
						<input type="radio" id="hombre" name="genero" value="HOMBRE" required>
						<br>
						<label for="mujer">Mujer</label>
						<input type="radio" id="mujer" name="genero" value="MUJER" required>
						<br>
						<label for="otro">Otro</label>
						<input type="radio" id="otro" name="genero" value="OTRO" required>
					</fieldset>
				</div>
				<div>
					<label for="beca">Beca: </label>
					<input type="text" id="beca" name="beca" required placeholder="Introduzca su beca">
				</div>
				<div>
					<label for="asignatura">Asignaturas Matriculadas: </label>
					<input type="text" id="asignatura" name="asignatura" required placeholder="Introduzca sus asignaturas">
				</div>
				<div>
					<label for="facultad">Seleccione su facultad</label>
					<select id="facultad" name="facultad" required>
						<option></option>
						<% for (Facultad facultad : facultades) {%>
							<option value=<%= facultad.id() %>><%= facultad.nombre() %></option>
						<%
							}	
						%>
					</select>
				</div>
				<div>
					<label for="correos">Correos: </label>
					<input type="text" id="correos" name="correos" placeholder="Añada uno o varios separados por ;">
				</div>
				<div>
					<label for="telefonos">Telefonos: </label>
					<input type="text" id="telefonos" name="telefonos" placeholder="Añada uno o varios separados por ;">
				</div>
				<br>
				<br>
				<input type="submit" value="Enviar">
			</form>
		</fieldset>
	</body>
</html>