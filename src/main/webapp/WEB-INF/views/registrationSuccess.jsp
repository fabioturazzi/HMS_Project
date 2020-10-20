
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Success</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>
	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
	</div>



	<div class="container">
		<h1 id="pageTitle">Successful Registration</h1>
		<h3>${customer.fName}'s profile was successfully created</h3>
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
				<td><b>Phone Number</b></td>
				<td>${customer.phoneNumber}</td>
			</tr>

		</table>

	</div>
</body>
</html>