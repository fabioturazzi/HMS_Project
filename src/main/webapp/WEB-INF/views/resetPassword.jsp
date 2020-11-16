
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

		<h1 id="pageTitle">Password Reset</h1>
		<h3>Please input username</h3>

		<hr></hr>
		<form:form cssClass="form-horizontal registerForm" method="POST"
			modelAttribute="user">
			<h3>Registration</h3>
			<hr></hr>
			<c:if test="${ message != null }">
				<h4 class="alert alert-danger">${message}</h4>
			</c:if>
			<div class="form-group">
				<label for="username" class="col-md-3 controllabel">Select Username</label>
				<div class="col-md-9">
					<form:input path="username" id="username"
						cssClass="form-control" required="required" />
					<form:errors path="username" style="color:red" />
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-primary" type="submit">Submit</button>
				</div>
			</div>

		</form:form>
	</div>
</body>
<script src="<c:url value="/static/js/navBar.js" />"></script>
</html>