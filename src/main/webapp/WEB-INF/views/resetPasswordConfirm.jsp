
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
		<h3>Please input login and password recovery answer</h3>

		<hr></hr>
		<form:form cssClass="form-horizontal registerForm" method="POST"
			modelAttribute="user" onSubmit="return passVal()">
			<h3>Registration</h3>
			<hr></hr>
			<c:if test="${ message != null }">
				<h4 class="alert alert-danger">${message}</h4>
			</c:if>
			<div class="form-group">
				<label for="username" class="col-md-3 controllabel">Username</label>
				<div class="col-md-9">
					<form:input path="username" id="username" class="form-control"
						required="required" value="${user.username}" readonly="true"></form:input>
					<form:errors path="username" style="color:red" />
				</div>
			</div>
			<div class="form-group">
				<label for="passQuestion" class="col-md-3 controllabel">Reset
					Question</label>
				<div class="col-md-9">
					<form:input path="passQuestion" id="passQuestion"
						class="form-control" required="required" readonly="true"
						value="${user.passQuestion}"></form:input>
					<form:errors path="passQuestion" style="color:red" />
				</div>
			</div>
			<div class="form-group">
				<label for="passAnswer" class="col-md-3 controllabel">Answer</label>
				<div class="col-md-9">
					<form:input path="passAnswer" id="passAnswer"
						cssClass="form-control" required="required" />
					<form:errors path="passAnswer" style="color:red" />
				</div>
			</div>
			<div class="alert alert-danger" id="passMismatch"
				style="display: none">Passwords do not match</div>
			<div class="form-group">
				<label for="password" class="col-md-3 controllabel">New
					Password</label>
				<div class="col-md-9">
					<form:input type="password" path="password" id="password"
						cssClass="form-control" required="required" pattern=".{8,}"
						title="Password must have 8 characters" />
					<form:errors path="password" style="color:red" />
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-md-3 controllabel">New
					Password (Confirm)</label>
				<div class="col-md-9">
					<input type="password" id="confPassword" class="form-control"
						required="required" pattern=".{8,}"
						title="Password must have 8 characters" />
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