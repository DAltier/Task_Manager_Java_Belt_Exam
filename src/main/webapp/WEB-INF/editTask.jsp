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
	<title>Edit Task</title>
</head>
	<div class="container ">
		<div class="row justify-content-center">
			<div class="col-sm-6">
				<h3 class="text-center"><a href="/tasks">Dashboard</a></h3>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-m p-4">
				<h1 class="text-center">Edit <c:out value="${task.taskName}" /></h1>
				<form:form method="POST" action="/tasks/${id}/edit" modelAttribute="edittask">
					<form:input type="hidden" path="id"></form:input> 
					<h4 class="avoid">
						<form:label path="taskName">Task:</form:label>
						<form:input cssClass="fields" type="text" path="taskName"/>
						<form:errors path="taskName"/>
					</h4>
					<h4 class="avoid">
						<form:label path="assignee">Assignee:</form:label>
						<form:select path="assignee" style="width: 15rem;">
							<form:option value="${edittask.assignee}"> ${edittask.assignee.getName()}</form:option>
							<c:forEach items="${users}" var="user"> 							
							<!-- List all users except the current assignee and the task creator-->
							<!-- Check if user isn't the current assignee  -->
								<c:if test="${user.name != edittask.assignee.getName()}"> 
									<!-- Check if user isn't the task creator -->
									<c:if test="${user.name!=creator.name}">
										<form:option value="${user}"><c:out value="${user.name}" /></form:option>
									</c:if>
								</c:if> 
							</c:forEach> 
						</form:select>
					</h4>
					<h4 class="avoid">
						<form:label path="priority">Priority:</form:label>
						<form:select path="priority" style="width: 15rem;">
							<form:option value="1">Low</form:option>
							<form:option value="2">Medium</form:option>
							<form:option value="3">High</form:option>
						</form:select>
					</h4>
					<!-- Creator needs to be specified when updating a task -->
					<form:input type="hidden" path="creator" value="${creator.id}"/>
					<div class="buttons has-addons is-right">
						<input type="submit" value="Save" class="btn btn-success" />
					</div>
				</form:form>
				<p>
					<form:errors path="task.*"></form:errors>
				</p>
				<p><c:out value="${errors}"/></p>
			</div>
		</div>
	</div>
<body>

</body>
</html>