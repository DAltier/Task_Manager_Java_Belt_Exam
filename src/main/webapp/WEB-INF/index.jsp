<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<title>Task Manager</title>
</head>

<body>
	<div id="container">
		<h1 class="text-center">Welcome</h1>
		
		<!-- registration -->
		<div id="leftpanel">
			<h1>Register</h1>
			<form:form method="post" action="/register" modelAttribute="user">
				<h4 class="avoid">
					<form:label path="name">Name:</form:label>
					<form:input cssClass="fields" type="text" path="name"/>
				</h4>
				<h4 class="avoid">
					<form:label path="email">Email:</form:label>
					<form:input cssClass="fields" type="email" path="email"/>
				</h4>
				<h4 class="avoid">
					<form:label path="password">Password:</form:label>
					<form:password cssClass="fields" path="password"/>
				</h4>
				<h4 class="avoid">
					<form:label path="passwordConfirmation">Confirm Password:</form:label>
					<form:password cssClass="fields" path="passwordConfirmation"/>
				</h4>
				<input type="submit" value="Register" class="btn btn-success"/>
			</form:form>
			<form:errors cssClass="red" path="user.*"/>
		</div>
		
		
		<!-- login -->
		<div id="rightpanel">
			<h1>Login</h1>
			<form action="/login" method="post">
				<h4>Email:<input class="fields" type="email" name="email"></h4>
				<h4>Password:<input class="fields" type="password" name="password"></h4>
				<input type="submit" value="Login" class="btn btn-success"/>
			</form>
			<p>
				<c:out value="${error}" />
			</p>
		</div>

	
	</div>
</body>
</html>