<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.1/css/bulma.min.css">
	<script defer src="https://use.fontawesome.com/releases/v5.1.0/js/all.js"></script>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<title>Create Task</title>
</head>

<body>
	<div class="container ">
		<div class="row justify-content-center">
			<div class="col-sm-6">
				<h3 class="text-center"><a href="/tasks">Dashboard</a></h3>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-m p-4">
				<h1 class="text-center">Create a new task</h1>
				<form:form method="post" action="/tasks/new" modelAttribute="task">
					<h4 class="avoid">
						<form:label path="taskName">Task:</form:label>
						<form:input cssClass="fields" type="text" path="taskName" style="width: 15rem;"/>
					</h4>
					<h4 class="avoid">
						<form:label path="assignee">Assignee:</form:label>
						<form:select path="assignee" style="width: 15rem;">
							<form:option value=""></form:option>
							<c:forEach items="${users}" var="user">
								<c:if test="${user.name != currentUser.name}">
									<form:option value="${user}">
										<c:out value="${user.name}" />
									</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</h4>
					<h4 class="avoid">
						<form:label path="priority">Priority:</form:label>
						<form:select path="priority" style="width: 15rem;">
							<form:option value=""></form:option>
							<form:option value="1">Low</form:option>
							<form:option value="2">Medium</form:option>
							<form:option value="3">High</form:option>
						</form:select>
					</h4>
		
					<div class="buttons has-addons is-centered">
						<input type="submit" value="Create" class="btn btn-success" />
					</div>
				</form:form>
				<p>
					<form:errors path="task.*"></form:errors>
				</p>
				<p><c:out value="${errors}"/></p>
			</div>
		</div>
	</div>
</body>
</html>