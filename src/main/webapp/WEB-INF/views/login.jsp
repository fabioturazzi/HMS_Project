
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
	</div>

	<div class="container">

		<h1 id="pageTitle">Welcome to Hackermen Hotel Management System</h1>
		<h3>Please input your login and password</h3>

		<hr></hr>
		<form:form cssClass="form-horizontal registerForm" method="POST"
			modelAttribute="user">
			<h3>Registration</h3>
			<hr></hr>
			<c:if test="${ message != null }">
				<h4 class="alert alert-danger">${message}</h4>
			</c:if>
			<div class="form-group">
				<label for="usernameForm" class="col-md-3 controllabel">Username</label>
				<div class="col-md-9">
					<form:input path="usernameForm" id="usernameForm"
						cssClass="form-control" required="required" />
					<form:errors path="usernameForm" style="color:red" />
				</div>
			</div>
			<div class="form-group">
				<label for="usernameForm" class="col-md-3 controllabel">Password</label>
				<div class="col-md-9">
					<form:password path="passwordForm" id="passwordForm"
						cssClass="form-control" required="required" />
					<form:errors path="passwordForm" style="color:red" />
				</div>
			</div>
			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-primary" type="submit">Submit</button>
				</div>
			</div>
			<div class="form-group">
				<a class="col-md-offset-3 col-md-9 signUpSign"
					href="${pageContext.request.contextPath}/registration">Don't
					have an account? Sign up!</a>
			</div>


		</form:form>
	</div>
</body>
<script src="<c:url value="/static/js/navBar.js" />"></script>
</html>