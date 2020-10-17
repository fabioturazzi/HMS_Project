
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Success</title>

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
					<li><a href="${pageContext.request.contextPath}/userManagement/customer">Manage System Data</a></li>
				</ul>
			</nav>
		</div>
	</div>



	<div class="container">
		<h1>${customer.fName}'s profile was successfully created</h1>
		<h3>Account information:</h3>
		<hr />

		<table class="table table-striped table-bordered">
			<tr>
				<td><b>Username</b></td>
				<td>${customer.username}</td>
			</tr>
			<tr>
				<td><b>User Type</b></td>
				<td>${customer.userType}</td>
			</tr>
			<tr>
				<td><b>First Name</b></td>
				<td>${customer.fName}</td>
			</tr>
			<tr>
				<td><b>Last Name</b></td>
				<td>${customer.lName}</td>
			</tr>
			<tr>
				<td><b>Email</b></td>
				<td>${customer.email}</td>
			</tr>
			<tr>
				<td><b>Address</b></td>
				<td>${customer.address}</td>
			</tr>
			<tr>
				<td><b>Password</b></td>
				<td>${customer.password}</td>
			</tr>
			<tr>
				<td><b>Phone Number</b></td>
				<td>${customer.phoneNumber}</td>
			</tr>

		</table>

	</div>
</body>
</html>