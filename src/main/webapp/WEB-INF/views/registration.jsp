
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Registration</title>

<script src="<c:url value="/static/js/jquery-1.11.1.min.js" />"></script>
<link href="<c:url value="/static/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/static/css/hackermenHMS.css" />"
	rel="stylesheet">
<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/static/js/customerPage.js" />"></script>


</head>
<body>

	<div class="pageHeader">
		<img src="<c:url value="/static/images/HMS Logo.png" />"
			alt="HMS Hackermen logo" class="hmsLogo" id="hmsLogo" />
		<div class="navBarDiv">
			<nav class="navbar navbar-default">
				<ul class="nav navbar-nav">
					<li class="active"><a
						href="${pageContext.request.contextPath}/registration/">Login/Registration</a></li>
					<li><a href="">Room Search (under development)</a></li>
					<li><a href="">My Reservations (under development)</a></li>
					<li><a href="">Staff - Manage System Data (under
							development)</a></li>
				</ul>
			</nav>
		</div>
	</div>

	<div class="container">

		<h1>Customer Registration</h1>
		<h3>Please input account information</h3>
		<form:form action="${pageContext.request.contextPath}/registration/"
			cssClass="form-horizontal" method="post" modelAttribute="customer"
			onSubmit="return passVal()">
			<div class="form-group">
				<label for="fName" class="col-md-3 controllabel">First Name:</label>
				<div class="col-md-9">
					<form:input path="fName" cssClass="form-control"
						required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="lName" class="col-md-3 controllabel">Last Name:</label>
				<div class="col-md-9">
					<form:input path="lName" cssClass="form-control"
						required="required" />
				</div>
			</div>
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			<div class="form-group">
				<label for="username" class="col-md-3 controllabel">Username:</label>
				<div class="col-md-9">
					<form:input path="username" cssClass="form-control"
						required="required" />
				</div>
			</div>

			<div class="alert alert-danger" id="passMismatch"
				style="display: none">Passwords do not match</div>

			<div class="form-group">
				<label for="password" class="col-md-3 controllabel">Password:</label>
				<div class="col-md-9">
					<form:input type="password" id="password" path="password"
						cssClass="form-control" required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="confPassword" class="col-md-3 controllabel">Confirm
					Password:</label>
				<div class="col-md-9">
					<input type="password" id="confPassword"
						cssClass="form-control form-control-static" required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-md-3 controllabel">Email:</label>
				<div class="col-md-9">
					<form:input type="email" path="email" cssClass="form-control"
						required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="address" class="col-md-3 controllabel">Address:</label>
				<div class="col-md-9">
					<form:input path="address" cssClass="form-control"
						required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="phoneNumber" class="col-md-3 controllabel">Phone
					Number:</label>
				<div class="col-md-9">
					<form:input type="tel" path="phoneNumber" cssClass="form-control"
						required="required" />
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<form:button cssClass="btn btnprimary">Submit</form:button>
				</div>
			</div>
		</form:form>

	</div>
</body>
</html>