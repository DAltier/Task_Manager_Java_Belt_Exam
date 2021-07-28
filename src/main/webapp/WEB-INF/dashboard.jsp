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
	<title>Task Manager</title>
</head>

<body>
	<div class="container">
		<div class="myheader">
			<p class="cleardisplayblock">
				Welcome,
				<c:out value="${user.name}" />
			</p>
			<h5 class="mini_nav">
				<a class="link" href="/highlow">Priority High-Low</a> | <a class="link"
					href="/lowhigh">Priority Low-High</a> | <a class="logout" href="/logout">Logout</a>
			</h5>
		</div>

		<!-- table task-->
		<table class="table is-fullwidth is-bordered">
			<thead class="tablehead">
				<tr>
					<td>Task</td>
					<td>Creator</td>
					<td>Assignee</td>
					<td>Priority</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tasks}" var="task" varStatus="loop">
					<tr>
						<td><a href="/tasks/${task.id}">${task.taskName }</a></td>
						<td>${task.creator.getName()}</td>
						<td>${task.assignee.getName()}</td>
						<c:if test="${task.priority==1}">
							<td>Low</td>
						</c:if>
						<c:if test="${task.priority==2}">
							<td>Medium</td>
						</c:if>
						<c:if test="${task.priority==3}">
							<td>High</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="buttons has-addons is-right">
			<a href="/tasks/new" class="btn btn-success">Create Task</a>
		</div>
	</div>
</body>
</html>