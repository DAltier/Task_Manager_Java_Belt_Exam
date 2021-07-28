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
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script defer src="https://use.fontawesome.com/releases/v5.1.0/js/all.js"></script>
	<title><c:out value="${task.taskName}" /></title>
</head>

<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-sm-6 p-4">
				<h3 class="text-center"><a href="/tasks">Dashboard</a></h3>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-m p-4">
				<h1 class="text-center">Task: <c:out value="${task.taskName}" /></h1>
				<table class="mytable">
					<tr>
						<td class="subtitle">Creator:</td>
						<td class="subtitle">${task.creator.getName()}</td>
					</tr>
					<tr>
						<td class="subtitle">Assignee:</td>
						<td class="subtitle">${task.assignee.getName()}</td>
					</tr>
					<tr>
						<td class="subtitle">Priority:</td>
						<c:if test="${task.priority==1}">
							<td class="subtitle">Low</td>
						</c:if>
						<c:if test="${task.priority==2}">
							<td class="subtitle">Medium</td>
						</c:if>
						<c:if test="${task.priority==3}">
							<td class="subtitle">High</td>
						</c:if>
					</tr>
					
					<!-- Black belt requirement -->	
					<!-- If current user is a task creator, then show Edit and Delete buttons -->	
					<c:if test="${task.creator.getId()==currentUserId}">
					<tr>
						<td><a class="btn btn-success" href="/tasks/${task.id}/edit">Edit</a></td>
						<td><a class="btn btn-success" href="/tasks/${task.id}/delete">Delete</a></td>
					</tr>
					</c:if>
				</table>
					
				<!-- Black belt requirement -->
				<!-- If current user is an assignee, then show Complete button -->
				<c:if test="${task.assignee.getId()==currentUserId}">
					<div class="buttons has-addons is-left">
						<a href="/tasks/${task.id}/delete" class="btn btn-success">Completed</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>